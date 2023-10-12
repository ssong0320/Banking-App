import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class BankTest {
    Bank bank;
    Checking checking;
    Saving saving;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(9.8, "00000000");
        saving = new Saving(9.8, "00000001");
    }

    @Test
    void test_bank_is_created_with_no_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void test_add_account_to_bank() {
    }
}
