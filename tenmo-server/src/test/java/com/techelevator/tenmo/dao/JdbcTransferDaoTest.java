package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests{
    private JdbcTransferDao sut;
    @Before
    public void setUp(){
        sut = new JdbcTransferDao(dataSource);
    }
    @Test
    public void list_all_transfer_test (){
        List<Transfer> transfers = sut.listAllTransfers(2001);
        Assert.assertEquals(4, transfers.size());
    }

    @Test
    public void correct_transfer_in_list(){
        Transfer transfer = createTestTransfer();
        List<Transfer> transfers = sut.listAllTransfers(2001);
        Assert.assertTrue(transfers.contains(transfer));

    }

    private Transfer createTestTransfer (){
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
