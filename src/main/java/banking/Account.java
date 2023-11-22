package banking;

public abstract class Account {
    private double APR;
    private double balance;
    private String ID;
    private String accountType;

    public Account(double APR, double balance, String ID) {
        this.APR = APR;
        this.balance = balance;
        this.ID = ID;
    }

    public double getAPR() {
        return APR;
    }

    public double getBalance() {
        return balance;
    }

    public String getID() {
        return ID;
    }

    public double depositMoney(double amount) {
        return balance += amount;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String type) {
        this.accountType = type;
    }

    public double withdrawMoney(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return balance;
        } else {
            return 0;
        }
    }
}