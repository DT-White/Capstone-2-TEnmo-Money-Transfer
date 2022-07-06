package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.Dictionary;

public interface TransferDao {
    public void sendBucks(Long accountFrom,Long accountTo, BigDecimal amount);
}
