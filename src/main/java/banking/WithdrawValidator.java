package banking;

public class WithdrawValidator {
    private Bank bank;

    public WithdrawValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length < 3) {
            return false;
        }

        String accountID = tokens[1];
        String amountToWithdraw = tokens[2];

        if (!isValidAccountID(accountID)) {
            return false;
        }

        Account account = bank.getAccountThroughBank(accountID);
        if (account == null) {
            return false;
        }

        switch (account.getAccountType()) {
            case "checking":
                return validateCheckingWithdraw(amountToWithdraw);
            case "savings":
                return validateSavingWithdraw(account, amountToWithdraw);
            case "cd":
                return validateCDWithdraw(account, amountToWithdraw) && validateMonthTwelve(tokens[1]);
            default:
                return false;
        }
    }

    boolean isValidAccountID(String accountID) {
        try {
            int id = Integer.parseInt(accountID);
        } catch (NumberFormatException e) {
            return false;
        }
        return accountID.length() == 8;
    }

    private boolean validateCheckingWithdraw(String amountToWithdraw) {
        double amount = isDouble(amountToWithdraw);
        return (amount >= 0 && amount <= 400);
    }

    private boolean validateSavingWithdraw(Account account, String amountToWithdraw) {
        double amount = isDouble(amountToWithdraw);
        Saving savingAccount = (Saving) account;
        return (amount >= 0 && amount <= 1000 && !savingAccount.getMonthWithdrawal());
    }

    private boolean validateCDWithdraw(Account account, String amountToWithdraw) {
        double amount = isDouble(amountToWithdraw);
        double balance = account.getBalance();
        return (amount >= balance);
    }

    double isDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean validateMonthTwelve(String command) {
        int number = bank.getAccount().get(command).getMonths();
        return number >= 12 && number % 12 == 0;
    }

}
