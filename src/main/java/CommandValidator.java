public class CommandValidator {
    private CreateValidator createValidator;
    private DepositValidator depositValidator;

    public CommandValidator() {
        createValidator = new CreateValidator();
        depositValidator = new DepositValidator();
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0];
        if (commandType != "Create" || commandType != "Deposit") {
            return false;
        }
        if (commandType == "Create") {
            return createValidator.validate(command);
        } else if (commandType == "Deposit") {
            return depositValidator.validate(command);
        }

        return false;
    }
}
