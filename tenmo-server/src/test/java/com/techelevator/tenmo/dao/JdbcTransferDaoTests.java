package com.techelevator.tenmo.dao;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {
    private JdbcTransferDao sut;

    @Before
    public void setUp() {
        sut = new JdbcTransferDao(dataSource);
    }

    @Test
    public void list_all_transfer_test() {
        List<Transfer> transfers = sut.listAllTransfers(2001);
        Assert.assertEquals("Transfer list have size 4",4, transfers.size());
    }

    @Test
    public void correct_transfer_in_list() {
        Transfer transfer = createTestTransfer();
        List<Transfer> transfers = sut.listAllTransfers(2001);
        Assert.assertTrue("Transfer list should contain a transfer equal to the test transfer",transfers.contains(transfer));
    }

    @Test
    public void send_bucks_test() {
        JdbcAccountDao accountDao = new JdbcAccountDao(dataSource);
        sut.sendBucks(2001,2002,new BigDecimal(100));
        List<Transfer> transfers = sut.listAllTransfers(2001);
        Account accountOne = accountDao.getAccount("testUserOne");
        Account accountTwo = accountDao.getAccount("testUserTwo");
        Assert.assertEquals("testUserOne should have 900 bucks after the transfer",0, accountOne.getBalance().compareTo(new BigDecimal(900)));
        Assert.assertEquals("testUserTwo should have 1100 bucks after the transfer",0, accountTwo.getBalance().compareTo(new BigDecimal(1100)));
        Assert.assertEquals("Transfer list should have size 5 after the transfer",5, transfers.size());
    }

    @Test
    public void get_all_pending_requests_test() {
        List<Transfer> pendingRequests = sut.getAllPendingRequests("testUserOne");
        Assert.assertEquals("Pending requests list should have size 1",1, pendingRequests.size());
        Transfer testTransfer = createTestTransfer();
        Assert.assertTrue("Pending requests list should contain a transfer equal to the test transfer",pendingRequests.contains(testTransfer));
    }

    @Test
    public void approve_pending_request_test(){
        JdbcAccountDao accountDao = new JdbcAccountDao(dataSource);
        Transfer testTransfer = createTestTransfer();
        sut.approvePendingRequest(testTransfer);
        testTransfer.setTransferStatus("Approved");
        Account accountOne = accountDao.getAccount("testUserOne");
        Account accountTwo = accountDao.getAccount("testUserTwo");
        Assert.assertTrue("Transfer list should contain a transfer equal to the test transfer, with status 'Approved', after the request is approved",sut.listAllTransfers(2001).contains(testTransfer));
        Assert.assertTrue("testUserOne should have 800 bucks after the request is approved",accountOne.getBalance().compareTo(new BigDecimal(800)) == 0);
        Assert.assertTrue("testUserTwo should have 1200 bucks after the request is approved",accountTwo.getBalance().compareTo(new BigDecimal(1200)) == 0);
    }

    @Test
    public void reject_pending_request_test(){
        Transfer testTransfer = createTestTransfer();
        sut.rejectPendingRequest(testTransfer);
        testTransfer.setTransferStatus("Rejected");
        Assert.assertTrue("Transfer list should contain a transfer equal to the test transfer, with status 'Rejected', after the request is rejected",sut.listAllTransfers(2001).contains(testTransfer));
    }

    private Transfer createTestTransfer() {
        Transfer transfer = new Transfer();
        transfer.setTransferId(3001);
        transfer.setAccountFrom(2001);
        transfer.setAccountTo(2002);
        transfer.setTransferStatus("Pending");
        transfer.setTransferType("Request");
        transfer.setUsername("testUserTwo");
        transfer.setAmount(new BigDecimal(200));
        return transfer;
    }
}
