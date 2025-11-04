package util;

import java.util.List;

import model.dto.Transaction;

public class Print {
    private Print() {
    }

    public static void displayTransactionList(List<Transaction> transactions) {

        for (int i = 0; i < transactions.size(); i++) {
            System.out
                    .println((i + 1) + " | " + transactions.get(i).getName() + " | " + transactions.get(i).getAmount());
        }

    }
}
