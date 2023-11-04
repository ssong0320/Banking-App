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
    }

    @Test
    void test_create_command_with_lowercase_c() {
        boolean actual = commandValidator.validate("create savings 12345678 1");
        assertFalse(actual);
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
    void test_Deposit_command_with_lowercase_d() {
        bank.addAccount(saving);
        boolean actual = commandValidator.validate("deposit 00000001 1000");
        assertFalse(actual);
    }

    @Test
    void test_Deposit_command_is_valid() {
        bank.addAccount(saving);
        boolean actual = commandValidator.validate("Deposit 00000001 1000");
        assertFalse(actual);
    }
}
