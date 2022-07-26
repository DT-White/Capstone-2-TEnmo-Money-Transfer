package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void sendBucks(Principal principal, @RequestBody Transfer transfer) {
        Long accountFrom = accountDao.getAccount(principal.getName()).getAccountId();
        transferDao.sendBucks(accountFrom, accountDao.getAccountIdByUserId(transfer.getUserIdTo()), transfer.getAmount());
    }

    @RequestMapping (path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal){
        String username = principal.getName();
        Long accountId = accountDao.getAccount(username).getAccountId();
        return transferDao.listAllTransfers(accountId, username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers/requests", method = RequestMethod.POST)
    public void requestBucks(Principal principal, @RequestBody Transfer transfer) {
        Long accountTo = accountDao.getAccount(principal.getName()).getAccountId();
        transferDao.requestBucks(accountDao.getAccountIdByUserId(transfer.getUserIdTo()), accountTo, transfer.getAmount());
    }

    @RequestMapping(path = "/transfers/requests", method = RequestMethod.GET)
    public List<Transfer> viewPendingRequests(Principal principal ){
        String username = principal.getName();
        return transferDao.getAllPendingRequests(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers/requests", method = RequestMethod.PUT)
    public void approvePendingRequest (@RequestBody Transfer transfer, @RequestParam String approved){
        if(approved.equalsIgnoreCase("True")){
        transferDao.approvePendingRequest(transfer);
    }
        else if (approved.equalsIgnoreCase("False")){
            transferDao.rejectPendingRequest(transfer);
        }
    }
}
