package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {
    CommandProcessor commandProcessor;
    Bank bank;
    Checking checking;
    Checking checking2;
    Saving saving;
    Saving saving2;
    CD cd;

    @BeforeEach
    void setUP() {
        bank = new Bank();
        checking = new Checking(0.01, "12345678");
        checking2 = new Checking(0.01, "87654321");
        saving = new Saving(0.01, "12345678");
        saving2 = new Saving(0.01, "87654321");
        cd = new CD(0.01, 1000, "12345678");
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
    void withdrawal_once_from_checking() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_twice_from_checking() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_once_from_saving() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_twice_from_saving() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_once_from_cd() {
        bank.addAccount(cd);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_twice_from_cd() {
        bank.addAccount(cd);
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        commandProcessor.evaluateCommand("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void transfer_from_checking_to_saving() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        commandProcessor.evaluateCommand("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_saving_to_checking() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(checking2);
        commandProcessor.evaluateCommand("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_checking_to_another_checking_account() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(checking2);
        commandProcessor.evaluateCommand("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_saving_to_another_saving_account() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        commandProcessor.evaluateCommand("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
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
