package fintech.driver;

import fintech.model.Account;
import fintech.model.DepositTransaction;
import fintech.model.WithdrawTransaction;
import fintech.model.TransferTransaction;
import fintech.model.NegativeBalanceException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * @author 12S24005 Mia Nathania Sibuea
 */
public class Driver3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Object> transactions = new ArrayList<>();
        int transactionId = 1;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("---")) {
                break;
            }

            String[] parts = line.split("#");
            String command = parts[0];

            if (command.equals("create-account")) {
                String name = parts[1];
                String username = parts[2];
                Account account = new Account(name, username);
                accounts.add(account);
            } else if (command.equals("deposit")) {
                String username = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String timestamp = parts[3];
                String description = parts[4];

                for (Account account : accounts) {
                    if (account.getUsername().equals(username)) {
                        account.deposit(amount);
                        DepositTransaction dt = new DepositTransaction(transactionId++, username, amount, timestamp, description);
                        account.addTransaction(dt);
                        transactions.add(dt);
                        break;
                    }
                }
            } else if (command.equals("withdraw")) {
                String username = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String timestamp = parts[3];
                String description = parts[4];

                for (Account account : accounts) {
                    if (account.getUsername().equals(username)) {
                        if (!account.withdraw(amount)) {
                            try {
                                throw new NegativeBalanceException("Insufficient balance for withdrawal");
                            } catch (NegativeBalanceException e) {
                            }
                        } else {
                            WithdrawTransaction wt = new WithdrawTransaction(transactionId++, username, amount, timestamp, description);
                            account.addTransaction(wt);
                            transactions.add(wt);
                        }
                        break;
                    }
                }
            } else if (command.equals("transfer")) {
                String sender = parts[1];
                String receiver = parts[2];
                double amount = Double.parseDouble(parts[3]);
                String timestamp = parts[4];
                String description = parts[5];

                Account senderAccount = null;
                Account receiverAccount = null;

                for (Account account : accounts) {
                    if (account.getUsername().equals(sender)) {
                        senderAccount = account;
                    }
                    if (account.getUsername().equals(receiver)) {
                        receiverAccount = account;
                    }
                }

                if (senderAccount != null && receiverAccount != null) {
                    if (!senderAccount.transfer(amount)) {
                        try {
                            throw new NegativeBalanceException("Insufficient balance for transfer");
                        } catch (NegativeBalanceException e) {
                        }
                    } else {
                        receiverAccount.receive(amount);
                        TransferTransaction tt = new TransferTransaction(transactionId++, sender, receiver, amount, timestamp, description);
                        senderAccount.addTransaction(tt);
                        transactions.add(tt);
                    }
                }
            }
        }

        for (Account account : accounts) {
            System.out.println(account.getUsername() + "|" + account.getName() + "|" + account.getBalance());
        }

        scanner.close();
    }

}