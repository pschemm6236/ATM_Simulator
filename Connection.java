import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Programmer: Parker Schemm 
 * 
 * Purpose: The connection that links the screen with the vault, user, and
 * account classes. Contains all of the methods for user to withdraw funds, choose their account, 
 * deposit funds, transfer funds between their savings/checking account to other account, display 
 * account balance, transfer funds to different customer account, operator functionality (operator login, 
 * add money to vault, remove money from vault, display bills), view vault, and various other helper functions. 
 * The connection class is the bread and butter of the ATM system where the core logic happens. 
 * 
 * Assumption: Assume User data, account data, and vault data are already loaded into the system. 
 * Assume mostly valid inputs, but error checking is implemented incase otherwise. 
 * Lastly, assume the user knows the operator password to access the operator functionality. 
 * 
 * 
 */

public class Connection { //begin Connection 

    //Declare fields 
    private User user;
    private Account account;
    private Screen screen;
    private Vault vault;
    private ArrayList<User> users; //holds all user information
    private ArrayList<Account> accounts; //holds all account information 
    private final String operatorPassword = "cannonball";
    Scanner scan = new Scanner(System.in);

    /**
     * Partial constructor to initialize the Connection class.
     * 
     * 
     * @param screen:   Screen object for displaying messages.
     * @param users:    List of users in the system.
     * @param accounts: List of accounts associated with users.
     * @param vault:    Vault object representing the ATM's cash storage.
     */
    public Connection(Screen screen, ArrayList<User> users, ArrayList<Account> accounts, Vault vault) {
        this.screen = screen;
        this.users = users;
        this.accounts = accounts;
        this.vault = vault;
    }

