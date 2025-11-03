package view;

import util.Input;

public class FunctionalView {

    private static FunctionalView functionalView;

    private FunctionalView() {
    }

    public static FunctionalView getInstance() {
        if (functionalView == null) {
            functionalView = new FunctionalView();
        }
        return functionalView;
    }

    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println("1. Manage Expenses\n2. Manage Incomes\n3. View Report\n4. Logout");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 -> {
                    manageExpenses();
                }
                case 2 -> {
                    manageIncomes();
                }
                case 3 -> {
                    viewReport();
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
    }

    private void manageIncomes() {
        System.out.println("Manage Incomes");

    }

    private void viewReport() {
        System.out.println("View Report");
    }
}
