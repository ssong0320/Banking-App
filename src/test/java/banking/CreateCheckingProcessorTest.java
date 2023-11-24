package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCheckingProcessorTest {
    CreateCheckingProcessor createCheckingProcessor;
    Bank bank;


    @BeforeEach
    void setUP() {
        bank = new Bank();
        createCheckingProcessor = new CreateCheckingProcessor(bank);
    }

    @Test
    void test_if_checking_account_can_be_created_correctly() {
        CreateCheckingProcessor.processCreateChecking("create checking 12345678 1.0");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("checking", account.getAccountType());
        assertEquals(1.0, account.getAPR());
    }
}