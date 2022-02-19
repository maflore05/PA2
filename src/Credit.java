
public class Credit extends Account{
    private int creditLimit;

    public Credit() {

    }

    public Credit(int creditAccountNumber, double creditBalance, int creditLimit){
        super(creditBalance,creditAccountNumber);
        this.creditLimit = creditLimit;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

}
