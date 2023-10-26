import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        bank.addAccount(checking);
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    void test_add_two_accounts_to_bank() {
        bank.addAccount(checking);
        bank.addAccount(saving);
        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    void test_getting_account_through_bank() {
        bank.addAccount(saving);
        assertEquals(saving, bank.getAccountThroughBank("00000001"));
    }

    @Test
    void deposit_money_through_bank() {
        bank.addAccount(saving);
        bank.depositThroughId("00000001", 50.0);
        assertEquals(50.0, saving.getBalance());
    }

    @Test
    void deposit_money_through_bank_twice() {
        bank.addAccount(saving);
        bank.depositThroughId("00000001", 50.0);
        bank.depositThroughId("00000001", 50.0);
        assertEquals(100.0, saving.getBalance());
    }

    @Test
    void withdraw_money_through_bank() {
        bank.addAccount(saving);
        bank.depositThroughId("00000001", 100.0);
        bank.withdrawThroughId("00000001", 50.0);
        assertEquals(50.0, saving.getBalance());
    }

    @Test
    void withdraw_money_through_bank_twice() {
        bank.addAccount(saving);
        bank.depositThroughId("00000001", 100.0);
        bank.withdrawThroughId("00000001", 50.0);
        bank.withdrawThroughId("00000001", 30.0);
        assertEquals(20.0, saving.getBalance());
    }
}
