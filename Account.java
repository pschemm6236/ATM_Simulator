/**
 * Programmer: Parker Schemm
 * 
 * Represents a bank account (checking or savings) for a user.
 * Each account has a type, a unique account number, and a balance.
 * 
 * Assumptions:
 * - The account type is either "checking" or "savings".
 * - The total balance should be a non-negative value.
 * 
 * Input: Account type, account number, and initial balance.
 * Output: Provides account details and allows access/modification of attributes.
 */
public class Account {

    // Instance variables representing account details
    private String accountType; // Type of account (checking or savings)
    private int accountNumber;  // Unique account identifier
    private double totalBalance; // Current account balance

    /**
     * Constructs an Account object with a specified type, number, and balance.
     * 
     * @param accountType The type of account (checking or savings).
     * @param accountNumber The unique account number.
     * @param totalBalance The initial balance in the account.
     */ 
    public Account(String accountType, int accountNumber, double totalBalance){

        this.accountType = accountType; 
        this.accountNumber = accountNumber; 
        this.totalBalance = totalBalance; 

    }
     /**
     * Retrieves the type of account (checking or savings).
     * 
     * @return The account type.
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Updates the account type.
     * 
     * @param accountType The new account type.
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Retrieves the account number.
     * 
     * @return The account number.
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Updates the account number.
     * 
     * @param accountNumber The new account number.
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Retrieves the total balance of the account.
     * 
     * @return The current balance.
     */
    public double getTotalBalance() {
        return totalBalance;
    }

    /**
     * Updates the total balance of the account.
     * 
     * @param totalBalance The new balance amount.
     */
    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

}
