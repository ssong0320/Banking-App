package banking;

public class CreateCheckingProcessor {
    private Bank bank;

    public CreateCheckingProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processCreateChecking(String command) {
        String[] tokens = command.split("\\s+");
        Checking checking = new Checking(Double.parseDouble(tokens[3]), tokens[2]);
        bank.addAccount(checking);
    }
}
