package banking;

public class CreateCDProcessor {
    private static Bank bank;

    public CreateCDProcessor(Bank inputBank) {
        CreateCDProcessor.bank = inputBank;
    }

    public static void processCreateCD(String command) {
        String[] tokens = command.split("\\s+");
        CD cd = new CD(Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), tokens[2]);
        bank.addAccount(cd);
    }
}
