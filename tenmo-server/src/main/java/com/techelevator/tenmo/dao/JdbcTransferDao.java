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
    public void sendBucks(Long accountTo, Long accountFrom, BigDecimal amount) {
        String sql = "start transaction insert into transfer(transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount) values (2, 2, ?, ?,?) returning transfer_id; update account set balance = balance - ? " +
                "where account_id = ?; update account set balance = balance + ? where account_id = ?; " +
                "commit;";
        Long transferId = jdbcTemplate.queryForObject(sql, Long.class, accountFrom, accountTo,amount, amount, accountFrom, amount, accountTo);


        // return id for insert statement



    }
}