    /**
     * Method to authenticate user login.
     * 
     * Checks the given username and password against the list of users.
     * Returns true if credentials match, false otherwise.
     * 
     * @param name     the name of the user wanting to log in
     * @param password the password of the user wanting to log in
     */
    public boolean login(String name, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name) && users.get(i).getPassword().equals(password)) {
                return true;
            }

        }
        return false;

    }

    /**
     * Method to withdraw funds from a user's account.
     * 
     * Prompts the user to select an account.
     * Displays account and vault balances before withdrawal.
     * Ensures valid input and sufficient funds in both the user's account and the
     * ATM vault.
     * Dispenses bills in available denominations and updates balances accordingly.
     * 
     * @param userName the name of the person who wants to withdraw funds
     */
    public void withdraw(String userName) {
        boolean validAmount = false;
        int amount = 0;

        // initialize bill denomination variables
        int hundreds = 0;
        int fifties = 0;
        int twenties = 0;
        int fives = 0;

        // prompt the user for which account to use
        int accountNum = chooseAccount(userName);

        // display both the users current account balance and vault balance before
        // transaction
        displayAccountBalance(accountNum);
        displayVaultBalance();

        // Ensure valid withdrawal amount
        while (!validAmount) {
            System.out.println(
                    "How much money would you like to withdraw (multiples of $5 only, or type 0 to exit): ");

            try {
                amount = scan.nextInt();
                scan.nextLine();

                // test if divisible by five
                if (amount % 5 != 0 || amount < 0) {
                    System.out.println("Error: Amount must be a multiple of $5 and non-negative number.");
                    continue;
                }
                // exit the menu
                else if (amount == 0) {
                    System.out.println("Zero dollars withdrawn. Exiting withdraw menu.\n");
                    return;
                }
                
                // check to ensure not over-withdrawing funds from ATM
                if (amount > vault.calculateBalance()) {
                    System.out.println("Error: ATM does not have enough cash.");
                    continue;
                }

                // loop through list of accounts and check if the requested amount is greater
                // than the accounts balance
                for (Account account : accounts) {
                    if (account.getAccountNumber() == accountNum) {
                        if (amount > account.getTotalBalance()) {
                            System.out.println("Error: Insufficient funds in your account.");
                        } else {
                            validAmount = true;
                        }
                    }
                } // end for

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        } // end while

        // method call to verify vault has enough bills for transaction
        boolean check = checkWithdrawal(amount);
        if (check == true) {
            // capture the original amount in a variable for adjusting the account balance
            int originalAmount = amount;

            // logic for withdrawing funds checking the vault
            // if the vault has funds and amount is one hundred or more
            while (amount >= 100 && vault.getHundredDollarBills() > 0) {
                amount -= 100; // deduct the new amount
                int newBillAmount = vault.getHundredDollarBills() - 1; // update vault bill count
                vault.setHundredDollarBills(newBillAmount); // use setter to push update
                hundreds++; // increment the number of hundred dollar bills used
            }
            // if the vault has funds and amount is Fifty or more
            while (amount >= 50 && vault.getFiftyDollarBills() > 0) {
                amount -= 50; //update amount
                int newBillAmount = vault.getFiftyDollarBills() - 1;// update vault bill count
                vault.setFiftyDollarBills(newBillAmount); // use setter to push update
                fifties++;// increment the number of FiftyD dollar bills used
            }
            // if the vault has funds and amount is Twenty or more
            while (amount >= 20 && vault.getTwentyDollarBills() > 0) {
                amount -= 20;//update amount
                int newBillAmount = vault.getTwentyDollarBills() - 1;// update vault bill count
                vault.setTwentyDollarBills(newBillAmount);
                twenties++;//increment variable
            }
            // if the vault has funds and amount is Twenty or more
            while (amount >= 5 && vault.getFiveDollarBills() > 0) {
                amount -= 5;//update amount
                int newBillAmount = vault.getFiveDollarBills() - 1;// update vault bill count
                vault.setFiveDollarBills(newBillAmount);// use setter to push update
                fives++; //increment variable
            }

            // logic for withdrawing funds from account
            // update totalBalance field in Account
            for (Account a : accounts) {
                if (a.getAccountNumber() == accountNum) {

                    // adjust the user accounts balance
                    double newBalance = (a.getTotalBalance() - originalAmount);
                    a.setTotalBalance(newBalance);
                }
            }

            // output the withdrawn bills
            System.out.println("Withdrawal successful!");
            System.out.println("Dispensed:");

            //check if there exists any amount of hundreds, fifties,
            //twenties, or fives, and if so then output that amount 
            //of bills to user to show how much money they withdrew
            if (hundreds > 0)
                System.out.println(hundreds + " x $100 bills");
            if (fifties > 0)
                System.out.println(fifties + " x $50 bills");
            if (twenties > 0)
                System.out.println(twenties + " x $20 bills");
            if (fives > 0)
                System.out.println(fives + " x $5 bills");

            displayAccountBalance(accountNum);
            displayVaultBalance();

        } else // else if there are not sufficient funds present in the ATM, then exit the
               // method
            return;

    } // end Withdraw method

    /**
     * Helper method to verify whether the ATM vault has sufficient bill
     * denominations
     * to dispense the requested withdrawal amount.
     * 
     * Returns:
     * - true if the requested amount can be dispensed.
     * - false if there are insufficient denominations available.
     * 
     * @param amount the inputted amount the user wants to withdraw
     */
    private boolean checkWithdrawal(int amount) {
        int tempAmount = amount;

        // Deduct as many $100 bills as possible without exceeding the available amount
        // or the vault's supply
        int maxHundreds = tempAmount / 100;
        int tempHundreds = Math.min(maxHundreds, vault.getHundredDollarBills());
        tempAmount -= tempHundreds * 100;

        // Deduct as many $50 bills as possible after handling $100 bills, considering
        // availability
        int tempFifties = Math.min(tempAmount / 50, vault.getFiftyDollarBills());
        tempAmount -= tempFifties * 50;

        // Deduct as many $20 bills as possible after handling higher denominations,
        // based on availability
        int tempTwenties = Math.min(tempAmount / 20, vault.getTwentyDollarBills());
        tempAmount -= tempTwenties * 20;

        // Deduct as many $5 bills as possible after handling larger bills, not
        // exceeding available bills
        int tempFives = Math.min(tempAmount / 5, vault.getFiveDollarBills());
        tempAmount -= tempFives * 5;

        // now check to see if there are enough bill denominations for the needed amount
        if (tempAmount > 0) {
            System.out.println(
                    "Error: ATM cannot dispense the exact requested amount due to insufficient bill denominations.");
            return false;
        }

        // return true meaning there is sufficient bill denominations left in the vault
        // for transaction
        return true;

    }// end checkWithdrawal

    /**
     * Method for user to deposit funds into either their savings/checking account.
     * User can enter either 5, 20, 50, or 100 dollar bills into their account, with
     * error checking involved.
     * 
     * @param userName
     */
    public void deposit(String userName) {
        // declare bill denomination input values
        int fivesAmount = 0;
        int twentiesAmount = 0;
        int fiftiesAmount = 0;
        int hundredsAmount = 0;

        int accountNum = chooseAccount(userName);
        displayAccountBalance(accountNum);
        displayVaultBalance();

        // declare while loop variables
        boolean moreFives = true;
        boolean moreTwenties = true;
        boolean moreFifties = true;
        boolean moreHundreds = true;

        // loop for depositing five dollar bills
        while (moreFives) {
            try {
                System.out.println("How many five dollar bills are you depositing?");
                fivesAmount = scan.nextInt();
                scan.nextLine();

                //check for negative 
                if (fivesAmount < 0) {
                    System.out.println("Error. Please input a non-negative amount of bills to deposit.");
                } 
                
                //else input is correct
                else {
                    moreFives = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        }

        // loop for depositing twenty dollar bills
        while (moreTwenties) {
            try {
                System.out.println("How many twenty dollar bills are you depositing?");
                twentiesAmount = scan.nextInt();
                scan.nextLine();

                //check for negative 
                if (twentiesAmount < 0) {
                    System.out.println("Error. Please input a non-negative amount of bills to deposit.");
                } 
                
                //else input is correct
                else {
                    moreTwenties = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        }

        // loop for depositing fifty dollar bills
        while (moreFifties) {
            try {
                System.out.println("How many fifty dollar bills are you depositing?");
                fiftiesAmount = scan.nextInt();
                scan.nextLine();

                //check for negative 
                if (fiftiesAmount < 0) {
                    System.out.println("Error. Please input a non-negative amount of bills to deposit.");
                } 
                
                //else input is correct
                else {
                    moreFifties = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        }

        // loop for depositing hundred dollar bills
        while (moreHundreds) {
            try {
                System.out.println("How many hundred dollar bills are you depositing?");
                hundredsAmount = scan.nextInt();
                scan.nextLine();

                //check for negative 
                if (hundredsAmount < 0) {
                    System.out.println("Error. Please input a non-negative amount of bills to deposit.");
                } 
                
                //else input is correct
                else {
                    moreHundreds = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        }

        // add up the total amount of bills to deposit
        int totalAmount = (fivesAmount * 5) + (twentiesAmount * 20)
                + (fiftiesAmount * 50) + (hundredsAmount * 100);

        System.out.println("Total amount depositing: $" + totalAmount);

        // set user account to new totalAmount;
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNum) {
                account.setTotalBalance(account.getTotalBalance() + totalAmount);
            }
        }

        // add bills to vault
        vault.setFiveDollarBills(vault.getFiveDollarBills() + fivesAmount);
        vault.setTwentyDollarBills(vault.getTwentyDollarBills() + twentiesAmount);
        vault.setFiftyDollarBills(vault.getFiftyDollarBills() + fiftiesAmount);
        vault.setHundredDollarBills(vault.getHundredDollarBills() + hundredsAmount);

        displayAccountBalance(accountNum);
        displayVaultBalance();
    }// end deposit method

    /**
     * Method for a user to transfer funds from either the checking account to their
     * savings
     * account, or vice versa. User chooses which account is the source account, and
     * the destination
     * account is determined based on the source. After account is chosen, the user
     * chooses how much money
     * they wish to transfer to their other account. Error checking is involved for
     * account choosing and
     * amount of money to withdraw.
     * 
     * 
     * @param userName name of the user wishing to transfer funds
     */
    public void transferBetweenAccount(String userName) {

        // variable initializations
        int destinationAccount = 0;
        int transferAmount = 0;
        boolean checking = false; // set checking to true if it is checking
        boolean more = true;

        System.out.println("Choose the source account you would would like to transfer funds from ");

        int sourceAccount = chooseAccount(userName);

        // display balances
        displayAccountBalance(sourceAccount);
        displayVaultBalance();

        // loop through the list of users
        for (User user : users) {

            // check first to see if the source account is the chosen account
            if (sourceAccount == user.getCheckingAccountNumber()) {
                checking = true; // set checking to true

                destinationAccount = user.getSavingsAccountNumber(); // assign the destination account number from
                                                                     // savings
                System.out.println("\nTransferring to Savings Account: #" + destinationAccount);
                displayAccountBalance(destinationAccount);

            }
            // else the destination account is checking
            else if (sourceAccount == user.getSavingsAccountNumber()) {
                checking = false;

                destinationAccount = user.getCheckingAccountNumber(); // assign the destination accout number from
                                                                      // checking
                System.out.println("\nTransferring to Checking Account: #" + destinationAccount);
                displayAccountBalance(destinationAccount);

            }
        } // end for loop

        //loop for transfer input 
        while (more) {
            System.out.println("\nPlease enter amount to transfer (or enter 0 to exit transfer money option): ");
            
            //error checking
            try {
                transferAmount = scan.nextInt();
                scan.nextLine();

                // check if the amount is negative, print error
                if (transferAmount < 0) {
                    System.out.println("Error. Transfer amount cannot be negative or zero.");
                }
                // exit transfer method
                else if (transferAmount == 0)
                    return;
                // else a valid # is entered
                else
                    more = false;
            } catch (InputMismatchException e) {//error checking
                System.out.println("Invalid input. Please enter a valid amount");
                scan.nextLine();
            }
        }

        // loop through list of accounts until source account found
        for (Account account : accounts) {

            // if source account is found
            if (account.getAccountNumber() == sourceAccount) {

                // check to see if amount being transferred exceeds the total account amount
                if ((account.getTotalBalance() - transferAmount) >= 0) {

                    // deduct the amount to be transferred from source account
                    account.setTotalBalance(account.getTotalBalance() - transferAmount);

                    // loop through accounts again and add the transfer amount to the desination
                    // account
                    for (Account a : accounts) {
                        if (a.getAccountNumber() == destinationAccount) {
                            a.setTotalBalance(a.getTotalBalance() + transferAmount);
                        }
                    }
                } 
                //error checking for negative 
                else {
                    System.out.println("Error: transfer will create a negative balance for account #" + sourceAccount);
                    return;
                }

            }
        } // end for loop

        // print out results of transfer and both account balances
        System.out.println("\nTransfer was successful! \n");

        // if the checking is the source account
        //display balances 
        if (checking) {
            System.out.print("Checking Account #" + sourceAccount + " - ");
            displayAccountBalance(sourceAccount);
            System.out.print("Savings Account #" + destinationAccount + " - ");
            displayAccountBalance(destinationAccount);
            System.out.println("");
        }
        // else savings is the source account
        //display balances
        else {
            System.out.print("Checking Account #" + destinationAccount + " - ");
            displayAccountBalance(destinationAccount);
            System.out.print("Savings Account #" + sourceAccount + " - ");
            displayAccountBalance(sourceAccount);
            System.out.println("");
        }
        displayVaultBalance();
    }// end transferBetweenAccounts

    /**
     * Method for customer to transfer money into account number of
     * another customer within the bank. User chooses which account they want to
     * transfer
     * funds from first, the amount, and the destination account number
     * with error checking for all user inputs and the destination account number.
     * 
     * @param userName the name of the user initiating the transaction
     */
    public void transferAcrossDiffAccounts(String userName) {
        int transferAmount = 0;
        boolean found = false;
        int destinationAccount = 0;

        System.out.println("Choose the source account you would would like to transfer funds from");

        int sourceAccount = chooseAccount(userName);

        //display balances 
        displayAccountBalance(sourceAccount);
        displayVaultBalance();

        // loop for entering a valid account number
        while (!found) {

            //error checking 
            try {
                System.out
                        .println("Please enter the account number for who you would like to receive this transfer: #");
                destinationAccount = scan.nextInt();
                scan.nextLine();

                // check to make sure account exists in the system
                // loop through the list of accounts
                for (Account x : accounts) {
                    if (x.getAccountNumber() == destinationAccount)
                        found = true;

                }

                //error checking 
                if (!found)
                    System.out.println("Error: Please input a valid account #");
            } catch (InputMismatchException e) {//error checking 
                System.out.println("Invalid input. Please enter a valid account number. ");
                scan.nextLine();
            }

        } // end while

        boolean more = true;

        // loop for user to enter correct dollar amount to transfer to destination
        // account
        while (more) {
            try {
                System.out.println("Please enter amount to transfer: ");
                transferAmount = scan.nextInt();
                scan.nextLine();


                //check for negative input 
                if(transferAmount < 0){
                    System.out.println("Error. Transfer amount must be non negative.");
                    return; 
                }

                //initial loop for accounts to find source account 
                for (Account account : accounts) {
                    if (account.getAccountNumber() == sourceAccount) {

                        // check to see if amount being transferred exceeds the total account amount
                        if ((account.getTotalBalance() - transferAmount) >= 0) {

                            // set the new balance of the person sending the money by subtracting the
                            // transfer
                            // amount checking their total balance
                            account.setTotalBalance(account.getTotalBalance() - transferAmount);

                            // loop thru accounts again, until the destination account is found
                            for (Account a : accounts) {
                                // if found
                                if (a.getAccountNumber() == destinationAccount) {
                                    // add the transfer amount to destination account
                                    a.setTotalBalance(a.getTotalBalance() + transferAmount);
                                    // display accout balances to user
                                    System.out.print(
                                            "\nTransfer was successful.\nChecking Account #" + sourceAccount + " - ");
                                    displayAccountBalance(sourceAccount);
                                    System.out.print("to destination Account #" + destinationAccount + "\n\n");
                                    displayVaultBalance();
                                    more = false; //exit loop 
                                }
                            } // end for
                        } // end if

                        //error checking
                        else {
                            System.out.println(
                                    "Error: transfer will create a negative balance for account #" + sourceAccount);
                        }
                    } // end if
                } // end for loop
            } catch (InputMismatchException e) {//error checking
                System.out.println("Invalid input! Please enter a valid number.");
                scan.nextLine(); // Clear invalid input
            }
        }
    }// end transferAcrossDiffAccounts

    /**
     * Method for the user to choose which accout they want to perform a certain
     * action on.
     * List of user account number is displayed to user, then they input which
     * account, either
     * checking or savings they would like to perform an action on. Error handling
     * for user inputs
     * is used.
     * 
     * @param userName the name of the user choosing an account
     * @return the number of the accout the user chose
     */
    public int chooseAccount(String userName) {
        int checkingNum = 0;
        int savingsNum = 0;
        int choice = 0;

        boolean more = true;

        //loop for choosing accout number 
        while (more) {
            System.out.println("Which account would you like to perform action on (either checking or savings):");

            // loop through the list of users
            for (int i = 0; i < users.size(); i++) {

                // if username is found, print out the checking and savings account numbers
                //user arraylist index and getter for both checking and savings accout number for that user
                if (users.get(i).getName().equals(userName)) {
                    System.out.println("1.) Checking Account: #" + users.get(i).getCheckingAccountNumber());
                    System.out.println("2.) Savings Account: #" + users.get(i).getSavingsAccountNumber());
                    checkingNum = users.get(i).getCheckingAccountNumber(); //save the checking number to a variable 
                    savingsNum = users.get(i).getSavingsAccountNumber(); //save the savings number to a variable 
                }
            }

            //error checking
            try {
                System.out.println("Please type either 1 or 2:");
                choice = scan.nextInt();
                scan.nextLine();

                // if user inputs a valid number
                if (choice == 1 || choice == 2) {
                    more = false;
                }

                //else invalid input 
                else {
                    System.out.println("Invalid input. Please type either 1 or 2 for checking or savings.");
                }
            } catch (InputMismatchException e) {//error checking
                System.out.println("Invalid input. Please type either 1 or 2 for checking or savings.");
                scan.nextLine();
            }

        } // end while

        //return checking 
        if (choice == 1) {
            return checkingNum;
        }
        //return savings 
        else {
            return savingsNum;
        }

    }// end chooseAccount

    /**
     * Method to display the current account's balance
     * 
     * @param accountNumber the account number of the user
     */
    public void displayAccountBalance(int accountNum) {

        //loop thru accounts list 
        for (Account a : accounts) {

            //if account is found from list 
            if (a.getAccountNumber() == accountNum) {

                //output balance 
                System.out.println("Current account balance: $" + a.getTotalBalance());
            }
        }
    }

    /**
     * Method to verify operator login. Checks user input
     * with final field in Connection class that holds operator password.
     * 
     * @return boolean indicating successful login or not
     */
    public boolean operatorLogin() {
        System.out.println("Please enter the operator password: ");
        String pass = scan.nextLine();

        //correct password input 
        if (pass.equals(operatorPassword)) {
            System.out.println("Operator login successful.\n");
            return true;
        } 
        //check for empty input 
        else if (pass.isEmpty()) {
            System.out.println("Error. Must give text input.");
            return false;
        } else { //error checking 
            System.out.println("Operator password incorrect.\n");
            return false;
        }

    }

    /*
     * Method to display the current vault total balance
     */
    public void displayVaultBalance() {
        int balance = vault.calculateBalance(); //use vault's calc balance method 
        System.out.println("Vault's current balance: $" + balance);
    }

    /*
     * Method to display each of the total bill denominations in the
     * ATM's vault.
     * 
     */
    public void displayVaultBills() {
        String outputBills = vault.toString();//use vault's toString method 
        System.out.println(outputBills); //output String 
    }

    /*
     * Method for operator to input vault bills. Input is required for each
     * denomination of five,
     * twenty, fifty, and hundred dollar bills. Error checking is implemented for
     * each input the operator
     * gives.
     * 
     */
    public void inputVaultBills() {
        boolean more = true;

        //begin input bills loop. 
        while (more) {
            inputBillsMenu();

            int choice = 0;

            //error checking
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {//error checking
                System.out.println("Error: Invalid input. Please enter a number between 1-5.");
                scan.nextLine(); // Clear the invalid input
                continue; // Restart loop
            }

            //add fives 
            if (choice == 1) {

                //error checking
                try {
                    System.out.println("How many five dollar bills are you adding:");
                    int numBills = scan.nextInt();
                    scan.nextLine();

                    //check for negative amount 
                    if (numBills < 0) {
                        System.out.println("Error. Please input non-negative amount.");
                    }

                    //else the amount is valid 
                    else {
                        vault.setFiveDollarBills(vault.getFiveDollarBills() + numBills);
                        System.out.println("Success. Addeed " + numBills + " five dollar bills to the vault.");
                    }

                } catch (InputMismatchException e) {//error checking
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }

            } 
            
            //add twenties 
            else if (choice == 2) {
                //error checking
                try {
                    System.out.println("How many twenty dollar bills are you adding:");
                    int numBills = scan.nextInt();
                    scan.nextLine();
                    //check for negative amount
                    if (numBills < 0) {
                        System.out.println("Error. Please input non-negative amount.");
                    }

                    //else the amount is valid 
                    else {
                        vault.setTwentyDollarBills(vault.getTwentyDollarBills() + numBills);
                        System.out.println("Success. Addeed " + numBills + " twenty dollar bills to the vault.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }
            } 
            
            //add fifties 
            else if (choice == 3) {
                //error checking
                try {
                    System.out.println("How many fifty dollar bills are you adding:");
                    int numBills = scan.nextInt();
                    scan.nextLine();
                    //check for negative amount
                    if (numBills < 0) {
                        System.out.println("Error. Please input non-negative amount.");
                    }

                    //else the amount is valid 
                    else {
                        vault.setFiftyDollarBills(vault.getFiftyDollarBills() + numBills);
                        System.out.println("Success. Addeed " + numBills + " fifty dollar bills to the vault.");
                    }
                } catch (InputMismatchException e) {//error checking
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }

            } 
            
            //add hundreds 
            else if (choice == 4) {

                //error checking
                try {
                    System.out.println("How many hundred dollar bills are you adding:");
                    int numBills = scan.nextInt();
                    scan.nextLine();
                    //check for negative amount
                    if (numBills < 0) {
                        System.out.println("Error. Please input non-negative amount.");
                    }

                    //else the amount is valid 
                    else {
                        vault.setHundredDollarBills(vault.getHundredDollarBills() + numBills);
                        System.out.println("Success. Addeed " + numBills + " hundred dollar bills to the vault.");
                    }
                } catch (InputMismatchException e) {//error checking
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }

            } 
            //exiting bill input menu option 
            else if (choice == 5) {
                System.out.println("Exiting input bills menu\n");
                more = false;
            } else {//error checking
                System.out.println("Error: please type a number 1-5");
            }
        } //end loop 
    }//end method 

    /**
     * Method for the operator to remove 5, 20, 50, or 100 dollar bills from the ATM's vault.
     * Operator get's the choose from the menu which bills they want to remove, and subsequently 
     * enter the amount of bills they will be removing. Error checking is implemented for all 
     * operator inputting. 
     *  
     */
    public void removeVaultBills() {
        boolean more = true;

        int choice = 0; 

        //operator remove bills menu loop 
        while (more) {

            removeBillsMenu();//display remove menu options

            //error checking for menu option choice 
            try{
                choice = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {//error checking 
                System.out.println("Error: Invalid input. Please enter a valid amount.");
                scan.nextLine(); // Clear the invalid input
            }
            

            //logic for removing five dollar bills from the vault. 
            if (choice == 1) {

                //error checking 
                try{
                    System.out.println("How many five dollar bills are you removing:");
                    int numBills = scan.nextInt();
                    scan.nextLine();
    
                    //check for negative bills input 
                    if(numBills < 0) {
                        System.out.println("Error. Cannot remove negative number of bills from vault.");
                    }

                    //check that operator is no over-withdrawing vault bills
                    else if (numBills > vault.getFiveDollarBills()) {
                        System.out.println(
                                "Error. Cannot remove more than " + vault.getFiftyDollarBills() + " five dollar bills\n");
                    } 
                    
                    //else operator has valid input for removing vault bills
                    else {
                        vault.setFiveDollarBills(vault.getFiveDollarBills() - numBills);
                        System.out.println("Success. Removed " + numBills + " five dollar bills from the vault.");
                    }
                } catch (InputMismatchException e) {//error checking 
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }
                

            }
            //logic for removing twenty dollar bills from vault 
            else if (choice == 2) {

                //error checking 
                try{
                    System.out.println("How many twenty dollar bills are you removing:");
                    int numBills = scan.nextInt();
                    scan.nextLine();

                    //check for negative bills input 
                    if(numBills < 0) {
                        System.out.println("Error. Cannot remove negative number of bills from vault.");
                    }

                    //check that operator is no over-withdrawing vault bills
                    else if (numBills > vault.getTwentyDollarBills()) {
                        System.out.println("Error. Cannot remove more than " + vault.getTwentyDollarBills()
                                + " twenty dollar bills\n");
                    } 
                    
                    //else operator has valid input for removing vault bills
                    else {
                        vault.setTwentyDollarBills(vault.getTwentyDollarBills() - numBills);
                        System.out.println("Success. Removed " + numBills + " twenty dollar bills from the vault.");
                    }
                } catch (InputMismatchException e) {//error checking 
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }
                
            } 
            //logic for removing fifty dollar bills from vault 
            else if (choice == 3) {

                //error checking 
                try{
                    System.out.println("How many fifty dollar bills are you removing:");
                    int numBills = scan.nextInt();
                    scan.nextLine();

                    //check for negative bills input 
                    if(numBills < 0) {
                        System.out.println("Error. Cannot remove negative number of bills from vault.");
                    }

                    //check that operator is no over-withdrawing vault bills
                    else if (numBills > vault.getFiftyDollarBills()) {
                        System.out.println(
                                "Error. Cannot remove more than " + vault.getFiftyDollarBills() + " fifty dollar bills\n");
                    } 
                    
                    //else operator has valid input for removing vault bills
                    else {
                        vault.setFiftyDollarBills(vault.getFiftyDollarBills() - numBills);
                        System.out.println("Success. Removed " + numBills + " fifty dollar bills from the vault.");
                    }
                } catch (InputMismatchException e) {//error checking 
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }
            } 
            //logic for removing hundred dollar bills 
            else if (choice == 4) {

                //error checking 
                try{
                    System.out.println("How many hundred dollar bills are you removing:");
                    int numBills = scan.nextInt();
                    scan.nextLine();

                    //check for negative bills input 
                    if(numBills < 0) {
                        System.out.println("Error. Cannot remove negative number of bills from vault.");
                    }

                    //check that operator is no over-withdrawing vault bills
                    else if (numBills > vault.getHundredDollarBills()) {
                        System.out.println("Error. Cannot remove more than " + vault.getHundredDollarBills()
                                + " hundred dollar bills\n");
                    } 
                    
                    //else operator has valid input for removing vault bills
                    else {
                        vault.setHundredDollarBills(vault.getHundredDollarBills() - numBills);
                        System.out.println("Success. Removed " + numBills + " hundred dollar bills from the vault.");
                    }
                } catch (InputMismatchException e) {//error checking 
                    System.out.println("Error: Invalid input. Please enter a valid amount.");
                    scan.nextLine(); // Clear the invalid input
                }
            } else if (choice == 5) { //operator exiting remove bills menu option 
                System.out.println("Exiting remove bills menu\n");
                more = false;
            } else { //error checking 
                System.out.println("Error: please type a number 1-5");
            }
        }
    } //end removeVaultBills method 

    /**
     * Private helper method to display operator input 
     * bills method 
     * 
     */
    private void inputBillsMenu() {
        System.out.println("\nBill input menu options: ");
        System.out.println("    1.) input five dollar bills ($5)");
        System.out.println("    2.) input twenty dollar bills ($20)");
        System.out.println("    3.) input fifty dollar bills ($50)");
        System.out.println("    4.) input hundred dollar bills ($100)");
        System.out.println("    5.) Stop inputting bills.");
        System.out.println("Please type a number 1-5: ");
    }

    /**
     * private helper method to display operator remove bills 
     * method. 
     */
    private void removeBillsMenu() {
        System.out.println("\nBill removal menu options: ");
        System.out.println("    1.) remove five dollar bills ($5)");
        System.out.println("    2.) remove twenty dollar bills ($20)");
        System.out.println("    3.) remove fifty dollar bills ($50)");
        System.out.println("    4.) remove hundred dollar bills ($100)");
        System.out.println("    5.) Stop removing bills.");
        System.out.println("Please type a number 1-5: ");
    }

} // end Connection class
