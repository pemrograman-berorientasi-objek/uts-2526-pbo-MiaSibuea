package fintech.driver;

import fintech.model.Account;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * @author  Mia Nathania Sibuea
 */
public class Driver1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();

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
            }
        }

        for (Account account : accounts) {
            System.out.println(account.getUsername() + "|" + account.getName() + "|" + account.getBalance());
        }

        scanner.close();
    }

}