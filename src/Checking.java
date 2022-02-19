
public class Checking extends Account{

    public Checking() {

    }

    public Checking(int checkingAccountNumber, double checkingBalance) {
        super(checkingBalance,checkingAccountNumber);
    }

    public void Deposit(double addMoney){
        super.Deposit(addMoney);
    }

    public void Withdraw(double subtractMoney){
        super.Withdraw(subtractMoney);
    }
}
