package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreCommandsTest {

    @Test
    void test_add_and_get_invalid_commands() {
        StoreCommands storeCommands = new StoreCommands();

        storeCommands.addInvalidCommand("create 123456789 1.0");
        storeCommands.addInvalidCommand("depo 12345678 1000");

        assertEquals(2, storeCommands.getAllCommands().size());
        assertEquals("create 123456789 1.0", storeCommands.getAllCommands().get(0));
        assertEquals("depo 12345678 1000", storeCommands.getAllCommands().get(1));
    }
}
