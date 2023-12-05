package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {

    TransferProcessor transferProcessor;
    Bank bank;
    Checking checking;
    Checking checking2;
    Saving saving;
    Saving saving2;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(0.01, "12345678");
        checking2 = new Checking(0.01, "87654321");
        saving = new Saving(0.01, "12345678");
        saving2 = new Saving(0.01, "87654321");
        transferProcessor = new TransferProcessor(bank);
    }

    @Test
    void transfer_from_checking_to_saving() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        transferProcessor.processTransfer("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_saving_to_checking() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(checking2);
        transferProcessor.processTransfer("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_checking_to_another_checking_account() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(checking2);
        transferProcessor.processTransfer("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }

    @Test
    void transfer_from_saving_to_another_saving_account() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        transferProcessor.processTransfer("Transfer 12345678 87654321 500");
        assertEquals(500, bank.getAccount().get("12345678").getBalance());
        assertEquals(500, bank.getAccount().get("87654321").getBalance());
    }
}