public class Checking extends Account {

    public Checking(double APR, String ID) {
        super(APR, 0, ID);
        setAccountType("checking");
    }

}
