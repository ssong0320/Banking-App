package banking;

public class PassTimeProcessor {

    private Bank bank;

    public PassTimeProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processPassTime(String command) {
        String[] tokens = command.split("\\s+");
        bank.passTime(Integer.parseInt(tokens[1]));
    }
}
