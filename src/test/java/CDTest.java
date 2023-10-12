import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {

    public static final double APR_VALUE = 9.8;
    public static final double CD_INITIAL = 1234.56;

    CD cd;

    @BeforeEach
    void setUp() {
        cd = new CD(APR_VALUE, CD_INITIAL, "9876798");
    }

    @Test
    public void cd_starting_balance_is_assigned_value() {
        double actual = cd.getBalance();

        assertEquals(1234.56, actual);
    }
}

