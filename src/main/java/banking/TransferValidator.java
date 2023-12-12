package banking;

public class TransferValidator {
    private Bank bank;

    public TransferValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        if (!isCommandValid(tokens)) {
            return false;
        }

        if (!areAccountsValid(tokens[1], tokens[2])) {
            return false;
        }

        Account sourceAccount = bank.getAccountThroughBank(tokens[1]);
        return isTransferValid(sourceAccount, tokens[3]);
    }

    private boolean isCommandValid(String[] tokens) {
        return tokens.length == 4;
    }

    private boolean areAccountsValid(String sourceAccountId, String destinationAccountId) {
        return isValidAccountID(sourceAccountId) && isValidAccountID(destinationAccountId)
                && bank.getAccountThroughBank(sourceAccountId) != null
                && bank.getAccountThroughBank(destinationAccountId) != null;
    }

    private boolean isTransferValid(Account account, String amountToTransfer) {
        String accountType = account.getAccountType();
        switch (accountType) {
            case "checking":
                return validateCheckingTransfer(amountToTransfer);
            case "savings":
                return validateSavingTransfer(account, amountToTransfer) && validateMonth(account.getID());
            default:
                return false;
        }
    }

    boolean isValidAccountID(String accountID) {
        try {
            Integer.parseInt(accountID);
            return accountID.length() == 8;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCheckingTransfer(String amountToTransfer) {
        double amount = isDouble(amountToTransfer);
        return (amount >= 0 && amount <= 400);
    }

    private boolean validateSavingTransfer(Account account, String amountToTransfer) {
        double amount = isDouble(amountToTransfer);
        Saving savingAccount = (Saving) account;
        return (amount >= 0 && amount <= 1000 && !savingAccount.getMonthWithdrawal());
    }

    double isDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private boolean validateMonth(String command) {
        Saving account = (Saving) bank.getAccountThroughBank(command);
        return !account.getMonthWithdrawal();
    }
}
