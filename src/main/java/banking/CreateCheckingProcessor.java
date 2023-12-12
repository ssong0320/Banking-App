package banking;

public class CreateCheckingProcessor {
    private static Bank bank;

    public CreateCheckingProcessor(Bank inputBank) {
        CreateCheckingProcessor.bank = inputBank;
    }

    public static void processCreateChecking(String command) {
        String[] args = command.split(" ");
        Checking checking = new Checking(Double.parseDouble(args[3]), args[2]);
        bank.addAccount(checking);
    }
}
