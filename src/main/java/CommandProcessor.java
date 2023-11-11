public class CommandProcessor {
    private Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
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

}
