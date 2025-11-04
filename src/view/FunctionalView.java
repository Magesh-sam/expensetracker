package view;

import java.util.List;

import context.AppContext;
import controller.TransactionController;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import util.Input;

public class FunctionalView {

    private static FunctionalView functionalView;
    private static final TransactionController transactionController = AppContext.getTransactionController();
    private static int currentUserId = AppContext.getCurrentUser().getUserId();

    private FunctionalView() {
    }

    public static FunctionalView getInstance() {
        if (functionalView == null) {
            functionalView = new FunctionalView();
        }
        return functionalView;
    }

    public void displayMenu() {
        printStats();
        int choice;

        while (true) {
            System.out.println("1. Manage Expenses\n2. Manage Incomes\n3. View Reports\n4. Logout");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 -> {
                    manageExpenses();
                }
                case 2 -> {
                    manageIncomes();
                }
                case 3 -> {
                    viewReports();
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private void manageExpenses() {
        System.out.println("Manage Expenses");
        AppContext.getExpenseView().displayMenu();
    }

    private void manageIncomes() {
        System.out.println("Manage Incomes");
        AppContext.getIncomeView().displayMenu();

    }

    private void viewReports() {
        printStats();
        listTopExpenses();
        listTopIncomes();
        listTopCategoriesByAmount();

    }

    private void listTopExpenses() {
        List<Transaction> expenses = transactionController.getTopFiveExpensesByAmount(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("===Top Expenses===");
        displayTransactionList(expenses);
    }

    private void listTopIncomes() {
        List<Transaction> incomes = transactionController.getTopFiveIncomeByAmount(currentUserId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found.");
            return;
        }
        System.out.println("===Top Incomes===");
        displayTransactionList(incomes);
    }

    private void listTopCategoriesByAmount() {

        List<CategoryAmount> item = transactionController.getTopFiveExpenseCategoriesByAmount(currentUserId);
        if (item == null || item.isEmpty()) {
            System.out.println("No records found");
        }
        System.out.println("===Top Categories By Amount== ");
        for (int i = 0; i < item.size(); i++) {
            System.out.println((i + 1) + " | " + item.get(i).getName() + " | " + item.get(i).getAmount());
        }

    }

    private void displayTransactionList(List<Transaction> transactions) {

        for (int i = 0; i < transactions.size(); i++) {
            System.out
                    .println((i + 1) + " | " + transactions.get(i).getName() + " | " + transactions.get(i).getAmount());
        }

    }

    private void printStats() {
        int totalIncome = transactionController.getTotalIncome(currentUserId);
        int totalExpense = transactionController.getTotalExpense(currentUserId);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Remaining: " + (totalIncome - totalExpense));

    }

}
