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
        String accountID = tokens[2];
        String apr = tokens[3];

        switch (accountType) {
            case "cd":
                if (tokens.length != 5) {
                    return false;
                }
                try {
                    double cdBalance = Double.parseDouble(tokens[4]);
                    if (cdBalance < 1000 || cdBalance > 10000) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                break;
            case "checking":
            case "saving":
                if (tokens.length != 4) {
                    return false;
                }
                break;
            default:
                return false;
        }
        if (!isValidAccountID(accountID)) {
            return false;
        }
        if (!isValidAPR(apr)) {
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
            int id = Integer.parseInt(accountID);
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


