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
        String accountType = tokens[1].toLowerCase();
        boolean isValid = true;
        switch (accountType) {
            case "cd":
                if ((tokens.length != 5) || !checkAmount(tokens)) {
                    isValid = false;
                }
                break;
            case "checking":
            case "savings":
                if (tokens.length != 4) {
                    isValid = false;
                }
                break;
            default:
                isValid = false;
        }
        if (!isValidAccountID(tokens[2]) || !isValidAPR(tokens[3])) {
            isValid = false;
        }
        return isValid;
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


