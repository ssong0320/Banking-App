package banking;

public class CreateCheckingProcessor {
    private static Bank bank;

    public CreateCheckingProcessor(Bank inputBank) {
        CreateCheckingProcessor.bank = inputBank;
    }

    public static void processCreateChecking(String command) {
        String[] tokens = command.split("\\s+");
        Checking checking = new Checking(Double.parseDouble(tokens[3]), tokens[2]);
        bank.addAccount(checking);
    }
}
