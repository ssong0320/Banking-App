package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawValidatorTest {

    WithdrawValidator withdrawValidator;
    WithdrawProcessor withdrawProcessor;
    Bank bank;
    Checking checking;
    Saving saving;
    CD cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(9.8, "12345678");
        saving = new Saving(9.8, "12345678");
        cd = new CD(9.8, 1000, "12345678");
        withdrawValidator = new WithdrawValidator(bank);
        withdrawProcessor = new WithdrawProcessor(bank);
    }

    @Test
    void valid_withdrawal_command() {
        bank.addAccount(saving);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void invalid_withdrawal_command_missing_id() {
        boolean actual = withdrawValidator.validate("withdraw 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_command_missing_amount() {
        boolean actual = withdrawValidator.validate("withdraw 12345678");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_command_missing_withdraw() {
        boolean actual = withdrawValidator.validate("12345678 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_command_id() {
        boolean actual = withdrawValidator.validate("withdraw 123456789 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_typo() {
        boolean actual = withdrawValidator.validate("withdrw 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_command_id_contain_letter() {
        boolean actual = withdrawValidator.validate("withdraw 1234567tt 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_id_is_less_than_eight_digits_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 1234567 0");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_id_is_more_than_eight_digits_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 123456789 0");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_id_is_not_in_number_form_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw one 0");
        assertFalse(actual);
    }

    @Test
    void invalid_withdrawal_amount_negative_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 -1");
        assertFalse(actual);
    }

    @Test
    void invalid_saving_withdrawal_amount_negative_command() {
        bank.addAccount(saving);
        boolean actual = withdrawValidator.validate("withdraw 12345678 -1");
        assertFalse(actual);
    }

    @Test
    void valid_checking_withdrawal_amount_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_saving_withdrawal_amount_command() {
        bank.addAccount(saving);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void valid_saving_withdrawal_one_dollar_amount_command() {
        bank.addAccount(saving);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1");
        assertTrue(actual);
    }

    @Test
    void valid_withdrawal_amount_one_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1");
        assertTrue(actual);
    }

    @Test
    void valid_checking_withdrawal_amount_max_command() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 400");
        assertTrue(actual);
    }

    @Test
    void valid_saving_withdrawal_amount_max_command() {
        bank.addAccount(saving);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void invalid_checking_account_more_than_max_withdrawal_amount() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 401");
        assertFalse(actual);
    }

    @Test
    void invalid_saving_account_more_than_max_withdrawal_amount() {
        bank.addAccount(checking);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void valid_saving_account_withdraw_per_month() {
        bank.addAccount(saving);
        bank.getAccount().get("12345678").setMonths(1);
        boolean actual = withdrawValidator.validate("withdraw 12345678 100");
        assertTrue(actual);
    }

    @Test
    void valid_saving_account_withdraw() {
        bank.addAccount(saving);
        bank.getAccount().get("12345678").setMonths(10);
        boolean actual = withdrawValidator.validate("withdraw 12345678 100");
        assertTrue(actual);
    }

    @Test
    void invalid_cd_account_withdraw() {
        bank.addAccount(cd);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        assertFalse(actual);
    }

    @Test
    void valid_cd_account_withdraw() {
        bank.addAccount(cd);
        bank.getAccount().get("12345678").setMonths(12);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void invalid_withdraw_zero_dollar() {
        bank.addAccount(cd);
        bank.getAccount().get("12345678").setMonths(12);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_zero_dollar_more_than_twelve_month() {
        bank.addAccount(cd);
        bank.getAccount().get("12345678").setMonths(13);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_from_cd_more_than_twelve_month() {
        bank.addAccount(cd);
        bank.getAccount().get("12345678").setMonths(11);
        boolean actual = withdrawValidator.validate("withdraw 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void testValidAccountIDLength() {
        String validID = "12345678";
        assertTrue(withdrawValidator.isValidAccountID(validID));
    }

    @Test
    void testAccountIDLessThanEightDigits() {
        String invalidID = "1234567";
        assertFalse(withdrawValidator.isValidAccountID(invalidID));
    }

    @Test
    void testAccountIDMoreThanEightDigits() {
        String invalidID = "123456789";
        assertFalse(withdrawValidator.isValidAccountID(invalidID));
    }

    @Test
    void testAccountIDWithNonNumericCharacters() {
        String invalidID = "12A45678";
        assertFalse(withdrawValidator.isValidAccountID(invalidID));
    }

    @Test
    void testInvalidAccountID() {
        String invalidID = "invalid";
        assertFalse(withdrawValidator.isValidAccountID(invalidID));
    }

    @Test
    void testInvalidDouble() {
        String invalidStr = "invalid";
        double result = withdrawValidator.isDouble(invalidStr);
        assertEquals(-1.0, result);
    }

    @Test
    void unsupportedAccountType() {
        bank.addAccount(new Account(0.0, 0.0, "00000000") {
            @Override
            public String getAccountType() {
                return "unsupported";
            }
        });
        boolean actual = withdrawValidator.validate("withdraw 00000000 100");
        assertFalse(actual);
    }

}