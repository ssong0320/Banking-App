package banking;

public abstract class Account {
    public double APR;
    public double balance;
    public String ID;
    public String accountType;
    int age = 0;

    protected Account(double APR, double balance, String ID) {
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
        balance = balance + amount;
        return balance;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String type) {
        this.accountType = type;
    }

    public double withdrawMoney(double amount) {
        if ((balance - amount) < 0.0) {
            balance = 0.0;
            return balance;
        } else {
            balance = balance - amount;
            return balance;
        }
    }

    public int getMonths() {
        return age;
    }

    public void setMonths(int months) {
        this.age = months;
    }

    public void calculateApr(int months) {
        age = age + months;

        if (accountType.equalsIgnoreCase("cd")) {
            calculateCd(months);
        } else {
            for (int month = 0; month < months; month++) {
                balance = balance + ((APR / 100) / 12) * balance;
            }
        }
    }

    private void calculateCd(int months) {
        for (int month = 0; month < months; month++) {
            for (int calculation = 0; calculation < 4; calculation++) {
                balance = balance + ((APR / 100) / 12) * balance;
            }
        }
    }

}