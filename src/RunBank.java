import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.io.FileWriter;

public class RunBank {
    public static void main(String args[]) {
        File logFile = new File("logFile.txt");
        List<String> logs = new ArrayList<>();

        List<String[]> customerData = null;
        try {
            customerData = readData("Bank Customers 2.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> itemData = null;
        try {
            itemData = readData("Miner Mall.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String[] temp = customers.get(5);
//        System.out.println(temp[10]);
//        System.out.println(customers.size());
        Customer[] customers = new Customer[customerData.size()];
        for(int i=1;i<customerData.size();i++) {
            String temp[] = customerData.get(i);
            String name = temp[1]+" "+temp[2];
            String address = temp[3] + ", " + temp[4] + ", " + temp[5] + ", " + temp[6];
            String phoneNumber = temp[7];
            String DOB = temp[8];
            int checkingAccountNumber = Integer.parseInt(temp[9]);
            double checkingBalance = Double.parseDouble(temp[10]);
            int savingsAccountNumber = Integer.parseInt(temp[11]);
            double savingsBalance = Double.parseDouble(temp[12]);
            int creditAccountNumber = Integer.parseInt(temp[13]);
            double creditBalance = Double.parseDouble(temp[14]);
            int creditScore = Integer.parseInt(temp[15]);
            int creditLimit = Integer.parseInt(temp[16]);

            Credit creditAccount = new Credit(creditAccountNumber, creditBalance, creditLimit);
            Checking checkingAccount = new Checking(checkingAccountNumber, checkingBalance);
            Savings savingsAccount = new Savings(savingsAccountNumber, savingsBalance);
            customers[i] = new Customer(name, address, DOB, phoneNumber, creditScore, creditAccount, checkingAccount, savingsAccount);
        }
        Item[] Items = new Item[19];
        for(int i=1;i<19;i++){
            String temp[] = itemData.get(i);
            String name = temp[1];
            double price = Double.parseDouble(temp[2]);
            Items[i] = new Item(name, price);
        }

        //Ask if user or manager
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Miners Bank");
        System.out.println("Are you a customer or manager\n1. Customer\n2. Manager");
        int choice = input.nextInt();
        input.nextLine();
        if(choice==1){
            System.out.println("Please enter your name");
            String userName = input.nextLine();
            int userID = doesAccountExist(userName, customers);
            if(userID==0){
                System.out.println("Sorry, you do not have an account with us");
            }else{
                boolean keepRunning = true;
                while(keepRunning){
                    System.out.println("What account would you like to access?\n1. Checking\n2. Credit\n3. Savings");
                    int accountChoice = input.nextInt();
                    Checking checkingAccount = customers[userID].getCheckingAccount();
                    Credit creditAccount = customers[userID].getCreditAccount();
                    Savings savingsAccount = customers[userID].getSavingsAccount();
                    if(accountChoice==1){
                        System.out.println("What would you like to do?\n1. View Balance\n2. Deposit\n3. Withdraw\n4. Transfer money\n5. Send money\n6. Buy products\n7. Log off");
                        choice = input.nextInt();
                        if(choice==1) {    //view balance
                            System.out.println(checkingAccount.getBalance());
                        }else if(choice==2){
                            System.out.println("How much would you like to deposit");
                            double depositAmount = input.nextDouble();
                            checkingAccount.Deposit(depositAmount);
                            System.out.println("Deposit of "+depositAmount+" was successful");
                        } else if(choice==3){
                            System.out.println("How much would you like to withdraw");
                            double withdrawAmount = input.nextDouble();
                            checkingAccount.Withdraw(withdrawAmount);
                            System.out.println("Withdrawal of "+withdrawAmount+" was successful");
                        } else if(choice==4){
                            System.out.println("What account would you like to transfer money to\n1. Credit\n2. Savings");
                            int transferChoice = input.nextInt();
                            System.out.println("How much would you like to transfer");
                            double transferAmount = input.nextDouble();
                            if(transferChoice==1){
                                checkingAccount.Withdraw(transferAmount);
                                creditAccount.Withdraw(transferAmount);
                                System.out.println("Payment of "+transferAmount+" to your credit account was successful");
                            } else if(transferChoice==2) {
                                checkingAccount.Withdraw(transferAmount);
                                savingsAccount.Deposit(transferAmount);
                                System.out.println("Transfer of "+transferAmount+" from your checking to your savings account was successful");
                            }
                        } else if(choice==5){
                            input.nextLine();
                            System.out.println("Who would you like to send money to");
                            String sendTo = input.nextLine();
                            System.out.println("How much would you like to send");
                            double sendAmount = input.nextDouble();
                            int elseID = doesAccountExist(sendTo, customers);
                            if(elseID==0){
                                System.out.println("Sorry, "+sendTo+" does not have an account");
                                continue;
                            }
                            Checking otherUserChecking = customers[elseID].getCheckingAccount();
                            payWithChecking(sendAmount,checkingAccount,otherUserChecking);
                        } else if(choice==6){
                            for(int i=1;i<Items.length;i++){
                                System.out.println(i+". Product: "+Items[i].getName()+" Price: "+Items[i].getPrice());
                            }
                            System.out.println("Please Select the item you would like to buy");
                            int buyItem = input.nextInt();
                            checkingAccount.Withdraw(Items[buyItem].getPrice());
                            System.out.println("You have successfully bought "+Items[buyItem].getName());
                        } else if(choice==7){
                            System.out.println("Thank you!");
                            keepRunning = false;
                        }

                    } else if(accountChoice==2){
                        System.out.println("What would you like to do?\n1. View Balance\n2. Pay\n3. Withdraw\n4. Transfer money\n5. Send money\n6. Buy products\n7. Log off");
                        choice = input.nextInt();
                        if(choice==1) {    //view balance
                            System.out.println(creditAccount.getBalance());
                        }else if(choice==2){
                            System.out.println("How much would you like to deposit");
                            double depositAmount = input.nextDouble();
                            creditAccount.Deposit(depositAmount);
                            System.out.println("Deposit of "+depositAmount+" was successful");
                        } else if(choice==3){
                            System.out.println("How much would you like to withdraw");
                            double withdrawAmount = input.nextDouble();
                            creditAccount.Withdraw(withdrawAmount);
                            System.out.println("Withdrawal of "+withdrawAmount+" was successful");
                        } else if(choice==4){
                            System.out.println("What account would you like to transfer money to\n1. Checking\n2. Savings");
                            int transferChoice = input.nextInt();
                            System.out.println("How much would you like to transfer");
                            double transferAmount = input.nextDouble();
                            if(transferChoice==1){
                                creditAccount.Withdraw(transferAmount);
                                checkingAccount.Deposit(transferAmount);
                                System.out.println("Payment of "+transferAmount+" to your credit account was successful");
                            } else if(transferChoice==2) {
                                creditAccount.Withdraw(transferAmount);
                                savingsAccount.Deposit(transferAmount);
                                System.out.println("Transfer of "+transferAmount+" from your checking to your savings account was successful");
                            }
                        } else if(choice==5){
                            input.nextLine();
                            System.out.println("Who would you like to send money to");
                            String sendTo = input.nextLine();
                            System.out.println("How much would you like to send");
                            double sendAmount = input.nextDouble();
                            int elseID = doesAccountExist(sendTo, customers);
                            if(elseID==0){
                                System.out.println("Sorry, "+sendTo+" does not have an account");
                                continue;
                            }
                            Checking otherUserChecking = customers[elseID].getCheckingAccount();
                            payWithCredit(sendAmount,creditAccount,otherUserChecking);
                        } else if(choice==6){
                            for(int i=1;i<Items.length;i++){
                                System.out.println(i+". Product: "+Items[i].getName()+" Price: "+Items[i].getPrice());
                            }
                            System.out.println("Please Select the item you would like to buy");
                            int buyItem = input.nextInt();
                            creditAccount.Withdraw(Items[buyItem].getPrice());
                            System.out.println("You have successfully bought "+Items[buyItem].getName());
                        } else if(choice==7){
                            System.out.println("Thank you!");
                            keepRunning = false;
                        }
                    } else if(accountChoice==3){
                        System.out.println("What would you like to do?\n1. View Balance\n2. Deposit\n3. Withdraw\n4. Transfer money\n5. Log off");
                        choice = input.nextInt();
                        if(choice==1) {    //view balance
                            System.out.println(savingsAccount.getBalance());
                        }else if(choice==2){
                            System.out.println("How much would you like to deposit");
                            double depositAmount = input.nextDouble();
                            savingsAccount.Deposit(depositAmount);
                            System.out.println("Deposit of "+depositAmount+" was successful");
                        } else if(choice==3){
                            System.out.println("How much would you like to withdraw");
                            double withdrawAmount = input.nextDouble();
                            savingsAccount.Withdraw(withdrawAmount);
                            System.out.println("Withdrawal of "+withdrawAmount+" was successful");
                        }else if(choice==4) {
                            System.out.println("You are currently transferring money to your checking account");
                            System.out.println("How much would you like to transfer");
                            double transferAmount = input.nextDouble();
                            savingsAccount.Withdraw(transferAmount);
                            checkingAccount.Deposit(transferAmount);
                        }else if(choice==5){
                            System.out.println("Thank you!");
                            keepRunning = false;
                        }
                    }
                    System.out.println("Please select an appropriate choice");
                }
            }
        }
        else if(choice==2){
            System.out.println("Welcome!\n1. Inquire account by name\n2. Inquire account by type/number");
            int inquireChoice = input.nextInt();
            input.nextLine();
            if(inquireChoice==1){
                System.out.println("Who’s account would you like to inquire about?");
                String searchName = input.nextLine();
                int accountId = doesAccountExist(searchName,customers);
                if(accountId==0){
                    System.out.println("Sorry, that account does not exist");
                } else{
                    printInfo(accountId,customers);
                }
            } else if(inquireChoice==2){
                System.out.println("What account type\n1. Checking\n2. Credit\n3. Savings");
                int accountType = input.nextInt();
                System.out.println("What is the account number");
                int accountNumber = input.nextInt();
                boolean found = false;
                if(accountType==1){
                    for(int i=1;i<customers.length;i++){
                        if(customers[i].getCheckingAccount().getAccountNumber()==accountNumber){
                            printInfo(i,customers);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        System.out.println("Sorry, that account number does not exist");
                    }
                }else if(accountType==2){
                    for(int i=1;i<customers.length;i++){
                        if(customers[i].getCreditAccount().getAccountNumber()==accountNumber){
                            printInfo(i,customers);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        System.out.println("Sorry, that account number does not exist");
                    }
                }else if(accountType==3){
                    for(int i=1;i<customers.length;i++){
                        if(customers[i].getSavingsAccount().getAccountNumber()==accountNumber){
                            printInfo(i,customers);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        System.out.println("Sorry, that account number does not exist");
                    }
                }
            }
        }


    }

    public static List<String[]> readData(String fileName) throws FileNotFoundException {
        int count = 0;
        File file = new File(fileName);
        Scanner scnr = new Scanner(file);
        List<String[]> content = new ArrayList<>();
        while(scnr.hasNextLine()) {
            String line = scnr.nextLine();
            content.add(line.split(","));
//            System.out.println(line);
        }
//        System.out.println(content);
        return content;
    }

    static int doesAccountExist(String userNameIn, Customer[] customers){
        for(int i=1;i< customers.length;i++){
            String idName = customers[i].getName();
            if(userNameIn.equalsIgnoreCase(idName)){
                return i;
            }
        }
        return 0;
    }

    static void payWithCredit(double amount, Credit creditAccount, Checking otherUserChecking){
        if((creditAccount.getBalance()+amount)>creditAccount.getCreditLimit()){
            System.out.println("You do not have enough money");
        }
        else{
            creditAccount.Withdraw(amount);
            otherUserChecking.Deposit(amount);
            System.out.println("Success!");
        }
    }

    static void payWithChecking(double amount, Checking checkingAccount,Checking otherUserChecking){
        if((checkingAccount.getBalance()-amount)<0){
            System.out.println("You do not have enough money");
        }
        else{
            checkingAccount.Withdraw(amount);
            otherUserChecking.Deposit(amount);
            System.out.println("Success!");
        }
    }

    static void printInfo(int accountId,Customer[] customers){
        Checking checkingAccount = customers[accountId].getCheckingAccount();
        Credit creditAccount = customers[accountId].getCreditAccount();
        Savings savingsAccount = customers[accountId].getSavingsAccount();
        System.out.println("Name: "+customers[accountId].getName());
        System.out.println("Address: "+customers[accountId].getAddress());
        System.out.println("Date of birth: "+customers[accountId].getDOB());
        System.out.println("Phone number: "+customers[accountId].getPhoneNumber());
        System.out.println("Credit score: "+customers[accountId].getCreditScore());
        System.out.println("Checking\n\tAccount number: "+checkingAccount.getAccountNumber()+"\n\tBalance: "+checkingAccount.getBalance());
        System.out.println("Credit\n\tAccount number: "+creditAccount.getAccountNumber()+"\n\tBalance: "+creditAccount.getBalance()+"\n\t Limit: "+creditAccount.getCreditLimit());
        System.out.println("Savings\n\tAccount number: "+savingsAccount.getAccountNumber()+"\n\tBalance: "+savingsAccount.getBalance());
    }

    static String[] logTransaction(String log, int logCounter, String[] logs){
        logs[logCounter] = log;
        return logs;
    }

    static void logAllTransactions(String[] logs, int logCounter){
        try{
            FileWriter myWriter = new FileWriter("logFile.txt");
            for(int i=0;i<logCounter;i++){
                myWriter.write(logs[i]+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}


//        Scanner input = new Scanner(System.in);
//        boolean keepRunning = true;
//        System.out.println("Welcome to Miners Bank");
//        System.out.println("Enter your name");
//        String userName = input.nextLine();
//        int userID = doesAccountExist(userName, customers, i);
//        //skip the first line of the file
//        if(userID==0){
//            System.out.println("Sorry your account does not exist try again");
//            keepRunning = false;
//        }
//        while(keepRunning){
//            System.out.println("Please select account\n1. Checking\n2.Credit\n3.Savings");
//
//            System.out.println("What would you like to do?\n1. View Balance\n2. Deposit\n3. Withdraw\n4. Send money\n5. Log off");
//            int choice = input.nextInt();
//            if(choice==1){	//view balance
//                System.out.println(customers[userID].);
//            }
//            else if(choice==2){	//deposit
//                System.out.println("Input amount");
//                double depositAmount = input.nextDouble();
//                depositMoney(depositAmount,accounts,userID);
//                String log = userName+" deposited "+depositAmount+" in their checking account";
//                logTransaction(log,logCounter,logs);
//                logCounter++;
//            }
//            else if(choice==3){	//withdraw
//                System.out.println("Input amount");
//                double withdrawAmount = input.nextDouble();
//                withdrawMoney(withdrawAmount,accounts,userID);
//                String log = userName+" withdrew "+withdrawAmount+" from their checking account";
//                logTransaction(log,logCounter,logs);
//                logCounter++;
//            }
//            else if(choice==4){	//send money
//                System.out.println("Input amount");
//                double sendAmount = input.nextDouble();
//                System.out.println("What is the name of the person you are sending money to");
//                String elseName = input.nextLine()+input.nextLine();
//                payPerson(sendAmount,accounts,userID,elseName,i);
//                String log = userName+" sent "+sendAmount+" to "+elseName;
//                logTransaction(log,logCounter,logs);
//                logCounter++;
//            }
//            else if(choice==5){	//log off
//                keepRunning = false;
//                break;
//            }
//            else{	//repeat the process if the user does not input a correct value
//                System.out.println("Please select an option");
//                continue;
//            }
//            System.out.println("Would you like to do something else (y or n)");
//            char keepGoing = input.next().charAt(0);
//            if(keepGoing=='n'){
//                keepRunning = false;
//            }
//        }
//        logAllTransactions(logs,logCounter);	//transfer the log data from array into a file
//        System.out.println("Thank you");
//    }
//
////        Scanner input = new Scanner(System.in);
////        System.out.println("Welcome to Miners Bank");
////        System.out.println("Enter your name");
////        String userName = input.nextLine();
//
//    }
//
//
//    static void depositMoney(double amount, Checking[] accounts, int userID){
//        accounts[userID].Deposit(amount);
//        System.out.println("Success!");
//    }
