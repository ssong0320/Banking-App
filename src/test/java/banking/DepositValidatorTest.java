package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {
    DepositValidator depositValidator;
    Bank bank;
    Checking checking;
    Saving saving;
    CD cd;

    @BeforeEach
    void setUP() {
        bank = new Bank();
        depositValidator = new DepositValidator(bank);
        checking = new Checking(9.8, "11111111");
        saving = new Saving(9.8, "22222222");
        cd = new CD(9.8, 1000, "33333333");
        bank.addAccount(checking);
        bank.addAccount(saving);
        bank.addAccount(cd);
    }

    @Test
    void test_if_can_deposit_into_existing_account() {
        boolean actual = depositValidator.validate("Deposit 11111111 100");
        assertTrue(actual);
    }

    @Test
    void test_if_deposit_command_is_case_insensitive() {
        boolean actual = depositValidator.validate("DepOSIT 11111111 100");
        assertTrue(actual);
    }

    @Test
    void test_if_can_deposit_zero_into_existing_account_checking() {
        boolean actual = depositValidator.validate("Deposit 11111111 0");
        assertTrue(actual);
    }

    @Test
    void test_if_can_deposit_zero_into_existing_account_cd() {
        boolean actual = depositValidator.validate("Deposit 33333333 0");
        assertFalse(actual);
    }

    @Test
    void test_if_can_deposit_zero_into_existing_account_saving() {
        boolean actual = depositValidator.validate("Deposit 22222222 0");
        assertTrue(actual);
    }

    @Test
    void test_if_can_deposit_into_non_existing_account() {
        boolean actual = depositValidator.validate("Deposit 11111112 1000");
        assertFalse(actual);
    }

    @Test
    void test_if_can_deposit_negative_amount_for_savings() {
        boolean actual = depositValidator.validate("Deposit 22222222 -100");
        assertFalse(actual);
    }

    @Test
    void test_if_can_deposit_negative_amount_for_checking() {
        boolean actual = depositValidator.validate("Deposit 11111111 -100");
        assertFalse(actual);
    }

    @Test
    void test_if_amount_over_two_thousand_five_hundred_can_be_deposited_into_saving() {
        boolean actual = depositValidator.validate("Deposit 22222222 2501");
        assertFalse(actual);
    }

    @Test
    void test_if_amount_over_one_thousand_can_be_deposited_into_checking() {
        boolean actual = depositValidator.validate("Deposit 11111111 1001");
        assertFalse(actual);
    }

    @Test
    void test_if_money_can_be_deposited_into_cd() {
        boolean actual = depositValidator.validate("Deposit 33333333 1001");
        assertFalse(actual);
    }

    @Test
    void test_if_deposit_possible_when_id_has_extra_digits() {
        boolean actual = depositValidator.validate("Deposit 111111111 1001");
        assertFalse(actual);
    }

    @Test
    void test_if_deposit_possible_when_id_has_missing_digits() {
        boolean actual = depositValidator.validate("Deposit 1111111 1001");
        assertFalse(actual);
    }

    @Test
    void test_if_amount_at_upper_limit_can_be_deposited_into_saving() {
        boolean actual = depositValidator.validate("Deposit 22222222 2500");
        assertTrue(actual);
    }

    @Test
    void test_if_amount_at_upper_limit_can_be_deposited_into_checking() {
        boolean actual = depositValidator.validate("Deposit 11111111 1000");
        assertTrue(actual);
    }

    @Test
    void test_if_negative_amount_just_below_lower_limit_is_rejected_saving() {
        boolean actual = depositValidator.validate("Deposit 22222222 -1");
        assertFalse(actual);
    }

    @Test
    void test_if_negative_amount_just_below_lower_limit_is_rejected_checking() {
        boolean actual = depositValidator.validate("Deposit 11111111 -1");
        assertFalse(actual);
    }

    @Test
    void test_if_you_can_deposit_multiple_times_in_an_account() {
        assertTrue(depositValidator.validate("Deposit 11111111 500"));
        assertTrue(depositValidator.validate("Deposit 11111111 300"));
    }

    @Test
    void test_if_missing_deposit_fails() {
        boolean actual = depositValidator.validate("11111111 500");
        assertFalse(actual);
    }

    @Test
    void test_if_missing_id_fails() {
        boolean actual = depositValidator.validate("deposit 100");
        assertFalse(actual);
    }

    @Test
    void test_if_missing_amount_fails() {
        boolean actual = depositValidator.validate("Deposit 11111111");
        assertFalse(actual);
    }

    @Test
    void test_depositing_into_account_that_does_not_exist() {
        boolean actual = depositValidator.validate("Deposit 12345678 1000");
        assertFalse(actual);
    }

    @Test
    void test_invalid_accountID_nonInteger() {
        boolean actual = depositValidator.isValidAccountID("InvalidID");
        assertFalse(actual);
    }

    @Test
    void test_invalid_accountID_length_7() {
        boolean actual = depositValidator.isValidAccountID("1234567");
        assertFalse(actual);
    }

    @Test
    void test_invalid_accountID_length_9() {
        boolean actual = depositValidator.isValidAccountID("123456789");
        assertFalse(actual);
    }
}


