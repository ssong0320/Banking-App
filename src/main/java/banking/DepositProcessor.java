package banking;

public class DepositProcessor {
    private Bank bank;

    public DepositProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processDeposit(String command) {
        String[] tokens = command.split("\\s+");
        bank.depositThroughId(tokens[1], Double.parseDouble(tokens[2]));
    }
}
