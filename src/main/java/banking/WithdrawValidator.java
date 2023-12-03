package banking;

public class WithdrawValidator {
    private Bank bank;

    public WithdrawValidator(Bank bank) {
        this.bank = bank;
    }

    public static double isDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean validateChecking(String command, Bank bank) {

        try {
            Account account = bank.getAccount().get(command);
            return account instanceof Checking;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean validateSaving(String command, Bank bank) {

        try {
            Account account = bank.getAccount().get(command);
            return account instanceof Saving;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean validateCd(String command, Bank bank) {

        try {
            Account account = bank.getAccount().get(command);
            return account instanceof CD;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean validateAccountExistInBank(String command, Bank bank) {
        try {
            return bank.getAccount().containsKey(command);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");

        if (validateAccountExistInBank(tokens[1], bank)) {
            if (validateChecking(tokens[1], bank)) {
                return withdrawCommandCheckingValidate(tokens[2]);
            } else if (validateSaving(tokens[1], bank)) {
                return withdrawCommandSavingValidate(tokens[2]) && validateMonth(tokens[1]);
            } else if (validateCd(tokens[1], bank)) {
                return withdrawCommandCdValidate(tokens[1]) && validateMonthTwelve(tokens[1]);
            }
        }
        return false;
    }


    public boolean withdrawCommandCheckingValidate(String command) {
        double amount = isDouble(command);
        return (amount >= 0 && amount <= 400);
    }

    public boolean withdrawCommandSavingValidate(String command) {
        double amount = isDouble(command);
        return (amount >= 0 && amount <= 1000);
    }

    public boolean withdrawCommandCdValidate(String command) {
        double amount = isDouble(command);
        double balance = bank.getAccount().get(command).getBalance();
        return amount >= balance;
    }

    public boolean validateMonthTwelve(String command) {
        int number = bank.getAccount().get(command).getMonths();
        return number >= 12 && number % 12 == 0;
    }

    public boolean validateMonth(String command) {
        Saving account = (Saving) bank.getAccount().get(command);
        return !account.getMonthWithdrawal();
    }
}

