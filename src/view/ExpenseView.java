package view;

import util.Input;

public class ExpenseView {

    private static ExpenseView expenseView;

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
                    System.out.println("Exiting...");
                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addExpense() {
        System.out.println("Add Expense - Not implemented yet");
    }

    private void editExpense() {
        System.out.println("Edit Expense - Not implemented yet");
    }

    private void deleteExpense() {
        System.out.println("Delete Expense - Not implemented yet");
    }

    private void listExpenses() {
        System.out.println("List Expenses - Not implemented yet");
    }

    private void listExpensesByCategory() {
        System.out.println("List Expenses by Category - Not implemented yet");
    }

    private void listExpensesByDateRange() {
        System.out.println("List Expenses by Date Range - Not implemented yet");
    }

    private void listExpensesByDate() {
        System.out.println("List Expenses by Date - Not implemented yet");
    }

    private void listExpensesByMonthAndYear() {
        System.out.println("List Expenses by Month and Year - Not implemented yet");
    }
}
