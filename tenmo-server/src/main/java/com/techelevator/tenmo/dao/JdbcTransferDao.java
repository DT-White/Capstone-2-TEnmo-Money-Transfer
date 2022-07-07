package com.techelevator.tenmo.dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

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

    private void addBucks(Long accoutTo, BigDecimal amount){
            String sql = "update account set balance = balance + ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accoutTo);
    }

    private void removeBucks(Long accountFrom, BigDecimal amount){
        String sql = "update account set balance = balance - ? where account_id = ?";
        jdbcTemplate.update(sql,amount, accountFrom);
    }

}
