# ATM_Simulator
To run this program, simply compile the ATMDriver.java file, then run that same file. After you run that file, you will be 
prompted for the username and password. I made the login process fairly simple: use either "Bobby" or "Hansel" as the username (if 
you like, or any of the 8 other available usernames in the driver program, under the loadUserData () method), and the password is 
"password" for all 10 user accounts. I understand this is not realistic, but for this program it was easiest for me to test it 
with simple passwords. 

After you log in, you will be shown the welcome screen and menu options. For withdrawal option number 1, first choose an account 
by typing 1 or 2, then select a valid amount to withdraw, then the changes will be reflected in both the account and vault balances. 
As requested, the vault balance is shown before and after each transaction that occurs. The same logic applies for menu option 2:
depositing money into the account. Choose account, then select valid amount to deposit for each prompt of bill denominations (fives,
twenties, fifties, or hundreds). For transfer option number 3, simply select the account for the source of the transfer, then 
the destination accout is automatically selected as the destination, since each user only has two accounts each. For option number 
four, transferring to someone else in the bank system, choose the source account first, then type in the account number of someone
you know you want to transfer funds to (for example log in as Bobby, with checking #1001, then transfer to Hansel with savings
#2002). Option five is simply for choosing an account and viewing its balance. For option six, first you must enter the operator password
to view the vault balance. The operator password is a final field in the connection class, and the password is "cannonball" for 
any operator to login. 

For option seven, you must once again log in as an operator to gain access to the operator menu options. Once logged in, 
you are free to view bill denomination amounts, add bills into the vault, remove bills from the vault, or exit the operator mode. 
For adding or removing bills from the vault, a seperate menu pops up for each option, showing a list of bills (fives, twenties, fifties
hundreds) that you can add or remove. Option eight allows whoever is currently logged into the ATM to log out of the system, 
but not exit the program. Option nine is for exiting the ATM program. 

I hope you enjoy using my ATM! 

-Programmer: Parker Schemm.
