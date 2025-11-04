package view;

import context.AppContext;
import controller.TransactionController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import model.dto.Category;
import model.dto.Transaction;
import model.dto.Transaction.TransactionType;
import util.Input;

public class ExpenseView {

    private static ExpenseView expenseView;
    private final TransactionController transactionController = TransactionController.getInstance();
    private int currentUserId = 1; // Assuming logged in user ID

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
            System.out.println("1. Add Expense\n2. Edit Expense\n3. Delete Expense\n4. List Expenses\n5. List Expenses by Category\n6. List Expenses by Date Range\n7. List Expenses by Date\n8. List Expenses by Month and Year\n9.Go back");
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
                case 8 ->
                    listExpensesByMonthAndYear();
                case 9 -> {
                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addExpense() {
        System.out.println("=== Add Expense ===");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategory();
        printCategoryList(categories);

        int choice = Input.getInt("category number (1-" + categories.size() + ")");
        if (choice < 1 || choice > categories.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        int categoryId = categories.get(choice - 1).getCategoryId();
        int paymentMethodId = Input.getInt("payment method ID");
        double amount = Input.getDouble("amount");

        Transaction transaction = new Transaction(categoryId, currentUserId, paymentMethodId,
                BigDecimal.valueOf(amount), TransactionType.expense, LocalDateTime.now());

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

        displayTransactionList(expenses);
        int choice = Input.getInt("expense number to edit (1-" + expenses.size() + ")");

        if (choice < 1 || choice > expenses.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Transaction expense = expenses.get(choice - 1);
        System.out.println("Current amount: " + expense.getAmount());
        double newAmount = Input.getDouble("new amount");
        expense.setAmount(BigDecimal.valueOf(newAmount));

        if (transactionController.updateTransaction(expense)) {
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

        displayTransactionList(expenses);
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
        displayTransactionList(expenses);
    }

    private void listExpensesByCategory() {
        System.out.println("=== Expenses by Category ===");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategory();
        printCategoryList(categories);

        int choice = Input.getInt("category number (1-" + categories.size() + ")");
        if (choice < 1 || choice > categories.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        int categoryId = categories.get(choice - 1).getCategoryId();
        List<Transaction> expenses = transactionController.getExpensesByCategory(currentUserId, categoryId);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found for this category.");
            return;
        }
        displayTransactionList(expenses);
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

            List<Transaction> allExpenses = transactionController.getExpensesByUserId(currentUserId);
            if (allExpenses == null || allExpenses.isEmpty()) {
                System.out.println("No expenses found.");
                return;
            }

            List<Transaction> filteredExpenses = allExpenses.stream()
                    .filter(t -> {
                        LocalDate transactionDate = t.getCreatedAt().toLocalDate();
                        return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
                    })
                    .toList();

            if (filteredExpenses.isEmpty()) {
                System.out.println("No expenses found in the specified date range.");
            } else {
                displayTransactionList(filteredExpenses);
            }
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
            List<Transaction> expenses = transactionController.getTransactionsByDate(currentUserId, date);
            if (expenses == null || expenses.isEmpty()) {
                System.out.println("No expenses found for this date.");
                return;
            }

            List<Transaction> expenseOnly = expenses.stream()
                    .filter(t -> t.getTransactionType() == TransactionType.expense)
                    .toList();

            if (expenseOnly.isEmpty()) {
                System.out.println("No expenses found for this date.");
            } else {
                displayTransactionList(expenseOnly);
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void listExpensesByMonthAndYear() {
        System.out.println("=== Expenses by Month and Year ===");
        int month = Input.getInt("month (1-12)");
        int year = Input.getInt("year");

        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter 1-12.");
            return;
        }

        List<Transaction> allExpenses = transactionController.getExpensesByUserId(currentUserId);
        if (allExpenses == null || allExpenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        List<Transaction> filteredExpenses = allExpenses.stream()
                .filter(t -> {
                    LocalDateTime createdAt = t.getCreatedAt();
                    return createdAt.getMonthValue() == month && createdAt.getYear() == year;
                })
                .toList();

        if (filteredExpenses.isEmpty()) {
            System.out.println("No expenses found for " + month + "/" + year + ".");
        } else {
            displayTransactionList(filteredExpenses);
        }
    }

    private void displayTransactionList(List<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-4s %-12s %-12s %-12s %-16s%n", "No.", "Amount", "Category", "Payment", "Date");
        System.out.println("=".repeat(80));

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            System.out.printf("%-4d $%-11.2f %-12d %-12d %-16s%n",
                    i + 1,
                    t.getAmount().doubleValue(),
                    t.getCategoryId(),
                    t.getPaymentMethodId(),
                    t.getCreatedAt().format(formatter));
        }
        System.out.println("=".repeat(80));
        System.out.println("Total expenses: " + transactions.size());

        BigDecimal total = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total amount: $" + total);
    }

    private void printCategoryList(List<Category> categories) {
        System.out.println("Category List:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + "." + categories.get(i).getName());
        }

    }
}
