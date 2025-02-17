package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    CommandValidator commandValidator;
    Bank bank;
    Checking checking;
    Saving saving;

    @BeforeEach
    void setUP() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        checking = new Checking(9.8, "00000000");
        saving = new Saving(9.8, "00000001");
        bank.addAccount(saving);
    }

    @Test
    void test_create_command_with_lowercase_c() {
        boolean actual = commandValidator.validate("create savings 12345678 1");
        assertTrue(actual);
    }

    @Test
    void test_create_command_with_typo() {
        boolean actual = commandValidator.validate("creat savings 12345678 1");
        assertFalse(actual);
    }

    @Test
    void test_create_command_is_valid() {
        boolean actual = commandValidator.validate("Create savings 12345678 1");
        assertTrue(actual);
    }

    @Test
    void test_deposit_command_with_a_typo() {
        boolean actual = commandValidator.validate("depo 00000001 1");
        assertFalse(actual);
    }

    @Test
    void test_deposit_command() {
        boolean actual = commandValidator.validate("deposit 00000001 1000");
        assertTrue(actual);
    }

    @Test
    void test_invalid_deposit_command() {
        boolean actual = commandValidator.validate("deposit 00000001 abc");
        assertFalse(actual);
    }

    @Test
    void test_deposit_non_existing_account() {
        boolean actual = commandValidator.validate("deposit 00000002 1000");
        assertFalse(actual);
    }

    @Test
    void test_zero_deposit_amount() {
        boolean actual = commandValidator.validate("deposit 00000001 0");
        assertTrue(actual);
    }

    @Test
    void test_negative_deposit_amount() {
        boolean actual = commandValidator.validate("deposit 00000001 -100");
        assertFalse(actual);
    }

    @Test
    void valid_withdrawal_command() {
        bank.addAccount(checking);
        boolean actual = commandValidator.validate("withdraw 00000000 100");
        assertTrue(actual);
    }

    @Test
    void invalid_command_withdrawal_typo() {
        bank.addAccount(checking);
        boolean actual = commandValidator.validate("withg 00000000 100");
        assertFalse(actual);
    }

    @Test
    void invalid_command_withdrawal_contain_number() {
        bank.addAccount(checking);
        boolean actual = commandValidator.validate("withdraw5 00000000 100");
        assertFalse(actual);
    }
}
