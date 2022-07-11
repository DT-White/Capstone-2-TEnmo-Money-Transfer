package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {
    private long transferId;
    private String transferType;
    private String transferStatus;
    private long accountFrom;
    private long accountTo;
    private BigDecimal amount;
    private String username;
    private long userIdTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return (transferId == transfer.transferId) && accountFrom == transfer.accountFrom && (accountTo == transfer.accountTo || transfer.accountTo == 0) && userIdTo == transfer.userIdTo && (Objects.equals(transferType, transfer.transferType) || Objects.equals(transfer.transferType, null)) && (Objects.equals(transfer.transferStatus, transferStatus) || Objects.equals(transfer.transferStatus, null)) && amount.compareTo(transfer.amount) == 0 && Objects.equals(username, transfer.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, transferType, transferStatus, accountFrom, accountTo, amount, username, userIdTo);
    }

    public long getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(Long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
