package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();


    public void sendBucks(AuthenticatedUser currentUser, Long userIdTo, BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Transfer newTransfer = new Transfer();
        newTransfer.setUserIdTo(userIdTo);
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
    public List<Transfer> getAllTransfers(AuthenticatedUser currentUser){
        List<Transfer> transfers = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, entity, Transfer[].class);
            transfers = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
    public void requestBucks(AuthenticatedUser currentUser, Long userIdTo, BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Transfer newTransfer = new Transfer();
        newTransfer.setUserIdTo(userIdTo);
        newTransfer.setAmount(amount);
        newTransfer.setTransferType("Request");
        newTransfer.setTransferStatus("Pending");
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);
        try{
            restTemplate.postForObject(API_BASE_URL + "/transfers/requests", entity, Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
    public List<Transfer> viewPendingRequests(AuthenticatedUser currentUser){
        List<Transfer> transfers = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfers/requests", HttpMethod.GET, entity, Transfer[].class);
            transfers = Arrays.asList(response.getBody());

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
}
