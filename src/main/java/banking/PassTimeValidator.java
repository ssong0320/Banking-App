package banking;

public class PassTimeValidator {

    public PassTimeValidator(Bank bank) {
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length == 2) {
            return validateMonth(tokens[1]);
        } else {
            return false;
        }
    }

    public boolean validateMonth(String command) {

        try {
            Integer.valueOf(command);
        } catch (NumberFormatException ex) {
            return false;
        }
        int newMonths = Integer.valueOf(command);

        return newMonths >= 1 && newMonths <= 60;

    }
}
