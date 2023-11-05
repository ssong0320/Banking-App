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

        try {
            double amount = Double.parseDouble(amountStr);
            Account account = bank.getAccountThroughBank(accountID);

            if (account.toString().equalsIgnoreCase("savings")) {
                if (amount >= 0 && amount <= 2500) {
                    return true;
                } else {
                    return false;
                }
            } else if (account.toString().equalsIgnoreCase("checking")) {
                if (amount >= 0 && amount <= 1000) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
