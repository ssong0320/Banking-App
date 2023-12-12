package banking;

public class DepositValidator {
    private Bank bank;

    public DepositValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        boolean isValid = true;
        String[] tokens = command.split("\\s+");

        if (tokens.length != 3) {
            return false;
        }

        String accountID = tokens[1];
        String amountStr = tokens[2];

        if (!isValidAccountID(accountID)) {
            return false;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            Account account = bank.getAccountThroughBank(accountID);

            if (account == null) {
                isValid = false;
            } else {
                String accountType = account.getAccountType().toLowerCase();
                isValid = validateAmountByAccountType(amount, accountType);
            }
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateAmountByAccountType(double amount, String accountType) {
        if ("savings".equals(accountType)) {
            return amount >= 0 && amount <= 2500;
        } else if ("checking".equals(accountType)) {
            return amount >= 0 && amount <= 1000;
        } else {
            return false;
        }
    }

    boolean isValidAccountID(String accountID) {
        try {
            Integer.parseInt(accountID);
        } catch (NumberFormatException e) {
            return false;
        }
        return accountID.length() == 8;
    }
}
