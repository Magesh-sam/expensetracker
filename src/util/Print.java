package util;

import java.util.List;

import model.dto.Transaction;
import model.dto.Category;
import model.dto.PaymentMethod;

public class Print {
    private Print() {
    }

    public static void displayTransactionList(List<Transaction> transactions) {

        for (int i = 0; i < transactions.size(); i++) {
            System.out
                    .println((i + 1) + " | " + transactions.get(i).getName() + " | " + transactions.get(i).getAmount());
        }

    }

    public static void printCategoryList(List<Category> categories) {
        System.out.println("Category List:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + "." + categories.get(i).getName());
        }

    }

    public static void printPaymentMethodList(List<PaymentMethod> paymentMethods) {
        System.out.println("Payment Type List:");
        for (int i = 0; i < paymentMethods.size(); i++) {
            System.out.println((i + 1) + "." + paymentMethods.get(i).getName());
        }

    }
}
