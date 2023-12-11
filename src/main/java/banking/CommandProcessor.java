package banking;

public class CommandProcessor {
    private CreateCDProcessor createCDProcessor;
    private CreateCheckingProcessor createCheckingProcessor;
    private CreateSavingsProcessor createSavingsProcessor;
    private DepositProcessor depositProcessor;
    private PassTimeProcessor passTimeProcessor;
    private WithdrawProcessor withdrawProcessor;
    private TransferProcessor transferProcessor;

    public CommandProcessor(Bank bank) {
        this.createCDProcessor = new CreateCDProcessor(bank);
        this.createCheckingProcessor = new CreateCheckingProcessor(bank);
        this.createSavingsProcessor = new CreateSavingsProcessor(bank);
        this.depositProcessor = new DepositProcessor(bank);
        this.passTimeProcessor = new PassTimeProcessor(bank);
        this.withdrawProcessor = new WithdrawProcessor(bank);
        this.transferProcessor = new TransferProcessor(bank);
    }

    public void evaluateCommand(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0].toLowerCase();
        processDelegator(tokens, command, commandType);
    }

    private void processDelegator(String[] tokens, String command, String commandType) {
        if ("create".equals(commandType)) {
            processCreate(command, tokens);
        } else if ("deposit".equals(commandType)) {
            depositProcessor.processDeposit(command);
        } else if ("pass".equals(commandType)) {
            passTimeProcessor.processPassTime(command);
        } else if ("withdraw".equals(commandType)) {
            withdrawProcessor.processWithdraw(command);
        } else if ("transfer".equals(commandType)) {
            transferProcessor.processTransfer(command);
        }
    }

    private void processCreate(String command, String[] tokens) {
        String accountType = tokens[1].toLowerCase();
        switch (accountType) {
            case "checking":
                createCheckingProcessor.processCreateChecking(command);
                break;
            case "savings":
                createSavingsProcessor.processCreateSaving(command);
                break;
            case "cd":
                createCDProcessor.processCreateCD(command);

            default:
                break;

        }
    }
}
