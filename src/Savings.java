
public class Savings extends Account{

    public Savings() {

    }

    public Savings(int savingsAccountNumber, double savingsBalance) {
        super(savingsBalance,savingsAccountNumber);
    }

    public void Deposit(double addMoney){
        super.Deposit(addMoney);
    }
}
