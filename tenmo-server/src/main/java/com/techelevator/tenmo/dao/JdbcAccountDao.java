package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component

// lets springframework know that this is the one we want to use

public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Account getAccount(String userName) {
        Account account = null;
        String sql = "select account_id, tenmo_user.user_id, balance, username from account join tenmo_user on account.user_id = tenmo_user.user_id " +
                "where tenmo_user.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;

    }

    @Override
    public Long getAccountIdByUserId(long userId) {
        String sql = "select account_id from account where user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        Long accountId = null;
        if(results.next()) {
            accountId = results.getLong("account_id");
        }
       return accountId;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
