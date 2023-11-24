package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCDProcessorTest {
    CreateCDProcessor createCDProcessor;
    Bank bank;

    @BeforeEach
    void setUP() {
        bank = new Bank();
        createCDProcessor = new CreateCDProcessor(bank);
    }

    @Test
    void test_if_cd_account_can_be_created_correctly() {
        createCDProcessor.processCreateCD("create cd 12345678 1.0 1000");
        Account account = bank.getAccountThroughBank("12345678");
        assertEquals("12345678", account.getID());
        assertEquals("cd", account.getAccountType());
        assertEquals(1.0, account.getAPR());
        assertEquals(1000, account.getBalance());
    }
}