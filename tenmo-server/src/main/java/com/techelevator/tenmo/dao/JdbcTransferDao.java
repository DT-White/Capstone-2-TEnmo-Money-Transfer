package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
        private JdbcTemplate jdbcTemplate;
        public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

    @Override
    public void sendBucks(Long accountFrom, Long accountTo, BigDecimal amount) {
        String sql = "insert into transfer(transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount) values (2, 2, ?, ?,?)";
        jdbcTemplate.update(sql, accountFrom, accountTo,amount);
        addBucks(accountTo,amount);
        removeBucks(accountFrom,amount);
    }

    @Override
    public List<User> getAllUsers() {
            List<User> list = new ArrayList<>();
            String sql = "select user_id, username from tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            User user = new User();
            user.setId(results.getLong("user_id"));
            user.setUsername(results.getString("username"));
            list.add(user);
        }
        return list;
    }

    @Override
    public List<Transfer> listAllTransfers(Long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "select username, amount, account_from, account_to, transfer_id, transfer_status_desc, transfer_type_desc " +
                "from transfer join account on account.account_id = transfer.account_from or account.account_id = transfer.account_to " +
                "join tenmo_user on tenmo_user.user_id = account.user_id join transfer_status on transfer.transfer_status_id = " +
                "transfer_status.transfer_status_id join transfer_type on transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "where account.account_id != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while(results.next()){
            Transfer transfer = new Transfer();
            transfer.setTransferId(results.getLong("transfer_id"));
            transfer.setAmount(results.getBigDecimal("amount"));
            transfer.setUsername(results.getString("username"));
            transfer.setAccountFrom(results.getLong("account_from"));
            transfer.setAccountTo(results.getLong("account_to"));
            transfer.setTransferType(results.getString("transfer_type_desc"));
            transfer.setTransferStatus(results.getString("transfer_status_desc"));
            transfers.add(transfer);
        }
        return transfers;
    }

    private void addBucks(Long accoutTo, BigDecimal amount){
            String sql = "update account set balance = balance + ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accoutTo);
    }

    private void removeBucks(Long accountFrom, BigDecimal amount){
        String sql = "update account set balance = balance - ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accountFrom);
    }

    public void requestBucks (Long accountFrom, Long accountTo, BigDecimal amount) {
        String sql = "insert into transfer(transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount) values (1, 1, ?, ?,?)";
        jdbcTemplate.update(sql, accountFrom, accountTo,amount);
    }

    public void approvePendingRequest(Transfer transfer) {
        String sql = "update transfer set transfer_status_id = 2 where transfer_id = ?;";
        jdbcTemplate.update(sql,transfer.getTransferId());
        addBucks(transfer.getAccountTo(), transfer.getAmount());
        removeBucks(transfer.getAccountFrom(),transfer.getAmount());
    }

    public void rejectPendingRequest(Transfer transfer) {
        String sql = "update transfer set transfer_status_id = 3 where transfer_id = ?;";
        jdbcTemplate.update(sql,transfer.getTransferId());
    }

    @Override
    public List<Transfer> viewPendingRequest (String username){
        List<Transfer> transfers = new ArrayList<>();
            String sql = "select transfer_id, transfer_type_id, transfer_status_id, account_from, amount, username from " +
                    "transfer join account on transfer.account_to = account.account_id join tenmo_user on account.user_id = " +
                    "tenmo_user.user_id where transfer_type_id = 1 and transfer_status_id = 1 and username != ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()){
            Transfer transfer = new Transfer();
            transfer.setTransferId(results.getLong("transfer_id"));
            transfer.setAmount(results.getBigDecimal("amount"));
            transfer.setUsername(results.getString("username"));
            transfer.setAccountFrom(results.getLong("account_from"));
            transfers.add(transfer);
        }
        return transfers;
    }
}
