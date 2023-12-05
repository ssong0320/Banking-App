package banking;

public class Saving extends Account {
    public static final double INITIAL_BALANCE = 0.0;
    private boolean monthWithdrawal = false;
    private double balance;

    public Saving(double APR, String ID) {
        super(APR, 0, ID);
        setAccountType("savings");
    }

    @Override
    public void setMonths(int months) {
        if (months > super.age) {
            monthWithdrawal = false;
        }
        super.age = months;
    }

    public boolean getMonthWithdrawal() {
        return monthWithdrawal;
    }


}
