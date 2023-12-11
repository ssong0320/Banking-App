package banking;

public class CommandValidator {
    private CreateValidator createValidator;
    private DepositValidator depositValidator;
    private PassTimeValidator passTimeValidator;
    private WithdrawValidator withdrawValidator;
    private TransferValidator transferValidator;

    public CommandValidator(Bank bank) {
        createValidator = new CreateValidator(bank);
        depositValidator = new DepositValidator(bank);
        passTimeValidator = new PassTimeValidator(bank);
        withdrawValidator = new WithdrawValidator(bank);
        transferValidator = new TransferValidator(bank);
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0].toLowerCase();
        return validateDelegator(command, commandType);
    }

    private boolean validateDelegator(String command, String commandType) {
        boolean isValid = false;
        if ("create".equals(commandType)) {
            isValid = createValidator.validate(command);
        } else if ("deposit".equals(commandType)) {
            isValid = depositValidator.validate(command);
        } else if ("pass".equals(commandType)) {
            isValid = passTimeValidator.validate(command);
        } else if ("withdraw".equals(commandType)) {
            isValid = withdrawValidator.validate(command);
        } else if ("transfer".equals(commandType)) {
            isValid = transferValidator.validate(command);
        }
        return isValid;
    }
}
