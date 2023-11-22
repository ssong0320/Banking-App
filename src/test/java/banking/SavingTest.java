package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingTest {

    public static final double APR_VALUE = 9.8;

    Saving saving;

    @BeforeEach
    public void setUp() {
        saving = new Saving(APR_VALUE, "12345678");
    }

    @Test
    public void checking_created_with_initial_balance_of_zero() {
        double actual = saving.getBalance();

        assertEquals(0.0, actual);
    }

}
