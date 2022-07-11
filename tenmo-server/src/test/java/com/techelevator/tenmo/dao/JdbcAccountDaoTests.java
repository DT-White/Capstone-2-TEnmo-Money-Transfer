package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests {
    private JdbcAccountDao sut;

    @Before
    public void setUp() {
        sut = new JdbcAccountDao(dataSource);
    }

    @Test
    public void get_account_test() {
        Account testAccount = createTestAccount();
        Account retrievedAccount = sut.getAccount("testUserOne");
        Assert.assertEquals("Retrieved account should be equal to test account",testAccount,retrievedAccount);
    }

    @Test
    public void get_account_id_by_user_id_test(){
        long retrievedAccountId = sut.getAccountIdByUserId(1001);
        Assert.assertEquals("Retrieved account id should be 2001",2001,retrievedAccountId);
    }

    private Account createTestAccount() {
        Account account = new Account();
        account.setAccountId(2001);
        account.setBalance(new BigDecimal(1000));
        account.setUserId(1001);
        return account;
    }
}
