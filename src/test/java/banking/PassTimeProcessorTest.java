package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassTimeProcessorTest {
    PassTimeProcessor passTimeProcessor;
    DepositProcessor depositProcessor;
    CreateCheckingProcessor createCheckingProcessor;
    CreateSavingsProcessor createSavingsProcessor;
    CreateCDProcessor createCDProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passTimeProcessor = new PassTimeProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        createCheckingProcessor = new CreateCheckingProcessor(bank);
        createSavingsProcessor = new CreateSavingsProcessor(bank);
        createCDProcessor = new CreateCDProcessor(bank);
    }

    @Test
    void calculate_apr_after_one_month_checking_account() {
        CreateCheckingProcessor.processCreateChecking("create checking 12345678 0.06");
        depositProcessor.processDeposit("deposit 12345678 1000");
        passTimeProcessor.processPassTime("Pass 1");
        assertEquals(1000.05, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void calculate_apr_after_twelve_month_checking_account() {
        CreateCheckingProcessor.processCreateChecking("create checking 12345678 0.06");
        depositProcessor.processDeposit("deposit 12345678 1000");
        passTimeProcessor.processPassTime("Pass 12");
        assertEquals(1000.6001650275032, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void calculate_cd_account_apr_after_twelve_month() {
        CreateCDProcessor.processCreateCD("create cd 12345678 0.01 1000");
        passTimeProcessor.processPassTime("pass 12");
        assertEquals(1000.4000783433436, bank.getAccount().get("12345678").getBalance());
    }
}
