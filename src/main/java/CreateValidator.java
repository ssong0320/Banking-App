public class CreateValidator {
    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        String accountType = tokens[1];
        String accountID = tokens[2];
        String apr = tokens[3];

        switch (accountType) {
            case "cd":
                if (tokens.length != 5) {
                    return false;
                }
                try {
                    double cdBalance = Double.parseDouble(tokens[4]);
                    if (cdBalance <= 1000 || cdBalance >= 10000) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            case "checking":
            case "savings":
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

        // Validate APR
        if (!isValidAPR(apr)) {
            return false;
        }
        return true;
    }

    private boolean isValidAPR(String apr) {
        try {
            double aprValue = Double.parseDouble(apr.replace("%", ""));
            if (aprValue <= 10 || aprValue >= 0) {
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
            double ID = Double.parseDouble(accountID);
            return accountID.matches("\\d{8}");
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
