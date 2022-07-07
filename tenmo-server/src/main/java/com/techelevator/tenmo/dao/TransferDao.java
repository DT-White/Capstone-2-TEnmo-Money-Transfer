package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    void sendBucks(Long accountFrom,Long accountTo, BigDecimal amount);
    List<User> getAllUsers();

}
