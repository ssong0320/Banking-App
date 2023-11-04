public class CommandValidator {
    private CreateValidator createValidator;
    private DepositValidator depositValidator;

    public CommandValidator(Bank bank) {
        createValidator = new CreateValidator(bank);
        depositValidator = new DepositValidator(bank);
    }

    public boolean validate(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0];
        if (!"Create".equals(commandType) && !"Deposit".equals(commandType)) {
            return false;
        }
        if ("Create".equals(commandType)) {
            return createValidator.validate(command);
        } else if ("Deposit".equals(commandType)) {
            return depositValidator.validate(command);
        }

        return false;
    }
}
