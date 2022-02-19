
public class Customer extends Person {
    private Credit creditAccount;
    private Checking checkingAccount;
    private Savings savingsAccount;

    public Customer() {

    }

    public Customer(String name, String address, String DOB, String phoneNumber, int creditScore, Credit creditAccount, Checking checkingAccount, Savings savingsAccount){
        super(name, address, DOB, phoneNumber, creditScore);
        this.creditAccount = creditAccount;
        this.checkingAccount = checkingAccount;
        this.savingsAccount = savingsAccount;
    }

    public Savings getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(Savings savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public Checking getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(Checking checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public Credit getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Credit creditAccount) {
        this.creditAccount = creditAccount;
    }
}
