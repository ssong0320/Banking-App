public class CD extends Account {

    public CD(double APR, double balance, String ID) {
        super(APR, balance, ID);
        setAccountType("cd");
    }
}
