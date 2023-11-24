package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {
    CommandProcessor commandProcessor;
    Bank bank;


    @BeforeEach
    void setUP() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void test_if_checking_account_can_be_created_correctly() {
        commandProcessor.evaluateCommand("create checking 12345678 1.0");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("checking", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }

    @Test
    void test_if_saving_account_can_be_created_correctly() {
        commandProcessor.evaluateCommand("create saving 12345678 1.0");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("saving", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }

    @Test
    void test_if_cd_account_can_be_created_correctly() {
        commandProcessor.evaluateCommand("create cd 12345678 1.0 1000");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("cd", account.getAccountType());
        assertEquals(1.0, account.getAPR());
        assertEquals(1000, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_checking() {
        commandProcessor.evaluateCommand("create checking 12345678 1.0");
        commandProcessor.evaluateCommand("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_saving() {
        commandProcessor.evaluateCommand("create saving 12345678 1.0");
        commandProcessor.evaluateCommand("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_cd() {
        commandProcessor.evaluateCommand("create cd 12345678 1.0 2000");
        commandProcessor.evaluateCommand("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(3500, account.getBalance());
    }

    @Test
    void calculate_apr_after_one_month_checking_account() {
        commandProcessor.evaluateCommand("create checking 12345678 0.06");
        commandProcessor.evaluateCommand("deposit 12345678 1000");
        commandProcessor.evaluateCommand("pass 1");
        assertEquals(1000.05, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void calculate_apr_after_twelve_month_checking_account() {
        commandProcessor.evaluateCommand("create checking 12345678 0.06");
        commandProcessor.evaluateCommand("deposit 12345678 1000");
        commandProcessor.evaluateCommand("pass 12");
        assertEquals(1000.6001650275032, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void calculate_cd_account_apr_after_twelve_month() {
        commandProcessor.evaluateCommand("create cd 12345678 0.01 1000");
        commandProcessor.evaluateCommand("pass 12");
        assertEquals(1000.4000783433436, bank.getAccount().get("12345678").getBalance());
    }

}
