package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private Long transferId;
    private String transferType;
    private String transferStatus;
    private Long accountFrom;
    private Long userIdTo;
    private BigDecimal amount;
    private String username;
    private Long accountTo;

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferType='" + transferType + '\'' +
                ", transferStatus='" + transferStatus + '\'' +
                ", accountFrom=" + accountFrom +
                ", userIdTo=" + userIdTo +
                ", amount=" + amount +
                ", username='" + username + '\'' +
                ", accountTo=" + accountTo +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public String getUserNameTo() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public Long getUserIdTo() {
        return userIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setUserIdTo(Long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
