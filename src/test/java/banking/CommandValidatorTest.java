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
        boolean actual = commandValidator.validate("create saving 12345678 1");
        assertTrue(actual);
    }

    @Test
    void test_create_command_with_typo() {
        boolean actual = commandValidator.validate("creat saving 12345678 1");
        assertFalse(actual);
    }

    @Test
    void test_create_command_is_valid() {
        boolean actual = commandValidator.validate("Create saving 12345678 1");
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
}
