package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class AccountController {
    private AccountDao accountDao;
    public AccountController (AccountDao accountDao){
        this.accountDao = accountDao;
        // handled creation of account DAO , spring framework created and injected

    }
    @RequestMapping(path="/accounts", method = RequestMethod.GET)
    public Account getAccountByUserId (Principal principal){
        return accountDao.getAccount(principal.getName());
    }

}
