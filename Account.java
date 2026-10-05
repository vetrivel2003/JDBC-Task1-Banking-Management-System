package task1;

public class Account {

    private int accountId;
    private int customerId;
    private String accountNumber;
    private double balance;

    public Account(int customerId, String accountNumber, double balance) {

        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}