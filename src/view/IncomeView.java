package view;

import util.Input;

public class IncomeView {

    private static IncomeView incomeView;

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
            System.out.println("1. Add Income\n2. Edit Income\n3. Delete Income\n4. List Incomes\n5. List Incomes by Category\n6. List Incomes by Date Range\n7. List Incomes by Date\n8. List Incomes by Month and Year\n9. Go back");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 ->
                    addIncome();
                case 2 ->
                    editIncome();
                case 3 ->
                    deleteIncome();
                case 4 ->
                    listIncomes();
                case 5 ->
                    listIncomesByCategory();
                case 6 ->
                    listIncomesByDateRange();
                case 7 ->
                    listIncomesByDate();
                case 8 ->
                    listIncomesByMonthAndYear();
                case 9 -> {
                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addIncome() {
        System.out.println("Add Income - Not implemented yet");
    }

    private void editIncome() {
        System.out.println("Edit Income - Not implemented yet");
    }

    private void deleteIncome() {
        System.out.println("Delete Income - Not implemented yet");
    }

    private void listIncomes() {
        System.out.println("List Incomes - Not implemented yet");
    }

    private void listIncomesByCategory() {
        System.out.println("List Incomes by Category - Not implemented yet");
    }

    private void listIncomesByDateRange() {
        System.out.println("List Incomes by Date Range - Not implemented yet");
    }

    private void listIncomesByDate() {
        System.out.println("List Incomes by Date - Not implemented yet");
    }

    private void listIncomesByMonthAndYear() {
        System.out.println("List Incomes by Month and Year - Not implemented yet");
    }
}
