package banking;

public class TransferValidator {
    private Bank bank;

    public TransferValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (tokens.length != 4) {
            return false;
        }

        String sourceAccountID = tokens[1];
        String destinationAccountID = tokens[2];
        String amountToTransfer = tokens[3];

        if (!isValidAccountID(sourceAccountID) || !isValidAccountID(destinationAccountID)) {
            return false;
        }

        Account sourceAccount = bank.getAccountThroughBank(sourceAccountID);
        Account destinationAccount = bank.getAccountThroughBank(destinationAccountID);

        if (sourceAccount == null || destinationAccount == null) {
            return false;
        }

        switch (sourceAccount.getAccountType()) {
            case "checking":
                return validateCheckingTransfer(sourceAccount, amountToTransfer);
            case "savings":
                return validateSavingTransfer(sourceAccount, amountToTransfer) && validateMonth(sourceAccountID);
            default:
                return false;
        }
    }

    boolean isValidAccountID(String accountID) {
        try {
            int id = Integer.parseInt(accountID);
            return accountID.length() == 8;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCheckingTransfer(Account account, String amountToTransfer) {
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
