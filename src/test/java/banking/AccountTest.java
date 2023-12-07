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

    @Test
    void withdraw_leading_to_zero_balance() {
        checking.depositMoney(50.0);
        double initialBalance = checking.getBalance();

        double withdrawnAmount = initialBalance;

        double remainingBalance = checking.withdrawMoney(withdrawnAmount);
        assertEquals(0.0, remainingBalance);
    }

    @Test
    void verify_balance_after_withdrawal() {
        double initialBalance = 100.0;
        checking.depositMoney(initialBalance);

        double withdrawnAmount = 30.0;
        double remainingBalance = checking.withdrawMoney(withdrawnAmount);

        assertEquals(initialBalance - withdrawnAmount, remainingBalance);
    }

    @Test
    void calculate_apr_updates_age_correctly() {
        int initialAge = 5;
        checking.setMonths(initialAge);

        int monthsToAdd = 3;
        checking.calculateApr(monthsToAdd);

        int expectedAge = initialAge + monthsToAdd;
        int actualAge = checking.getMonths();

        assertEquals(expectedAge, actualAge);
    }

    @Test
    void withdraw_more_than_balance() {
        double initialBalance = 50.0;
        checking.depositMoney(initialBalance);

        double withdrawalAmount = 70.0;
        double remainingBalance = checking.withdrawMoney(withdrawalAmount);

        assertEquals(0, remainingBalance);
    }

}
