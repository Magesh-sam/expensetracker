package controller;

import context.AppContext;
import java.sql.SQLException;
import java.util.List;
import model.dto.Category;
import service.CategoryService;

public class CategoryController {

    private static CategoryController categoryController;
    private static final CategoryService categoryService = AppContext.getCategoryService();

    private CategoryController() {
    }

    public static CategoryController getInstance() {
        if (categoryController == null) {
            categoryController = new CategoryController();
        }
        return categoryController;
    }

    public int createCategory(Category category) {
        try {
            return categoryService.createCategory(category);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Category getCategoryById(int categoryId) {
        try {
            return categoryService.getCategoryById(categoryId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getAllCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getIncomeCategories(int userId) {
        try {
            return categoryService.getIncomeCategories(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getExpenseCategories(int userId) {
        try {
            return categoryService.getExpenseCategories(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getIncomeCategoriesByUser(int userId) {
        try {
            return categoryService.getIncomeCategoriesByUser(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Category> getExpenseCategoriesByUser(int userId) {
        try {
            return categoryService.getExpenseCategoriesByUser(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateCategory(Category category) {
        try {
            return categoryService.updateCategory(category);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteCategory(int categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
