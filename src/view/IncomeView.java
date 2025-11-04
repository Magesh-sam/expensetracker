package view;

import context.AppContext;
import controller.TransactionController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.dto.Category;
import model.dto.Transaction;
import model.dto.Transaction.TransactionType;
import model.dto.PaymentMethod;
import util.Input;

public class IncomeView {

    private static IncomeView incomeView;
    private final TransactionController transactionController = TransactionController.getInstance();
    private static int currentUserId = AppContext.getCurrentUser().getUserId();

    private IncomeView() {
    }

    public static IncomeView getInstance() {
        if (incomeView == null) {
            incomeView = new IncomeView();
        }
        return incomeView;
    }

    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println(
                    "1. Add Income\n2. Edit Income\n3. Delete Income\n4. List Income\n5. List Income by Category\n6. List Income by Date Range\n7. List Income by Date\n8. Go back");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 ->
                    addIncome();
                case 2 ->
                    editIncome();
                case 3 ->
                    deleteIncome();
                case 4 ->
                    listIncome();
                case 5 ->
                    listIncomeByCategory();
                case 6 ->
                    listIncomeByDateRange();
                case 7 ->
                    listIncomeByDate();
                case 8 -> {
                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addIncome() {
        System.out.println("=== Add Income ===");
        String incomeName = Input.getString("income title");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategories();
        printCategoryList(categories);

        int choice;
        while (true) {
            choice = Input.getInt("Payment method number (1-" + categories.size() + ")");
            if (choice >= 1 || choice <= categories.size()) {

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
            if (choice >= 1 || choice <= paymentMethods.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int paymentMethodId = paymentMethods.get(choice - 1).getPaymentMethodId();

        BigDecimal amount = Input.getBigDecimal("amount");

        Transaction transaction = new Transaction(categoryId, currentUserId, paymentMethodId,
                amount, TransactionType.income, LocalDateTime.now(), incomeName);

        int result = transactionController.createTransaction(transaction);
        if (result > 0) {
            System.out.println("Expense added successfully!");
        } else {
            System.out.println("Failed to add expense.");
        }
    }

    private void editIncome() {
        System.out.println("=== Edit Income ===");
        List<Transaction> incomes = transactionController.getIncomeByUserId(currentUserId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found.");
            return;
        }

        displayTransactionList(incomes);
        int choice = Input.getInt("income number to edit (1-" + incomes.size() + ")");

        if (choice < 1 || choice > incomes.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Transaction existingIncome = incomes.get(choice - 1);
        // reset
        choice = 0;
        System.out.println("press enter/0 to keep the values unchanged");
        System.out.println("Select Category");
        List<Category> categories = AppContext.getCategoryController().getAllCategories();
        printCategoryList(categories);

        while (true) {
            choice = Input.getInt("category number (1-" + categories.size() + ")");
            if (choice >= 0 || choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }

        int newCategoryId = choice == 0 ? existingIncome.getCategoryId() : categories.get(choice - 1).getCategoryId();
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

        int paymentMethodId = choice == 0 ? existingIncome.getPaymentMethodId()
                : paymentMethods.get(choice - 1).getPaymentMethodId();

        BigDecimal amount = Input.getBigDecimal("amount");
        amount = amount.doubleValue() == 0 ? existingIncome.getAmount() : amount;
        existingIncome.setCategoryId(newCategoryId);
        existingIncome.setPaymentMethodId(paymentMethodId);
        existingIncome.setAmount(amount);
        if (transactionController.updateTransaction(existingIncome)) {
            System.out.println("Income updated successfully!");
        } else {
            System.out.println("Failed to update income.");
        }
    }

    private void deleteIncome() {
        System.out.println("=== Delete Income ===");
        List<Transaction> incomes = transactionController.getIncomeByUserId(currentUserId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found.");
            return;
        }

        displayTransactionList(incomes);
        int choice = Input.getInt("income number to delete (1-" + incomes.size() + ")");

        if (choice < 1 || choice > incomes.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Transaction income = incomes.get(choice - 1);
        if (transactionController.deleteTransaction(income.getTransactionId(), currentUserId)) {
            System.out.println("income deleted successfully!");
        } else {
            System.out.println("Failed to delete income.");
        }
    }

    private void listIncome() {
        System.out.println("=== All Incomes ===");
        List<Transaction> incomes = transactionController.getIncomeByUserId(currentUserId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found.");
            return;
        }
        displayTransactionList(incomes);
    }

    private void listIncomeByCategory() {
        System.out.println("=== Incomes by Category ===");
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
        List<Transaction> incomes = transactionController.getIncomeByCategory(currentUserId, categoryId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found for this category.");
            return;
        }
        displayTransactionList(incomes);
    }

    private void listIncomeByDateRange() {
        System.out.println("=== Incomes by Date Range ===");
        System.out.println("Enter start date (YYYY-MM-DD):");
        String startDateStr = Input.getString("start date");
        System.out.println("Enter end date (YYYY-MM-DD):");
        String endDateStr = Input.getString("end date");

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            List<Transaction> incomeTransactionsByDateRange = transactionController.getIncomeTransactionsByDateRange(
                    currentUserId,
                    startDate, endDate);
            if (incomeTransactionsByDateRange == null || incomeTransactionsByDateRange.isEmpty()) {
                System.out.println("No income found.");
                return;
            }

            displayTransactionList(incomeTransactionsByDateRange);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void listIncomeByDate() {
        System.out.println("=== Income by Date ===");
        System.out.println("Enter date (YYYY-MM-DD):");
        String dateStr = Input.getString("date");

        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Transaction> incomes = transactionController.getIncomeByDate(currentUserId, date);
            if (incomes == null || incomes.isEmpty()) {
                System.out.println("No incomes found for this date.");
                return;
            }

            displayTransactionList(incomes);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void displayTransactionList(List<Transaction> transactions) {

        for (int i = 0; i < transactions.size(); i++) {
            System.out
                    .println((i + 1) + " | " + transactions.get(i).getName() + " | " + transactions.get(i).getAmount());
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
