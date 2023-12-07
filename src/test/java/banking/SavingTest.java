package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void get_Month_Withdrawal_Initially_False() {
        boolean actualWithdrawal = saving.getMonthWithdrawal();

        assertFalse(actualWithdrawal);
    }

    @Test
    public void get_Month_Withdrawal_After_Setting_Months() {
        int currentAge = saving.getMonths();
        saving.setMonths(currentAge + 1);

        boolean actualWithdrawal = saving.getMonthWithdrawal();

        assertFalse(actualWithdrawal);
    }

    @Test
    public void set_Months_More_Than_Current_Age_Month_Withdrawal_False() {
        int currentAge = saving.getMonths();
        int futureMonths = currentAge + 5;

        saving.setMonths(futureMonths);

        boolean actualWithdrawal = saving.getMonthWithdrawal();

        assertFalse(actualWithdrawal);
    }

    @Test
    public void get_Month_Withdrawal_After_Explicit_Modification() {
        saving.setMonths(5);
        saving.monthWithdrawal = true;

        boolean actualWithdrawal = saving.getMonthWithdrawal();

        assertTrue(actualWithdrawal);
    }
}
