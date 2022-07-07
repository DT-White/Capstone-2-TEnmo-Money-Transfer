package com.techelevator.tenmo.dao;
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
            List<User> list = null;
            String sql = "select user_id, username from tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            list = new ArrayList<>();
            User user = new User();
            user.setId(results.getLong("user_id"));
            user.setUsername(results.getString("username"));
            list.add(user);
        }
        return list;
    }

    private void addBucks(Long accoutTo, BigDecimal amount){
            String sql = "update account set balance = balance + ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accoutTo);
    }

    private void removeBucks(Long accountFrom, BigDecimal amount){
        String sql = "update account set balance = balance - ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accountFrom);
    }


}
