package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printUserList(List<User> userList) {
        System.out.println("-------------------------------------------\n" +
                "Users\n" +
                "ID                 Name\n" +
                "-------------------------------------------");

        if (userList != null) {
            for (User user : userList) {
                System.out.println(user.getId() + "               " + user.getUsername());
            }
        }
        System.out.println("---------");
        System.out.println();
    }

    public void printFeedback(String message) {
        System.out.println(message);
    }

    public void printTransferList(List<Transfer> transfers, Long accountId) {
        System.out.println("-------------------------------------------\n" +
                "Transfers\n" +
                "ID            From/To            Amount\n" +
                "-------------------------------------------");
        if (transfers != null) {
            for (Transfer transfer : transfers) {
                String fromOrTo;
                if (!accountId.equals(transfer.getAccountFrom())) {
                    fromOrTo = "From:  ";
                } else fromOrTo = "To:    ";
                System.out.print(transfer.getTransferId() + "          " + fromOrTo);
                System.out.printf("%-10s $ %6.2f", transfer.getUserNameTo(), transfer.getAmount());
                System.out.println();
            }
            System.out.println("---------");
        }
    }
    public void printTransferDetails (Transfer transfer, Long accountId, String username){

                System.out.println("--------------------------------------------\n" +
                        "Transfer Details\n" +
                        "--------------------------------------------\n" +
                        "Id: " + transfer.getTransferId() + "\n" +
                        "From: " + (accountId.equals(transfer.getAccountFrom())?username:transfer.getUsername()) + "\n" +
                        "To: " + (accountId.equals(transfer.getAccountTo())?username:transfer.getUsername())  + "\n" +
                        "Type: " + transfer.getTransferType()     + "\n" +
                        "Status: " + transfer.getTransferStatus()  + "\n" +
                        "Amount: $" + transfer.getAmount());
        }

    public void viewPendingRequests(List<Transfer> transfers) {
        System.out.println("-------------------------------------------\n" +
                "Pending Transfers\n" +
                "ID              To            Amount\n" +
                "-------------------------------------------");
        if (transfers != null) {
            for (Transfer transfer : transfers) {

                System.out.print(transfer.getTransferId() + "          " );
                System.out.printf("%-10s $ %6.2f", transfer.getUserNameTo(), transfer.getAmount());
                System.out.println();
            }
            System.out.println("---------");
        }
    }
    public void printApproveOrRejectMenu (){
        System.out.println("1: Approve\n" +
                "2: Reject\n" +
                "0: Don't approve or reject\n" +
                "---------\n" );
    }
}
