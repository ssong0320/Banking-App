package banking;

public class WithdrawValidator {
    private Bank bank;

    public WithdrawValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] tokens = command.split(" ");

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
