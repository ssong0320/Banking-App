package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {
    DepositProcessor depositProcessor;
    CreateCheckingProcessor createCheckingProcessor;
    CreateSavingsProcessor createSavingsProcessor;
    CreateCDProcessor createCDProcessor;
    Bank bank;


    @BeforeEach
    void setUP() {
        bank = new Bank();
        depositProcessor = new DepositProcessor(bank);
        createCheckingProcessor = new CreateCheckingProcessor(bank);
        createSavingsProcessor = new CreateSavingsProcessor(bank);
        createCDProcessor = new CreateCDProcessor(bank);
    }

    @Test
    void test_if_deposit_works_for_checking() {
        CreateCheckingProcessor.processCreateChecking("create checking 12345678 1.0");
        depositProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_saving() {
        CreateSavingsProcessor.processCreateSaving("create saving 12345678 1.0");
        depositProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(1500, account.getBalance());
    }

    @Test
    void test_if_deposit_works_for_cd() {
        CreateCDProcessor.processCreateCD("create cd 12345678 1.0 2000");
        depositProcessor.processDeposit("deposit 12345678 1500");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals(3500, account.getBalance());
    }
}
