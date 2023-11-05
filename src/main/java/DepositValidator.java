public class DepositValidator {
    private Bank bank;

    public DepositValidator(Bank bank) {
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

        try {
            double amount = Double.parseDouble(amountStr);
            Account account = bank.getAccountThroughBank(accountID);

            if (account == null) {
                return false;
            }

            if (account instanceof Saving) {
                if (amount < 0 || amount > 2500) {
                    return false;
                }
            } else if (account instanceof Checking) {
                if (amount < 0 || amount > 1000) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
