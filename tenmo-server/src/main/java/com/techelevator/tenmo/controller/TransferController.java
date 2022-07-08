package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        // handled creation of account DAO , spring framework created and injected
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void sendBucks(Principal principal, @RequestBody Transfer transfer) {
        Long accountFrom = accountDao.getAccount(principal.getName()).getAccountId();
        transferDao.sendBucks(accountFrom, accountDao.getAccountIdByUserId(transfer.getAccountTo()), transfer.getAmount());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return transferDao.getAllUsers();
    }

    @RequestMapping (path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal){
        Long accountId = accountDao.getAccount(principal.getName()).getAccountId();
        return transferDao.listAllTransfers(accountId);
    }

}
