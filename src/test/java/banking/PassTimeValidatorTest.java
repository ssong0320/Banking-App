package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {

    PassTimeValidator passTimeValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passTimeValidator = new PassTimeValidator(bank);
    }

    @Test
    void valid_pass_command() {
        boolean actual = passTimeValidator.validate("pass 1");
        assertTrue(actual);
    }

    @Test
    void invalid_pass_command_month_exceed_sixty() {
        boolean actual = passTimeValidator.validate("pass 61");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_month_below_one() {
        boolean actual = passTimeValidator.validate("pass 0");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_month_is_negative() {
        boolean actual = passTimeValidator.validate("pass -1");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_missing() {
        boolean actual = passTimeValidator.validate("1");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_month() {
        boolean actual = passTimeValidator.validate("pass");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_month_contain_letter() {
        boolean actual = passTimeValidator.validate("pass 1t");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_command_empty_string() {
        boolean actual = passTimeValidator.validate("");
        assertFalse(actual);
    }


}

