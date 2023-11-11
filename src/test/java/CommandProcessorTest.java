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
        commandProcessor.processCreateChecking("create checking 12345678 1.0");
        assertEquals(1, bank.getAccounts().size());
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("checking", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }

    @Test
    void test_if_saving_account_can_be_created_correctly() {
        commandProcessor.processCreateSaving("create saving 12345678 1.0");
        assertEquals(1, bank.getAccounts().size());
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("saving", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }

    @Test
    void test_if_cd_account_can_be_created_correctly() {
        commandProcessor.processCreateCD("create cd 12345678 1.0 1000");
        assertEquals(1, bank.getAccounts().size());
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("cd", account.getAccountType());
        assertEquals(1.0, account.getAPR());
        assertEquals(1000, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_checking() {
        commandProcessor.processCreateSaving("create checking 12345678 1.0");
        commandProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_saving() {
        commandProcessor.processCreateSaving("create saving 12345678 1.0");
        commandProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_cd() {
        commandProcessor.processCreateCD("create cd 12345678 1.0 2000");
        commandProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(3500, account.getBalance());
    }
}
