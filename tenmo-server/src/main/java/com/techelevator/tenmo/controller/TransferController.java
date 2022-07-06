package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
@RestController
public class TransferController {
        private TransferDao transferDao;
        private AccountDao accountDao;
        public TransferController (TransferDao transferDao, AccountDao accountDao){
            this.transferDao = transferDao;
            this.accountDao = accountDao;
            // handled creation of account DAO , spring framework created and injected
        }
        @RequestMapping(path="/transfers", method = RequestMethod.POST)
        public void sendBucks (Principal principal, @RequestBody Long accountTo, @RequestBody BigDecimal amount) {
            Long accountFrom = accountDao.getAccount(principal.getName()).getAccountId();
            transferDao.sendBucks(accountFrom, accountTo, amount);


            //returns account and gets ID

        }
}
