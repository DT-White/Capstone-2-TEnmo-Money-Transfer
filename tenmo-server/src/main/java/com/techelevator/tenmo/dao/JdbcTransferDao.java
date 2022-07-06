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
                "amount) values (2, 2, ?, ?,?); update account set balance = balance - ? " +
                "where account_id = ?; update account set balance = balance + ? where account_id = ?;";
        jdbcTemplate.queryForObject(sql, Long.class, accountFrom, accountTo,amount, amount, accountFrom, amount, accountTo);
// write new method to ask for ID
    }
}
