package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreCommandsTest {
    StoreCommands storeCommands;
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
        storeCommands = new StoreCommands(bank);
    }

    @Test
    void store_invalid_create_checking_command() {
        storeCommands.addInvalidCommand("createee checking 12345678 0.01");
        assertEquals("createee checking 12345678 0.01", storeCommands.getInvalidCommand().get(0));
    }

    @Test
    void store_valid_deposit_command() {
        bank.addAccount(checking);
        storeCommands.addValidCommand("Deposit 12345678 100");
        assertEquals("Deposit 12345678 100", storeCommands.getValidCommands().get("12345678").get(0));
    }

    @Test
    void store_two_valid_command() {
        bank.addAccount(saving);
        bank.addAccount(checking2);
        storeCommands.addValidCommand("Deposit 12345678 100");
        storeCommands.addValidCommand("Transfer 87654321 12345678 50");
        assertEquals("Deposit 12345678 100", storeCommands.getValidCommands().get("12345678").get(0));
        assertEquals("Transfer 87654321 12345678 50", storeCommands.getValidCommands().get("12345678").get(1));
        assertEquals("Transfer 87654321 12345678 50", storeCommands.getValidCommands().get("87654321").get(0));
    }

    @Test
    void store_two_invalid_command() {
        storeCommands.addInvalidCommand("Depossit 12345678 1000");
        storeCommands.addInvalidCommand("Creatte checking 12345678 0.01");
        assertEquals("Depossit 12345678 1000", storeCommands.getInvalidCommand().get(0));
        assertEquals("Creatte checking 12345678 0.01", storeCommands.getInvalidCommand().get(1));
    }

    @Test
    void store_valid_command_transaction_history() {
        bank.addAccount(checking);
        storeCommands.addValidCommand("Deposit 12345678 500");
        assertEquals("Deposit 12345678 500", storeCommands.getValidCommands().get("12345678").get(0));
    }

    @Test
    void store_two_valid_command_transaction_history() {
        bank.addAccount(checking);
        bank.addAccount(saving2);
        storeCommands.addValidCommand("Deposit 12345678 500");
        storeCommands.addValidCommand("Transfer 12345678 87654321 100");
        assertEquals("Deposit 12345678 500", storeCommands.getValidCommands().get("12345678").get(0));
        assertEquals("Transfer 12345678 87654321 100", storeCommands.getValidCommands().get("12345678").get(1));
        assertEquals("Transfer 12345678 87654321 100", storeCommands.getValidCommands().get("87654321").get(0));
    }

    @Test
    void output_one_account_state() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        assertEquals("Checking 12345678 1000.00 0.01", storeCommands.getOutput().get(0));
    }

    @Test
    void output_two_account_state() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        bank.depositThroughId("87654321", 1000);
        assertEquals("Checking 12345678 1000.00 0.01", storeCommands.getOutput().get(0));
        assertEquals("Savings 87654321 1000.00 0.01", storeCommands.getOutput().get(1));
    }

    @Test
    void output_one_checking_account_with_single_transaction_history() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        storeCommands.addValidCommand("Deposit 12345678 500");
        assertEquals("Checking 12345678 1000.00 0.01", storeCommands.getOutput().get(0));
        assertEquals("Deposit 12345678 500", storeCommands.getOutput().get(1));
    }

    @Test
    void output_account_with_twice_transaction_history() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        bank.addAccount(saving2);
        storeCommands.addValidCommand("Deposit 12345678 500");
        storeCommands.addValidCommand("Transfer 12345678 87654321");
        assertEquals("Checking 12345678 1000.00 0.01", storeCommands.getOutput().get(0));
        assertEquals("Deposit 12345678 500", storeCommands.getOutput().get(1));
        assertEquals("Transfer 12345678 87654321", storeCommands.getOutput().get(2));
        assertEquals("Savings 87654321 0.00 0.01", storeCommands.getOutput().get(3));
        assertEquals("Transfer 12345678 87654321", storeCommands.getOutput().get(4));
    }

    @Test
    void output_account_state_with_invalid_command() {
        bank.addAccount(checking);
        bank.depositThroughId("12345678", 1000);
        storeCommands.addValidCommand("Deposit 12345678 500");
        storeCommands.addInvalidCommand("Depositt 12345678 500");
        assertEquals("Checking 12345678 1000.00 0.01", storeCommands.getOutput().get(0));
        assertEquals("Deposit 12345678 500", storeCommands.getOutput().get(1));
        assertEquals("Depositt 12345678 500", storeCommands.getOutput().get(2));
    }
}