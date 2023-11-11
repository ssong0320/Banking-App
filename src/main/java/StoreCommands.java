import java.util.ArrayList;
import java.util.List;

public class StoreCommands {
    private List<String> commands;

    public StoreCommands() {
        this.commands = new ArrayList<>();
    }

    public void addInvalidCommand(String command) {
        commands.add(command);
    }

    public List<String> getAllCommands() {
        return new ArrayList<>(commands);
    }
}
