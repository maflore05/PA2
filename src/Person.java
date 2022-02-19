
public class Person {
    private String name;
    private String address;
    private String DOB;
    private String phoneNumber;
    private int creditScore;

    public Person() {

    }

    public Person(String name, String address, String DOB, String phoneNumber, int creditScore){
        this.name = name;
        this.address = address;
        this.DOB = DOB;
        this.phoneNumber = phoneNumber;
        this. creditScore = creditScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dOB) {
        DOB = dOB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void updateCredit(int change) {
        if(change<0) {
            this.creditScore-=change;
        }else {
            this.creditScore+=change;
        }
    }
}

