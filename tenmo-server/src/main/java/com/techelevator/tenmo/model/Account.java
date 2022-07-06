package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    // stores info about the blalance that each user has
    private Long accountId;
    private Long userId;
    private BigDecimal balance;

    public Long getAccountId() {
        return accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
