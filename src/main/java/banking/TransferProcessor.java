package banking;

public class TransferProcessor {

    private final Bank bank;

    public TransferProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processTransfer(String withdrawalCommand) {
        String[] splitCommand = withdrawalCommand.split(" ");

        if (splitCommand[0].equalsIgnoreCase("transfer")) {
            bank.transfer(splitCommand[1], splitCommand[2], Float.parseFloat(splitCommand[3]));
        }
    }
}
