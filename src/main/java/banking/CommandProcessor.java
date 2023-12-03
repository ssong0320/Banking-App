package banking;

public class CommandProcessor {
    private Bank bank;
    private CreateCDProcessor createCDProcessor;
    private CreateCheckingProcessor createCheckingProcessor;
    private CreateSavingsProcessor createSavingsProcessor;
    private DepositProcessor depositProcessor;
    private PassTimeProcessor passTimeProcessor;
    private WithdrawProcessor withdrawProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.createCDProcessor = new CreateCDProcessor(bank);
        this.createCheckingProcessor = new CreateCheckingProcessor(bank);
        this.createSavingsProcessor = new CreateSavingsProcessor(bank);
        this.depositProcessor = new DepositProcessor(bank);
        this.passTimeProcessor = new PassTimeProcessor(bank);
        this.withdrawProcessor = new WithdrawProcessor(bank);
    }

    public void evaluateCommand(String command) {
        String[] tokens = command.split("\\s+");
        String commandType = tokens[0].toLowerCase();
        if ("create".equals(commandType)) {
            String accountType = tokens[1].toLowerCase();
            switch (accountType) {
                case "checking":
                    createCheckingProcessor.processCreateChecking(command);
                    break;
                case "saving":
                    createSavingsProcessor.processCreateSaving(command);
                    break;
                case "cd":
                    createCDProcessor.processCreateCD(command);
            }

        } else if ("deposit".equals(commandType)) {
            depositProcessor.processDeposit(command);
        } else if ("pass".equals(commandType)) {
            passTimeProcessor.processPassTime(command);
        } else if ("withdraw".equals(commandType)) {
            withdrawProcessor.processWithdraw(commandType);
        }
    }

}
