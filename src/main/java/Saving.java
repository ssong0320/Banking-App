public class Saving extends Account {

    public static final double INITIAL_BALANCE = 0.0;
    private double balance;

    public Saving(double APR, String ID) {
        super(APR, 0, ID);
    }

}
