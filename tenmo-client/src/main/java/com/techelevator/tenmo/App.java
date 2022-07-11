package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticatedUser currentUser;
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                consoleService.printUserList(accountService.getAllAvailableUsers(currentUser));
                int userIdTo = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");
                BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");
                sendBucks(userIdTo, amount);
            } else if (menuSelection == 5) {
                consoleService.printUserList(accountService.getAllAvailableUsers(currentUser));
                int userIdRequestTo = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel):");
                BigDecimal requestAmount = consoleService.promptForBigDecimal("Enter amount:");
                requestBucks(userIdRequestTo, requestAmount);
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        Account account = accountService.getAccount(currentUser);
        System.out.println("Your current account balance is: $"+ account.getBalance());
	}

	private void viewTransferHistory() {
        Account account = accountService.getAccount(currentUser);
        List<Transfer> newTransfers = transferService.getAllTransfers(currentUser);
        consoleService.printTransferList(newTransfers, account.getAccountId());
        int transferId =  consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel):");
        if (newTransfers != null) {
            for (Transfer transfer : newTransfers) {
                if (transferId == transfer.getTransferId()) {
                    consoleService.printTransferDetails(transfer, account.getAccountId(), currentUser.getUser().getUsername());
                }
            }
        }
	}

	private void viewPendingRequests() {
        Account account = accountService.getAccount(currentUser);
        List<Transfer> newTransfers = transferService.viewPendingRequests(currentUser);
        consoleService.viewPendingRequests(newTransfers);
        int transferId =  consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel):");
        consoleService.printApproveOrRejectMenu();
        int menuOption = consoleService.promptForInt("Please choose an option:");
        for (Transfer transfer : newTransfers) {
            if (transferId == transfer.getTransferId()) {
                if(menuOption == 1){
                    transferService.approvePendingRequest(currentUser, transfer);
                }
                else if (menuOption == 2){
                    transferService.rejectPendingRequest(currentUser, transfer);
                }
                else break;
            }
        }

	}

	private void sendBucks(long userIdTo, BigDecimal amount) {
        BigDecimal currentBalance = accountService.getAccount(currentUser).getBalance();

        if(currentUser.getUser().getId().equals(userIdTo)){
            consoleService.printFeedback("you cannot send monies to yourself");
        }
        else if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            consoleService.printFeedback("please put in valid amount");
        }
        else if (amount.compareTo(currentBalance) == 1) {
            consoleService.printFeedback("please enter amount not greater than your balance");
        }
        else transferService.sendBucks(currentUser, userIdTo, amount);

		
	}

	private void requestBucks(long userIdTo, BigDecimal amount) {
        if(currentUser.getUser().getId().equals(userIdTo)){
            consoleService.printFeedback("you cannot send monies to yourself");
        }
        else if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            consoleService.printFeedback("please put in valid amount");
        }
        else transferService.requestBucks(currentUser, userIdTo, amount);
	}

}
