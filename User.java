/**
 * Programmer: Parker Schemm 
 * 
 * Purpose: Represents a user in the ATM system. Each user has a name, 
 * associated checking and savings account numbers, a password for authentication, 
 * and a role that determines whether they are a regular user or an operator.
 * This class provides methods to access and modify user details.
 * 
 * Assumption: Each user will have a unique name, a valid checking account number, 
 * and a valid savings account number. The password is assumed to be stored securely 
 * and is only retrievable for authentication purposes. No additional security 
 * measures such as encryption are implemented in this class.
 *
 * Input: User information including name, checking account number, savings account 
 * number, password, and role.
 * Output: Allows retrieval and modification of user information through getter 
 * and setter methods.
 *
 * Algorithm: The class initializes user attributes via a constructor and provides 
 * methods to get and set each attribute. Password retrieval is limited to returning 
 * the stored password for authentication purposes.
 */
public class User {


    //field declarations 
    private String name; 
    private int checkingAccountNumber;  
    private int savingsAccountNumber;
    private String password; 
    private String role; //regular user or operator 

    /**
     * Constructs a User object with the specified details.
     * 
     * @param name The name of the user.
     * @param checkingAccountNumber The user's checking account number.
     * @param savingsAccountNumber The user's savings account number.
     * @param password The user's password.
     * @param role The role of the user (either "regular user" or "operator").
     */
    public User(String name, int checkingAccountNumber, int savingsAccountNumber, String password, String role) {
        this.name = name; 
        this.checkingAccountNumber= checkingAccountNumber;
        this.savingsAccountNumber = savingsAccountNumber;
        this.password = password;
        this.role = role;
    }

    
    /**
     * Retrieves the user's password.
     * 
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password for the user.
     * 
     * @param password The new password to be assigned.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the checking account number associated with the user.
     * 
     * @return The checking account number.
     */
    public int getCheckingAccountNumber() {
        return checkingAccountNumber;
    }

    /**
     * Updates the checking account number associated with the user.
     * 
     * @param checkingAccountNumber The new checking account number.
     */
    public void setCheckingAccountNumber(int checkingAccountNumber) {
        this.checkingAccountNumber = checkingAccountNumber;
    }

    /**
     * Retrieves the savings account number associated with the user.
     * 
     * @return The savings account number.
     */
    public int getSavingsAccountNumber() {
        return savingsAccountNumber;
    }

    /**
     * Updates the savings account number associated with the user.
     * 
     * @param savingsAccountNumber The new savings account number.
     */
    public void setSavingsAccountNumber(int savingsAccountNumber) {
        this.savingsAccountNumber = savingsAccountNumber;
    }

    /**
     * Retrieves the role of the user (e.g., "regular user" or "operator").
     * 
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Updates the role of the user.
     * 
     * @param role The new role to be assigned.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the name of the user.
     * 
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the user.
     * 
     * @param name The new name to be assigned.
     */
    public void setName(String name) {
        this.name = name;
    }
}
