package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    public static final double APR_VALUE = 9.8;

    Account checking;

    @BeforeEach
    void setUp() {
        checking = new Checking(APR_VALUE, "12345678");
    }

    @Test
    void apr_value_is_the_supplied_value() {
        double actual = checking.getAPR();
        assertEquals(9.8, actual);
    }

    @Test
    public void deposit_money() {
        double actual = checking.depositMoney(100.0);
        assertEquals(100.0, actual);
    }

    @Test
    void deposit_money_twice() {
        checking.depositMoney(100.0);
        double actual = checking.depositMoney(100.0);
        assertEquals(200.0, actual);
    }

    @Test
    void test_withdrawing_money() {
        checking.depositMoney(100.0);
        double actual = checking.withdrawMoney(20.0);
        assertEquals(80.0, actual);
    }

    @Test
    void test_withdrawing_money_twice() {
        checking.depositMoney(100.0);
        checking.withdrawMoney(20.0);
        double actual = checking.withdrawMoney(20.0);
        assertEquals(60.0, actual);
    }


    @Test
    void test_balance_does_not_go_below_zero() {
        checking.depositMoney(100.0);
        double actual = checking.withdrawMoney(200.0);
        assertEquals(0.0, actual);
    }
}
