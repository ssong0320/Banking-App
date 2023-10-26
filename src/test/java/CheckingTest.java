import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {

    public static final double APR_VALUE = 9.8;

    Checking checking;

    @BeforeEach
    public void setUp() {
        checking = new Checking(APR_VALUE, "9797987");
    }

    @Test
    public void checking_created_with_initial_balance_of_0() {
        double actual = checking.getBalance();

        assertEquals(0.0, actual);
    }

}

