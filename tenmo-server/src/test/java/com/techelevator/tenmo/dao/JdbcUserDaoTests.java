package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;

    @Before
    public void setup(){
        sut = new JdbcUserDao(dataSource);
    }

    @Test
    public void find_all_users_test() {
        List<User> users = sut.findAll();
        Assert.assertEquals("User list should have size 4",4,users.size());
    }

    @Test
    public void find_id_by_username_test(){
        long retrievedId = sut.findIdByUsername("testUserOne");
        Assert.assertEquals("testUserOne should have id 1001",1001,retrievedId);
    }

    @Test
    public void find_by_username_test(){
        User testUser = createTestUser();
        User retrievedUser = sut.findByUsername("testUserOne");
        Assert.assertEquals("The user with the name 'testUserOne' should be equal to the test user",testUser,retrievedUser);
    }

    @Test
    public void create_user_test(){
        sut.create("createdUser","");
        User testUser = createTestUser();
        testUser.setId(1005);
        testUser.setUsername("createdUser");
        User retrievedUser = sut.findByUsername("createdUser");
        Assert.assertEquals("The created user should match the test user",testUser,retrievedUser);
    }

    private User createTestUser(){
        User user = new User();
        user.setId(1001);
        user.setUsername("testUserOne");
        user.setPassword("");
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
