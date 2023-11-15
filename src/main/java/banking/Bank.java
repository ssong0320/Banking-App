package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.put(account.getID(), account);
    }

    public void depositThroughId(String id, double amount) {
        Account account = accounts.get(id);
        account.depositMoney(amount);
    }

    public void withdrawThroughId(String id, double amount) {
        Account account = accounts.get(id);
        account.withdrawMoney(amount);
    }

    public Account getAccountThroughBank(String id) {
        return accounts.get(id);
    }
}
