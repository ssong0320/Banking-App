package banking;

public class CommandProcessor {
    private Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void evaluateCommand(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0].toLowerCase();
        if ("create".equals(commandType)) {
            String accountType = tokens[1].toLowerCase();
            switch (accountType) {
                case "checking":
                    processCreateChecking(command);
                    break;
                case "saving":
                    processCreateSaving(command);
                    break;
                case "cd":
                    processCreateCD(command);
            }

        } else if ("deposit".equals(commandType)) {
            processDeposit(command);
        } else if ("pass".equals(commandType)) {
            processPassTime(command);
        }
    }

    public void processCreateChecking(String command) {
        String[] tokens = command.split("\\s+");
        Checking checking = new Checking(Double.parseDouble(tokens[3]), tokens[2]);
        bank.addAccount(checking);
    }

    public void processCreateSaving(String command) {
        String[] tokens = command.split("\\s+");
        Saving saving = new Saving(Double.parseDouble(tokens[3]), tokens[2]);
        bank.addAccount(saving);
    }

    public void processCreateCD(String command) {
        String[] tokens = command.split("\\s+");
        CD cd = new CD(Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), tokens[2]);
        bank.addAccount(cd);
    }

    public void processDeposit(String command) {
        String[] tokens = command.split("\\s+");
        bank.depositThroughId(tokens[1], Double.parseDouble(tokens[2]));
    }

    public void processPassTime(String command) {
        String[] tokens = command.split("\\s+");
        bank.passTime(Integer.parseInt(tokens[1]));
    }

}
