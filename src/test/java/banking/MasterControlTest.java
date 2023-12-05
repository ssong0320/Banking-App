package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;

    private ArrayList<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new StoreCommands(bank));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {

        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {

        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void typo_in_withdraw_command_is_invalid() {

        input.add("withdraww 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraww 12345678 100", actual);
    }

    @Test
    void typo_in_transfer_command_is_invalid() {

        input.add("transferr 12345678 87654321 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transferr 12345678 87654321 100", actual);
    }

    @Test
    void typo_in_pass_command_is_invalid() {

        input.add("passs 1");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("passs 1", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("create checking 12345678 1.0", actual.get(1));
    }

    @Test
    void invalid_to_create_saving_accounts_with_same_ID() {
        input.add("create savings 12345678 1.0");
        input.add("create savings 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Savings 12345678 0.00 1.00", actual.get(0));
        assertEquals("create savings 12345678 1.0", actual.get(1));
    }

    @Test
    void invalid_to_create_cd_accounts_with_same_ID() {
        input.add("create cd 12345678 1.0 1000");
        input.add("create cd 12345678 1.0 1000");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 1000.00 1.00", actual.get(0));
        assertEquals("create cd 12345678 1.0 1000", actual.get(1));
    }

    @Test
    void invalid_to_deposit_accounts_with_invalid_amount() {
        input.add("deposit 12345678 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 12345678 3000", actual);
    }

    @Test
    void invalid_to_deposit_accounts_with_missing_id() {
        input.add("deposit 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 3000", actual);
    }

    @Test
    void invalid_to_deposit_accounts_with_missing_deposit() {
        input.add("12345678 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("12345678 3000", actual);
    }

    @Test
    void invalid_to_deposit_accounts_with_missing_amount() {
        input.add("deposit 12345678");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 12345678", actual);
    }

    @Test
    void invalid_to_withdraw_accounts_with_invalid_amount() {
        input.add("withdraw 12345678 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraw 12345678 3000", actual);
    }

    @Test
    void invalid_to_withdraw_accounts_with_missing_id() {
        input.add("withdraw 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraw 3000", actual);
    }

    @Test
    void invalid_to_withdraw_accounts_with_missing_withdraw() {
        input.add("12345678 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("12345678 3000", actual);
    }

    @Test
    void invalid_to_withdraw_accounts_with_missing_amount() {
        input.add("withdraw 12345678");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("withdraw 12345678", actual);
    }

    @Test
    void invalid_to_transfer_accounts_with_invalid_amount() {
        input.add("transfer 12345678 87654321 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transfer 12345678 87654321 3000", actual);
    }

    @Test
    void invalid_to_transfer_accounts_with_missing_id() {
        input.add("transfer 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transfer 3000", actual);
    }

    @Test
    void invalid_to_transfer_accounts_with_missing_transfer() {
        input.add("12345678 87654321 3000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("12345678 87654321 3000", actual);
    }

    @Test
    void invalid_to_transfer_accounts_with_missing_amount() {
        input.add("transfer 87654321 12345678");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("transfer 87654321 12345678", actual);
    }

    @Test
    void invalid_to_pass_accounts_with_missing_pass() {
        input.add("1");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("1", actual);
    }

    @Test
    void invalid_to_pass_accounts_with_missing_month() {
        input.add("pass");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("pass", actual);
    }

    @Test
    void invalid_to_pass_accounts_with_max_month() {
        input.add("pass 100000");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("pass 100000", actual);
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

}

