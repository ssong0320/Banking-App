package banking;

public class CreateSavingsProcessor {
    private static Bank bank;

    public CreateSavingsProcessor(Bank bank) {
        this.bank = bank;
    }

    public static void processCreateSaving(String command) {
        String[] tokens = command.split("\\s+");
        Saving saving = new Saving(Double.parseDouble(tokens[3]), tokens[2]);
        bank.addAccount(saving);
    }
}
