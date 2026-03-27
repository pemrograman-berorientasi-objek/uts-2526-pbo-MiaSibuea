package fintech.model;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private String username;
    private String name;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name, String username) {
        this.name = name;
        this.username = username;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if(balance - amount < 0) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean transfer(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void receive(double amount) {
        balance += amount;
    }

}
