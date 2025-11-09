package view;

import context.AppContext;
import controller.CategoryController;
import interfaces.IView;
import java.util.List;
import model.dto.Category;
import model.dto.Transaction;
import util.Input;
import util.Print;

public class CategoryView implements IView {

    private static CategoryView categoryView;
    private static final CategoryController categoryController = AppContext.getCategoryController();
    private static final int currentUserId = AppContext.getCurrentUser().getUserId();

    public static CategoryView getInstance() {
        if (categoryView == null) {
            categoryView = new CategoryView();
        }
        return categoryView;
    }

    @Override
    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println(
                    "1. Add Expense Category\n2. Edit Expense Category\n3. Delete Expense Category\n4. Add Income Category\n5. Edit Income Category\n6. Delete Income Category\n7. Go back");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 ->
                    addExpenseCategory();
                case 2 ->
                    editExpenseCategory();
                case 3 ->
                    deleteExpenseCategory();

                case 4 ->
                    addIncomeCategory();
                case 5 ->
                    editIncomeCategory();
                case 6 ->
                    deleteIncomeCategory();
                case 7 -> {
                    return;
                }
                default ->
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addExpenseCategory() {
        String categoryName = Input.getString("category name");
        Category c = new Category(currentUserId, categoryName, Transaction.TransactionType.expense);
        int result = categoryController.createCategory(c);
        if (result > 0) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category.");
        }

    }

    private void editExpenseCategory() {
        List<Category> categories = categoryController.getExpenseCategoriesByUser(currentUserId);
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        Print.printCategoryList(categories);
        int choice;
        while (true) {
            choice = Input.getInt("Category number (1-" + categories.size() + ")");
            if (choice >= 1 && choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }
        Category c = categories.get(choice - 1);
        System.out.println("press k to keep the values unchanged");
        String categoryName = Input.getString("category name");
        categoryName = categoryName.equalsIgnoreCase("k") ? c.getName() : categoryName;
        c.setName(categoryName);

        if (categoryController.updateCategory(c)) {
            System.out.println("Category updated successfully!");
        } else {
            System.out.println("Failed to update category.");
        }

    }

    private void deleteExpenseCategory() {
        List<Category> categories = categoryController.getExpenseCategoriesByUser(currentUserId);
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        Print.printCategoryList(categories);
        int choice;
        while (true) {
            choice = Input.getInt("Category number (1-" + categories.size() + ")");
            if (choice >= 1 && choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }
        Category c = categories.get(choice - 1);
        if (categoryController.deleteCategory(c.getCategoryId())) {
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Failed to delete category.");
        }

    }

    private void addIncomeCategory() {
        String categoryName = Input.getString("category name");
        Category c = new Category(currentUserId, categoryName, Transaction.TransactionType.income);
        int result = categoryController.createCategory(c);
        if (result > 0) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category.");
        }

    }

    private void editIncomeCategory() {
        List<Category> categories = categoryController.getIncomeCategoriesByUser(currentUserId);
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        Print.printCategoryList(categories);
        int choice;
        while (true) {
            choice = Input.getInt("Category number (1-" + categories.size() + ")");
            if (choice >= 1 && choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }
        Category c = categories.get(choice - 1);
        System.out.println("press k to keep the values unchanged");
        String categoryName = Input.getString("category name");
        categoryName = categoryName.equalsIgnoreCase("k") ? c.getName() : categoryName;
        c.setName(categoryName);

        if (categoryController.updateCategory(c)) {
            System.out.println("Category updated successfully!");
        } else {
            System.out.println("Failed to update category.");
        }

    }

    private void deleteIncomeCategory() {
        List<Category> categories = categoryController.getIncomeCategoriesByUser(currentUserId);
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        Print.printCategoryList(categories);
        int choice;
        while (true) {
            choice = Input.getInt("Category number (1-" + categories.size() + ")");
            if (choice >= 1 && choice <= categories.size()) {

                break;
            }

            System.out.println("Invalid selection.");
        }
        Category c = categories.get(choice - 1);
        if (categoryController.deleteCategory(c.getCategoryId())) {
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Failed to delete category.");
        }

    }

}
