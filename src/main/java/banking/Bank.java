package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private final ArrayList<String> accountOrder = new ArrayList<>();
    private final HashMap<String, Account> accounts = new HashMap<>();

    HashMap<String, Account> getAccount() {
        return accounts;
    }

    public List<String> getAccountOrder() {
        return accountOrder;
    }


    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.put(account.getID(), account);
        accountOrder.add(account.getID());
    }

    public void depositThroughId(String id, double amount) {
        Account account = accounts.get(id);
        account.depositMoney(amount);
    }

    public double withdrawThroughId(String id, double amount) {
        Account account = accounts.get(id);
        account.withdrawMoney(amount);
        return amount;
    }

    public Account getAccountThroughBank(String id) {
        return accounts.get(id);
    }

    public void pass(int months) {
        List<String> accountClose = new ArrayList<>();

        for (String ID : accounts.keySet()) {
            Account account = accounts.get(ID);
            if (account.balance < 100 && account.balance > 0) {
                account.balance -= 25;
            }

            if (account.balance == 0) {
                accountClose.add(ID);
                continue;
            }
            account.calculateApr(months);
        }

        for (String ID : accountClose) {
            accounts.remove(ID);
            accountOrder.remove(ID);
        }
    }

    public void transfer(String fromId, String toId, double amount) {
        accounts.get(toId).depositMoney(withdrawThroughId(fromId, amount));
    }
}
