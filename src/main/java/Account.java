public abstract class Account {
    private double APR;
    private double balance;
    private String ID;

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

    public double withdrawMoney(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return balance;
        } else {
            return 0;
        }
    }
}