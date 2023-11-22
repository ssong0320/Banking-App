package banking;

public class PassTimeValidator {
    private Bank bank;

    public PassTimeValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length != 2) {
            return false;
        }

        int Months = Integer.parseInt(tokens[1]);

        if (Months < 0 && Months > 60) {
            return false;
        }
        return true;
    }
}
