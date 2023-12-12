package banking;

public class CreateValidator {
    private Bank bank;

    public CreateValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        if (tokens.length < 4) {
            return false;
        }
        if (!validateAccountType(tokens)) {
            return false;
        }
        return validateCommonConditions(tokens);
    }

    private boolean validateAccountType(String[] tokens) {
        String accountType = tokens[1].toLowerCase();
        switch (accountType) {
            case "cd":
                return tokens.length == 5 && checkAmount(tokens);
            case "checking":
            case "savings":
                return tokens.length == 4;
            default:
                return false;
        }
    }

    private boolean validateCommonConditions(String[] tokens) {
        return isValidAccountID(tokens[2]) && isValidAPR(tokens[3]);
    }

    private boolean checkAmount(String[] tokens) {
        try {
            if ((Double.parseDouble(tokens[4]) < 1000 || Double.parseDouble(tokens[4]) > 10000)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isValidAPR(String apr) {
        try {
            double aprValue = Double.parseDouble(apr);
            if (aprValue >= 0 && aprValue <= 10) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidAccountID(String accountID) {
        try {
            Integer.parseInt(accountID);
        } catch (NumberFormatException e) {
            return false;
        }
        if (accountID.length() != 8) {
            return false;
        }
        if (bank.getAccountThroughBank(accountID) != null) {
            return false;
        }
        return true;
    }

}