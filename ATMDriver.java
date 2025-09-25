import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programmer: Parker Schemm
 * 
 * The driver class for the ATM system. 
 * Initializes the necessary components and starts the ATM application.
 * 
 * Responsibilities:
 * - Sets up the scanner for user input.
 * - Initializes users, accounts, and vault money.
 * - Loads predefined user and account data.
 * - Starts the program through the Screen class.
 */
public class ATMDriver{

    //Main method initializes and starts the ATM system. 
    public static void main(String[] args){//begin main 
        
        Scanner scan = new Scanner(System.in);// Create a scanner to handle user input
 

        //initialize ArrayList of users and accounts 
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Account> accounts = new ArrayList<Account>();

        //initialize vault money 
        Vault v = new Vault(100, 100, 100, 100);        
        
        // Create a screen object to handle user interactions
        Screen s = new Screen(scan);

        // Establish a connection between the screen, users, accounts, and vault
        Connection c = new Connection(s, users, accounts, v);

        // Load predefined user and account data into the system
        loadUserData(users, accounts);
        
        s.run(c); //run the program via the screen class

        scan.close();
   
    }

    /**
     * Loads predefined users and their corresponding accounts into the system.
     * 
     * @param u The list of users.
     * @param a The list of accounts.
     */
    private static void loadUserData(ArrayList<User> u, ArrayList<Account> a){
        //load each user into system 
        u.add(new User("Bobby", 1001, 1002, "password", "regular user"));
        u.add(new User("Hansel", 2001, 2002, "password", "regular user"));
        u.add(new User("Dillon", 3001, 3002, "password", "regular user"));
        u.add(new User("Tim", 4001, 4002, "password", "regular user"));
        u.add(new User("Seth", 5001, 5002, "password", "regular user"));
        u.add(new User("Fred", 6001, 6002, "password", "regular user"));
        u.add(new User("Chad", 7001, 7002, "password", "regular user"));
        u.add(new User("Dave", 8001, 8002, "password", "regular user"));
        u.add(new User("Greg", 9001, 9002, "password", "regular user"));
        u.add(new User("Yosuf", 10001, 10002, "password", "regular user"));

        //load each user's accounts into system 
        a.add(new Account("Checking", 1001, 23));
        a.add(new Account("Savings", 1002, 1400));
        a.add(new Account("Checking", 2001, 23));
        a.add(new Account("Savings", 2002, 2032)); 
        a.add(new Account("Checking", 3001, 150));
        a.add(new Account("Savings", 3002, 2092)); 
        a.add(new Account("Checking", 4001, 323));
        a.add(new Account("Savings", 4002, 1032));
        a.add(new Account("Checking", 5001, 13));
        a.add(new Account("Savings", 5002, 2032));
        a.add(new Account("Checking", 6001, 333));
        a.add(new Account("Savings", 6002, 3032)); 
        a.add(new Account("Checking", 7001, 723));
        a.add(new Account("Savings", 7002, 27032));
        a.add(new Account("Checking", 8001, 23));
        a.add(new Account("Savings", 8002, 132));   
        a.add(new Account("Checking", 9001, 83));
        a.add(new Account("Savings", 9002, 2232)); 
        a.add(new Account("Checking", 10001, 2983));
        a.add(new Account("Savings", 10002, 9932)); 

    }//end loadUserData 

} //end ATMDriver 


