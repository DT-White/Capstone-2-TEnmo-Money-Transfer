package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();


    public void sendBucks(AuthenticatedUser currentUser, Long accountTo, BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Transfer newTransfer = new Transfer();
        newTransfer.setAccountTo(accountTo);
        newTransfer.setAmount(amount);
        newTransfer.setTransferType("Send");
        newTransfer.setTransferStatus("Approved");
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);
        try{
            restTemplate.postForObject(API_BASE_URL + "/transfers", entity, Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
    public List<Transfer> getAllTranfers(){
        List<Transfer> transfers = null;
        try{
            transfers = new ArrayList<Transfer>(Arrays.asList(restTemplate.getForObject(API_BASE_URL + "/users", Transfer[].class)));

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;

    }
}
