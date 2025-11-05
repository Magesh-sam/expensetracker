package view;

import context.AppContext;
import controller.TransactionController;
import java.util.List;
import model.dto.AppUser;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import util.Input;
import util.Print;

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
            System.out.println(
                    "1. Manage Expenses\n2. Manage Incomes\n3. View Reports\n4. Update Profile\n 5. Delete User\n6. Exit");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 -> {
                    printStats();
                    manageExpenses();
                }
                case 2 -> {
                    printStats();
                    manageIncomes();
                }
                case 3 -> {
                    viewReports();
                }
                case 4 -> {
                    updateProfile();
                }
                case 5 -> {
                    deleteUser();
                }
                case 6 -> {
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
        System.out.println("===Expense Management===");
        AppContext.getExpenseView().displayMenu();
    }

    private void manageIncomes() {
        System.out.println("===Income Management===");
        AppContext.getIncomeView().displayMenu();

    }

    private void viewReports() {
        System.out.println("===Reports===");
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
        Print.displayTransactionList(expenses);
    }

    private void listTopIncomes() {
        List<Transaction> incomes = transactionController.getTopFiveIncomeByAmount(currentUserId);
        if (incomes == null || incomes.isEmpty()) {
            System.out.println("No incomes found.");
            return;
        }
        System.out.println("===Top Incomes===");
        Print.displayTransactionList(incomes);
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

    private void printStats() {
        int totalIncome = transactionController.getTotalIncome(currentUserId);
        int totalExpense = transactionController.getTotalExpense(currentUserId);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Remaining: " + (totalIncome - totalExpense));

    }

    private void updateProfile() {
        AppUser user = AppContext.getCurrentUser();

        System.out.println("===Update Profile===");
        System.out.println("Name: " + user.getName());
        System.out.println("Mobile Number: " + user.getMobileNumber());
        System.out.println("Email: " + user.getLoginCredential().getEmail());
        System.out.println("Email/Mobile Number Cannot be changed");
        String name = Input.getString("Name");

        String password = Input.getPassword();
        user.setName(name);
        user.getLoginCredential().setPassword(password);
        AppContext.getAppUserController().updateUser(user);
        System.out.println("Profile Updated Successfully!");
    }

    private void deleteUser() {
        System.out.println("===Delete User===");
        System.out.println("Account will be deleted permanently");
        System.out.println("Are you sure you want to delete your account? (y/n)");
        String choice = Input.getString("choice");
        if (!choice.equalsIgnoreCase("y")) {
            return;
        }
        int userId = AppContext.getCurrentUser().getUserId();
        AppContext.getAppUserController().deleteUser(userId);
        System.out.println("User Deleted Successfully!");
        AppContext.setCurrentUser(null);
        AppContext.getAppUserView().displayMenu();
    }

}
