package banking;

public class CommandValidator {
    private CreateValidator createValidator;
    private DepositValidator depositValidator;
    private PassTimeValidator passTimeValidator;

    public CommandValidator(Bank bank) {
        createValidator = new CreateValidator(bank);
        depositValidator = new DepositValidator(bank);
        passTimeValidator = new PassTimeValidator(bank);
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0].toLowerCase();
        if ("create".equals(commandType)) {
            return createValidator.validate(command);
        } else if ("deposit".equals(commandType)) {
            return depositValidator.validate(command);
        } else if ("pass".equals(commandType)) {
            return passTimeValidator.validate(command);
        }
        return false;
    }
}
