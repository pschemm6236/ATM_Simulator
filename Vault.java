/**
 *
 * Programmer: Parker Schemm 
 * 
 * Purpose: Represents the cash vault in an ATM system. The vault contains 
 * various denominations of bills, and this class provides functionality 
 * to calculate the total balance based on the stored cash.
 * 
 * Assumption: The number of each type of bill is non-negative. 
 * The vault only contains $100, $50, $20, and $5 bills.
 * 
 * Input: The number of $100, $50, $20, and $5 bills stored in the vault.
 * Output: Provides access to the stored cash counts, allows updating 
 * of the values, and calculates the total cash balance.
 * 
 * Algorithm: The class initializes bill counts via a constructor 
 * and provides getter and setter methods for each denomination. 
 * The `calculateBalance` method computes the total cash available 
 * based on the stored denominations.
 */
public class Vault{


    // Field declarations representing different bill denominations in the vault
    private int hundredDollarBills;
    private int fiftyDollarBills;
    private int twentyDollarBills;
    private int fiveDollarBills;

    /**
     * Constructs a Vault object with the specified number of bills.
     * 
     * @param hundreds The number of $100 bills.
     * @param fifties The number of $50 bills.
     * @param twenties The number of $20 bills.
     * @param fives The number of $5 bills.
     */
    public Vault(int hundreds, int fifties, int twenties, int fives) {
        this.hundredDollarBills = hundreds;
        this.fiftyDollarBills = fifties;
        this.twentyDollarBills = twenties;
        this.fiveDollarBills = fives;
    }

    /**
     * Calculates the total balance of cash stored in the vault.
     * 
     * @return The total dollar amount available in the vault.
     */
    public int calculateBalance() {
        return (hundredDollarBills * 100) +
               (fiftyDollarBills * 50) +
               (twentyDollarBills * 20) +
               (fiveDollarBills * 5);
    }

    /**
     * Retrieves the number of $100 bills in the vault.
     * 
     * @return The count of $100 bills.
     */
    public int getHundredDollarBills() {
        return hundredDollarBills;
    }

    /**
     * Updates the number of $100 bills in the vault.
     * 
     * @param hundredDollarBills The new count of $100 bills.
     */
    public void setHundredDollarBills(int hundredDollarBills) {
        this.hundredDollarBills = hundredDollarBills;
    }

    /**
     * Retrieves the number of $50 bills in the vault.
     * 
     * @return The count of $50 bills.
     */
    public int getFiftyDollarBills() {
        return fiftyDollarBills;
    }

    /**
     * Updates the number of $50 bills in the vault.
     * 
     * @param fiftyDollarBills The new count of $50 bills.
     */
    public void setFiftyDollarBills(int fiftyDollarBills) {
        this.fiftyDollarBills = fiftyDollarBills;
    }

    /**
     * Retrieves the number of $20 bills in the vault.
     * 
     * @return The count of $20 bills.
     */
    public int getTwentyDollarBills() {
        return twentyDollarBills;
    }

    /**
     * Updates the number of $20 bills in the vault.
     * 
     * @param twentyDollarBills The new count of $20 bills.
     */
    public void setTwentyDollarBills(int twentyDollarBills) {
        this.twentyDollarBills = twentyDollarBills;
    }

    /**
     * Retrieves the number of $5 bills in the vault.
     * 
     * @return The count of $5 bills.
     */
    public int getFiveDollarBills() {
        return fiveDollarBills;
    }

    /**
     * Updates the number of $5 bills in the vault.
     * 
     * @param fiveDollarBills The new count of $5 bills.
     */
    public void setFiveDollarBills(int fiveDollarBills) {
        this.fiveDollarBills = fiveDollarBills;
    }

    /**
     * Returns a formatted string representation of the vault contents.
     * 
     * @return A string displaying the count of each bill denomination.
     */
    @Override
    public String toString() {
        return "Vault Bills:\n   Hundred Dollar Bills ($100) = " + getHundredDollarBills() + "\n   Fifty Dollar Bills ($50) = "
                + getFiftyDollarBills() + "\n   Twenty Dollar Bills ($20) = " + getTwentyDollarBills()
                + "\n   Five Dollar Bills ($5) = " + getFiveDollarBills() + "\n";
    }
    

 
    

}
