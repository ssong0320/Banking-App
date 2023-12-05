package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreCommands {
    private final Map<String, List<String>> validCommands = new HashMap<>();

    private final List<String> invalidCommand = new ArrayList<>();

    private final Bank bank;

    public StoreCommands(Bank bank) {
        this.bank = bank;
    }

    public Map<String, List<String>> getValidCommands() {
        return validCommands;
    }

    public List<String> getInvalidCommand() {
        return invalidCommand;
    }

    public void addInvalidCommand(String command) {
        invalidCommand.add(command);
    }

    public void addValidCommand(String command) {

        String[] splitCommand = command.split(" ");

        if (splitCommand[0].equalsIgnoreCase("create") || splitCommand[0].equalsIgnoreCase("deposit") || splitCommand[0].equalsIgnoreCase("withdraw")) {
            insertIntoMap(validCommands, splitCommand[1], command);
        } else if (splitCommand[0].equalsIgnoreCase("transfer")) {
            insertIntoMap(validCommands, splitCommand[1], command);
            insertIntoMap(validCommands, splitCommand[2], command);
        }
    }

    public void insertIntoMap(Map<String, List<String>> map, String ID, String command) {
        if (map.get(ID) != null) {
            map.get(ID).add(command);
        } else if (map.get(ID) == null) {
            map.put(ID, new ArrayList<>());
            map.get(ID).add(command);
        }
    }

    public List<String> getOutput() {
        List<String> output = new ArrayList<>();
        for (String ID : bank.getAccountOrder()) {
            output.add(getFormattedAccountState(ID));
            if (validCommands.get(ID) != null) {
                output.addAll(validCommands.get(ID));
            }
        }
        output.addAll(invalidCommand);
        return output;
    }

    private String getFormattedAccountState(String ID) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        Account account = bank.getAccount().get(ID);
        String accountType = account.getAccountType();
        accountType = accountType.substring(0, 1).toUpperCase() + accountType.substring(1).toLowerCase();
        String balance = decimalFormat.format(account.getBalance());
        String apr = decimalFormat.format(account.getAPR());
        return accountType + " " + account.getID() + " " + balance + " " + apr;
    }
}



