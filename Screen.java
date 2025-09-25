import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Programmer: Parker Schemm 
 * 
 * Purpose: The Screen class handles user interaction with the ATM system. It prompts users 
 * to enter their credentials, logs them into their account, and provides a menu for selecting 
 * various ATM operations such as withdrawing, depositing, transferring funds, and checking balances. 
 * It also allows operators to access vault-related functions.
 *
 * Assumption: The user inputs their first name and a password correctly. The ATM system assumes
 * that the username and password are valid and stored in the system. The user will input 
 * integer values when selecting options from the menu, but error handling is implemented for 
 * incorrect inputs. The program expects that the Connection object (c) properly handles 
 * authentication and account transactions.
 *
 * Input: User's first name and password for login. Numeric selections for menu options. 
 * Output: Confirmation messages for successful transactions, errors for invalid inputs, 
 * and menu options for interaction.
 *
 * Algorithm: Continuously prompt the user for login details. If valid, display the user menu 
 * and wait for input. Based on the user's selection, call the corresponding Connection 
 * object methods to perform transactions or retrieve information. Implement input validation 
 * to handle non-integer values and incorrect menu selections. 
 */

public class Screen {

    Scanner scan = new Scanner(System.in);

    /**
     * Construct Screen object
     * 
     * @param scan that reads text from a character-input stream
     */
    public Screen(Scanner scan) {
        this.scan = scan;
    }

    /* 
     * Runs the main ATM loop. Prompts users for login credentials, verifies authentication,
     * and then provides an interface for selecting ATM operations.
     * If the user is an operator, additional menu options for vault management are available.
     * Input validation is included to ensure correct data types are entered.
     * input: Connection object (c) which handles authentication and transactions.
     * output: Messages confirming successful transactions, errors for invalid input.
     */
    public void run(Connection c) {

        boolean atmLoop = true;

        //Primary ATM program loop 
        while (atmLoop) {
            
            System.out.println(
                    "Please enter the name listed under your account (first name only, or type letter q to quit): ");
            String username = scan.nextLine();
            if (username.equals("q")) //logif for program exit 
                System.exit(0);
            //check that username is empty 
            if (username.isEmpty()) {
                System.out.println("Error. Name cannot be empty. Please try again.");
                continue;
            }
            System.out.println("Please enter your account password: ");
            String password = scan.nextLine();
            //check that password is empty
            if (password.isEmpty()) {
                System.out.println("Error. Password cannot be empty. Please try again.");
                continue;
            }

            // Authenticate user login
            boolean loginSuccussful = c.login(username, password);

            //if user has valid credentials 
            if (loginSuccussful) {
                System.out.println("Login was successful.\n");
                System.out.println("Welcome to the ATM system.\n");

                boolean more = true;
                //loop for the ATM menu 
                while (more) {
                    outputUserMenu(); //show menu 

                    //error checking
                    try {
                        int input = scan.nextInt();
                        scan.nextLine(); // Consume the newline character left by nextInt()

                        // Perform the selected operation based on user input
                        //withdraw funds menu option  
                        if (input == 1) {
                            c.withdraw(username);
                        } 
                        //deposit funds menu option 
                        else if (input == 2) {
                            c.deposit(username);
                        } 
                        //transfer funds between accounts 
                        else if (input == 3) {
                            c.transferBetweenAccount(username);
                        } 
                        //transfer funds to different accounts 
                        else if (input == 4) {
                            c.transferAcrossDiffAccounts(username);
                        } 
                        //view account balance 
                        else if (input == 5) {
                            int accountNum = c.chooseAccount(username);
                            c.displayAccountBalance(accountNum);
                        } 
                        //view vault balance
                        else if (input == 6) {
                            boolean verified = c.operatorLogin();
                            if (verified) {
                                c.displayVaultBalance();
                            }
                        } 
                        //operator mode option, verify login first, then show menu options if verified 
                        else if (input == 7) {
                            //verify that the operator password is correct 
                            boolean verified = c.operatorLogin();
                            boolean moreOperator = true;

                            //if the operator is verified 
                            if (verified) {

                                //operator menu loop 
                                while (moreOperator) {
                                    outputOperatorMenu(); //show menu 

                                    //error checking 
                                    try {
                                        int operatorInput = scan.nextInt();
                                        scan.nextLine();

                                        //show bills
                                        if (operatorInput == 1) {
                                            c.displayVaultBills();
                                        } 
                                        //option for adding bills
                                        else if (operatorInput == 2) {
                                            c.inputVaultBills();
                                        } 
                                        //option for removing bills
                                        else if (operatorInput == 3) {
                                            c.removeVaultBills();
                                        } 
                                        //exit menu option 
                                        else if (operatorInput == 4) {
                                            System.out.println("Exiting the operator menu\n");
                                            moreOperator = false;
                                        } 
                                        //error checking 
                                        else {
                                            System.out.println("Error: please type a number 1-4");
                                        }
                                    } catch (InputMismatchException e) { //error checking
                                        System.out.println("Invalid input! Please enter a valid number.");
                                        scan.nextLine(); // Clear invalid input
                                    }

                                } // end while more operator
                            } // end if
                        } // end else if

                        //menu option for user to log out of system, but not exit program 
                        else if (input == 8) {
                            loginSuccussful = false;
                            more = false;
                        } 
                        //exit program 
                        else if (input == 9)
                            System.exit(0);

                        //error checking                          
                        else {
                            System.out.println("Invalid input! Please enter a valid number. ");
                        }
                    } catch (InputMismatchException e) {//error checking
                        System.out.println("Invalid input! Please enter a valid number.");
                        scan.nextLine(); // Clear invalid input
                    }

                } // end while(more)
            } // end if
            else {//error checking
                System.out.println("Login Failed. Please try again.");
                atmLoop = true;
            }
        } // end while(atmLoop)

    } //end run method 

     /*
     * Displays the ATM user menu with available operations. Users can select 
     * options to withdraw, deposit, transfer funds, check balances, or exit the system.
     * input: none
     * output: Printed menu with numbered options.
     */
    private void outputUserMenu() {

        System.out.println("\nUser menu options: ");
        System.out.println("    1.) withdraw money from a checking or savings account");
        System.out.println("    2.) deposit money into a checking or savings account");
        System.out.println("    3.) transfer money between checking/savings accounts");
        System.out.println("    4.) transfer money to checking/savings account of another customer");
        System.out.println("    5.) display current account balance");
        System.out.println("    6.) display current vault balance");
        System.out.println("    7.) Enter operator mode");
        System.out.println("    8.) Log out of ATM system");
        System.out.println("    9.) Exit ATM program");
        System.out.println("Please type a number 1-9: ");
    }

    /*
    * Displays the operator menu with options to view, add, or remove vault bills.
    * input: none
    * output: Printed menu with numbered options for operator-specific actions.
    */
    private void outputOperatorMenu() {
        System.out.println("\nOperator menu options: ");
        System.out.println("    1.) Display the number of each type of bill");
        System.out.println("    2.) Put a given number of money bills in the ATM");
        System.out.println("    3.) Remove a given number of money bills from the ATM");
        System.out.println("    4.) Exit operator mode");
        System.out.println("Please type a number 1-4: ");
    }

}