package view;

import context.AppContext;
import controller.TransactionController;
import java.util.List;
import model.dto.AppUser;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import util.Input;
import util.Print;
import util.SecurityUtil;
import util.Validation;

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
        System.out.println("=== Update Profile ===");
        System.out.println("Current Name: " + user.getName());
        System.out.println("Current Mobile Number: " + user.getMobileNumber());
        System.out.println("Current Email: " + user.getLoginCredential().getEmail());
        System.out.println("(Press 'k' to keep the current value)");

        String name = Input.getString("Name");
        String email = Input.getString("Email");
        String password = Input.getString("Password");
        String mobileNo = Input.getString("Mobile Number");

        name = name.equalsIgnoreCase("k") ? user.getName() : name;
        email = email.equalsIgnoreCase("k") ? user.getLoginCredential().getEmail() : email;
        password = password.equalsIgnoreCase("k") ? user.getLoginCredential().getPassword() : password;
        mobileNo = mobileNo.equalsIgnoreCase("k") ? user.getMobileNumber() : mobileNo;

        if (!Validation.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        if (!Validation.isValidMobileNo(mobileNo)) {
            System.out.println("Invalid mobile number.");
            return;
        }

        if (!Validation.isValidPassword(password)) {
            System.out.println("Invalid  password.");
            return;
        }

        AppUser existingByEmail = AppContext.getAppUserController().getUserByEmail(email);
        if (existingByEmail != null && existingByEmail.getUserId() != user.getUserId()) {
            System.out.println("User already exists with the given email ID.");
            return;
        }

        AppUser existingByMobileNo = AppContext.getAppUserController().getUserByMobileNumber(mobileNo);
        if (existingByMobileNo != null && existingByMobileNo.getUserId() != user.getUserId()) {
            System.out.println("User already exists with the given mobile number.");
            return;
        }

        user.setName(name);
        user.getLoginCredential().setEmail(email);
        user.getLoginCredential().setPassword(SecurityUtil.hashPassword(password));
        user.setMobileNumber(mobileNo);

        if (AppContext.getAppUserController().updateUser(user)) {

            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Failed to Update. Please try again");
        }
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
        if (AppContext.getAppUserController().deleteUser(userId)) {

            System.out.println("User Deleted Successfully!");
            AppContext.setCurrentUser(null);
            AppContext.getAppUserView().displayMenu();
        } else {
            System.out.println("Failed to DElete. Please try again");

        }
    }

}
