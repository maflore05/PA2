
public class Account {
    private double balance;
    private int accountNumber;

    public Account() {

    }

    public Account(double balance, int accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void Deposit(double addMoney) {
        this.balance+=addMoney;
    }

    public void Withdraw(double subtractMoney) {
        this.balance-=subtractMoney;
    }

}
