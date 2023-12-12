package banking;

public class CreateCheckingProcessor {
    private static Bank bank;

    public CreateCheckingProcessor(Bank inputBank) {
        CreateCheckingProcessor.bank = inputBank;
    }

    public static void processCreateChecking(String command) {
        String[] commandParts = command.split(" "); // Changed variable name
        double depositAmount = Double.parseDouble(commandParts[3]); // Changed operation order
        String accountId = commandParts[2];
        Checking checking = new Checking(depositAmount, accountId); // Changed the order of arguments
        bank.addAccount(checking);
    }
}

