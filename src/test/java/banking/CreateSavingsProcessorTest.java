package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateSavingsProcessorTest {
    CreateSavingsProcessor createSavingsProcessor;
    Bank bank;


    @BeforeEach
    void setUP() {
        bank = new Bank();
        createSavingsProcessor = new CreateSavingsProcessor(bank);
    }

    @Test
    void test_if_saving_account_can_be_created_correctly() {
        createSavingsProcessor.processCreateSaving("create saving 12345678 1.0");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("savings", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }

}