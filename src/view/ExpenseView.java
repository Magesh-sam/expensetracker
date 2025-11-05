package view;

import context.AppContext;
import controller.TransactionController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.dto.Category;
import model.dto.CategoryAmount;
import model.dto.PaymentMethod;
import model.dto.Transaction;
import model.dto.Transaction.TransactionType;
import util.Input;
import util.Print;

public class ExpenseView {

    private static ExpenseView expenseView;
    private final TransactionController transactionController = TransactionController.getInstance();
    private static final int currentUserId = AppContext.getCurrentUser().getUserId();

    private ExpenseView() {
    }

    public static ExpenseView getInstance() {
        if (expenseView == null) {
            expenseView = new ExpenseView();
        }
        return expenseView;
    }

    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println(
                    "1. Add Expense\n2. Edit Expense\n3. Delete Expense\n4. List Expenses\n5. List Expenses by Category\n6. List Expenses by Date Range\n7. List Expenses by Date\n8. List Top Expenses\n9. List Top Categoriesby expense\n10. Go back");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 ->
                    addExpense();
                case 2 ->
                    editExpense();
                case 3 ->
                    deleteExpense();
                case 4 ->
                    listExpenses();
                case 5 ->
                    listExpensesByCategory();
                case 6 ->
                    listExpensesByDateRange();
                case 7 ->
                    listExpensesByDate();
                case 8 -> {
                    listTopExpenses();
                }
                case 9 -> {
                    listTopCategoriesByExpense();
                }
                case 10 -> {

                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addExpense() {
        System.out.println("=== Add Expense ===");
        String expenseName = Input.getString("expense title");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategories();
        printCategoryList(categories);

        int choice;
        while (true) {
            choice = Input.getInt("Category number (1-" + categories.size() + ")");
            if (choice >= 1 && choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }
        int categoryId = categories.get(choice - 1).getCategoryId();
        // resetting chocie for next input
        choice = 0;
        List<PaymentMethod> paymentMethods = AppContext.getPaymentController().getAllPaymentMethods();

        printPaymentMethodList(paymentMethods);
        System.out.println("Select Payment Method");
        while (true) {
            choice = Input.getInt("Payment method number (1-" + paymentMethods.size() + ")");
            if (choice >= 1 && choice <= paymentMethods.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int paymentMethodId = paymentMethods.get(choice - 1).getPaymentMethodId();

        BigDecimal amount = Input.getBigDecimal("amount");

        Transaction transaction = new Transaction(categoryId, currentUserId, paymentMethodId,
                amount, TransactionType.expense, LocalDateTime.now(), expenseName);

        int result = transactionController.createTransaction(transaction);
        if (result > 0) {
            System.out.println("Expense added successfully!");
        } else {
            System.out.println("Failed to add expense.");
        }
    }

    private void editExpense() {
        System.out.println("=== Edit Expense ===");
        List<Transaction> expenses = transactionController.getExpensesByUserId(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        Print.displayTransactionList(expenses);
        int choice = Input.getInt("expense number to edit (1-" + expenses.size() + ")");

        if (choice < 1 || choice > expenses.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Transaction existingExpense = expenses.get(choice - 1);
        // reset
        choice = 0;
        System.out.println("press enter/0 to keep the values unchanged");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategories();
        printCategoryList(categories);

        while (true) {
            choice = Input.getInt("Payment method number (1-" + categories.size() + ")");
            if (choice >= 0 || choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int newCategoryId = choice == 0 ? existingExpense.getCategoryId() : expenses.get(choice - 1).getCategoryId();
        // resetting chocie for next input
        choice = 0;
        List<PaymentMethod> paymentMethods = AppContext.getPaymentController().getAllPaymentMethods();

        printPaymentMethodList(paymentMethods);
        System.out.println("Select Payment Method");
        while (true) {
            choice = Input.getInt("Payment method number (1-" + paymentMethods.size() + ")");
            if (choice >= 0 || choice <= paymentMethods.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int paymentMethodId = choice == 0 ? existingExpense.getPaymentMethodId()
                : paymentMethods.get(choice - 1).getPaymentMethodId();

        BigDecimal amount = Input.getBigDecimal("amount");
        amount = amount.doubleValue() == 0 ? existingExpense.getAmount() : amount;
        existingExpense.setCategoryId(newCategoryId);
        existingExpense.setPaymentMethodId(paymentMethodId);
        existingExpense.setAmount(amount);
        if (transactionController.updateTransaction(existingExpense)) {
            System.out.println("Expense updated successfully!");
        } else {
            System.out.println("Failed to update expense.");
        }
    }

    private void deleteExpense() {
        System.out.println("=== Delete Expense ===");
        List<Transaction> expenses = transactionController.getExpensesByUserId(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        Print.displayTransactionList(expenses);
        int choice = Input.getInt("expense number to delete (1-" + expenses.size() + ")");

        if (choice < 1 || choice > expenses.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Transaction expense = expenses.get(choice - 1);
        if (transactionController.deleteTransaction(expense.getTransactionId(), currentUserId)) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Failed to delete expense.");
        }
    }

    private void listExpenses() {
        System.out.println("=== All Expenses ===");
        List<Transaction> expenses = transactionController.getExpensesByUserId(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Print.displayTransactionList(expenses);
    }

    private void listExpensesByCategory() {
        System.out.println("=== Expenses by Category ===");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategories();
        printCategoryList(categories);

        int choice;
        while (true) {
            choice = Input.getInt("category number (1-" + categories.size() + ")");
            if (choice >= 1 || choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int categoryId = categories.get(choice - 1).getCategoryId();
        List<Transaction> expenses = transactionController.getExpensesByCategory(currentUserId, categoryId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found for this category.");
            return;
        }
        Print.displayTransactionList(expenses);
    }

    private void listExpensesByDateRange() {
        System.out.println("=== Expenses by Date Range ===");
        System.out.println("Enter start date (YYYY-MM-DD):");
        String startDateStr = Input.getString("start date");
        System.out.println("Enter end date (YYYY-MM-DD):");
        String endDateStr = Input.getString("end date");

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            List<Transaction> expenseTransactionsByDateRange = transactionController.getExpenseTransactionsByDateRange(
                    currentUserId,
                    startDate, endDate);
            if (expenseTransactionsByDateRange == null || expenseTransactionsByDateRange.isEmpty()) {
                System.out.println("No expenses found.");
                return;
            }

            Print.displayTransactionList(expenseTransactionsByDateRange);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void listExpensesByDate() {
        System.out.println("=== Expenses by Date ===");
        System.out.println("Enter date (YYYY-MM-DD):");
        String dateStr = Input.getString("date");

        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Transaction> expenses = transactionController.getExpenseByDate(currentUserId, date);
            if (expenses == null || expenses.isEmpty()) {
                System.out.println("No expenses found for this date.");
                return;
            }

            Print.displayTransactionList(expenses);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void listTopExpenses() {
        List<Transaction> expenses = transactionController.getTopFiveExpensesByAmount(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Print.displayTransactionList(expenses);
    }

    private void listTopCategoriesByExpense() {

        List<CategoryAmount> expenses = transactionController.getTopFiveExpenseCategoriesByAmount(currentUserId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No records found");
        }
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + " | " + expenses.get(i).getName() + " | " + expenses.get(i).getAmount());
        }

    }

    private void printCategoryList(List<Category> categories) {
        System.out.println("Category List:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + "." + categories.get(i).getName());
        }

    }

    private void printPaymentMethodList(List<PaymentMethod> paymentMethods) {
        System.out.println("Payment Type List:");
        for (int i = 0; i < paymentMethods.size(); i++) {
            System.out.println((i + 1) + "." + paymentMethods.get(i).getName());
        }

    }
}
