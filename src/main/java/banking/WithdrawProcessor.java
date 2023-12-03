package banking;

public class WithdrawProcessor {

    private final Bank bank;

    public WithdrawProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processWithdraw(String withdrawCommand) {
        String[] splitCommand = withdrawCommand.split(" ");

        if (splitCommand[0].equalsIgnoreCase("withdraw")) {
            bank.withdrawThroughId(splitCommand[1], Float.parseFloat(splitCommand[2]));
        }
    }
}
