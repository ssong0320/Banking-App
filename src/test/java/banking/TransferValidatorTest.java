package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferValidatorTest {

    Checking checking;
    Checking checking2;
    Saving saving;
    Saving saving2;
    CD cd;
    private TransferValidator transferValidator;
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(0.01, "12345678");
        checking2 = new Checking(0.01, "87654321");
        saving = new Saving(0.01, "12345678");
        saving2 = new Saving(0.01, "87654321");
        cd = new CD(0.01, 1000, "87654321");
        transferValidator = new TransferValidator(bank);
    }

    @Test
    void valid_transfer_checking_command() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 100");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_saving_command() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 100");
        assertTrue(actual);
    }

    @Test
    void invalid_missing_transfer_command() {
        boolean actual = transferValidator.validate("12345678 87654321 100");
        assertFalse(actual);
    }

    @Test
    void invalid_missing_from_id_command() {
        boolean actual = transferValidator.validate("transfer 87654321 100");
        assertFalse(actual);
    }

    @Test
    void invalid_missing_to_id_command() {
        boolean actual = transferValidator.validate("transfer 87654321 100");
        assertFalse(actual);
    }

    @Test
    void invalid_missing_amount_command() {
        boolean actual = transferValidator.validate("transfer 12345678 87654321");
        assertFalse(actual);
    }

    @Test
    void invalid_amount_contain_letter_command() {
        boolean actual = transferValidator.validate("transfer 12345678 87654321 t");
        assertFalse(actual);
    }

    @Test
    void invalid_command_transfer_negative_amount() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void valid_command_transfer_zero_amount() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_command_transfer_one_amount() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1");
        assertTrue(actual);
    }

    @Test
    void valid_command_transfer_max_checking_account_amount() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 400");
        assertTrue(actual);
    }

    @Test
    void invalid_command_cannot_transfer_max_checking_account_amount() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 401");
        assertFalse(actual);
    }

    @Test
    void invalid_command_saving_transfer_negative_amount() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void valid_command_saving_transfer_zero_amount() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_command_saving_transfer_one_amount() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1");
        assertTrue(actual);
    }

    @Test
    void valid_command_saving_transfer_max_amount() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1000");
        assertTrue(actual);
    }

    @Test
    void invalid_command_saving_cannot_transfer_max_amount() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1001");
        assertFalse(actual);
    }

    @Test
    void invalid_command_transfer_to_cd_account() {
        bank.addAccount(checking);
        bank.addAccount(cd);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_command_transfer_from_cd_account() {
        bank.addAccount(checking);
        bank.addAccount(cd);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1000");
        assertFalse(actual);
    }

    @Test
    void invalid_command_transfer_to_two_checking_account_negative_dollar() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void valid_command_transfer_to_two_checking_account_zero_dollar() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 0");
        assertTrue(actual);
    }

    @Test
    void valid_command_transfer_to_two_checking_account_one_dollar() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1");
        assertTrue(actual);
    }

    @Test
    void valid_command_transfer_to_two_checking_account_four_hundred_dollar() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 400");
        assertTrue(actual);
        bank.transfer("12345678", "87654321", 400);
        assertEquals(400, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void invalid_command_transfer_to_more_than_two_checking_account_four_hundred_dollar() {
        bank.addAccount(checking);
        bank.addAccount(checking2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 401");
        assertFalse(actual);
        bank.transfer("12345678", "87654321", 401);
        assertEquals(401, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void invalid_command_transfer_to_two_saving_account_negative_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 -1");
        assertFalse(actual);
    }

    @Test
    void valid_command_transfer_to_two_saving_account_zero_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 0");
        assertTrue(actual);
        bank.transfer("12345678", "87654321", 0);
        assertEquals(0, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void valid_command_transfer_to_two_saving_account_one_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1");
        assertTrue(actual);
        bank.transfer("12345678", "87654321", 1);
        assertEquals(1, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void valid_command_transfer_to_two_saving_account_four_hundred_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1000");
        assertTrue(actual);
        bank.transfer("12345678", "87654321", 1000);
        assertEquals(1000, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void invalid_command_transfer_to_more_than_two_saving_account_four_hundred_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1001");
        assertFalse(actual);
        bank.transfer("12345678", "87654321", 1001);
        assertEquals(1001, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void invalids_command_transfer_to_more_than_two_saving_account_four_hundred_dollar() {
        bank.addAccount(saving);
        bank.addAccount(saving2);
        boolean actual = transferValidator.validate("transfer 12345678 87654321 1001");
        assertFalse(actual);
        bank.transfer("12345678", "87654321", 2500);
        assertEquals(2500, bank.getAccount().get("87654321").getBalance());
    }
}
