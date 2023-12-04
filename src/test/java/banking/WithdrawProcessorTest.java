package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {

    WithdrawProcessor withdrawProcessor;
    Bank bank;
    Checking checking;
    Saving saving;
    CD cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(0.1, "12345678");
        saving = new Saving(0.1, "12345678");
        cd = new CD(0.1, 1000, "12345678");
        withdrawProcessor = new WithdrawProcessor(bank);
    }

    @Test
    void withdraw_once_from_checking() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdraw_twice_from_checking() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdraw_once_from_saving() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_twice_from_saving() {
        bank.addAccount(saving);
        bank.depositThroughId("12345678", 1000);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_once_from_cd() {
        bank.addAccount(cd);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(900, bank.getAccount().get("12345678").getBalance());
    }

    @Test
    void withdrawal_twice_from_cd() {
        bank.addAccount(cd);
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        withdrawProcessor.processWithdraw("Withdraw 12345678 100");
        assertEquals(800, bank.getAccount().get("12345678").getBalance());
    }
}
