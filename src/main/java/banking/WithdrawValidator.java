package banking;

public class WithdrawValidator {
    private Bank bank;

    public WithdrawValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length != 3) {
            return false;
        }

        String accountID = tokens[1];
        String amountStr = tokens[2];

        if (!isValidAccountID(accountID)) {
            return false;
        }
    }

    private boolean isValidAccountID(String accountID) {
        try {
            int id = Integer.parseInt(accountID);
        } catch (NumberFormatException e) {
            return false;
        }
        if (accountID.length() != 8) {
            return false;
        }
        return true;
    }
}
