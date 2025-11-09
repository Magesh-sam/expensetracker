package service;

import context.AppContext;
import interfaces.ICategoryDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import model.dto.Category;
import util.Validation;

public class CategoryService {

    private static final ICategoryDAO categoryDAO = AppContext.getCategoryDAO();
    private static CategoryService categoryService;

    private CategoryService() {
    }

    public static CategoryService getInstance() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    public int createCategory(Category category) throws SQLException {
        Objects.requireNonNull(category, "Category cannot be null");
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty or null");
        }
        if (categoryDAO.categoryExists(category.getName())) {
            throw new IllegalArgumentException("Category Already Exists");
        }
        return categoryDAO.createCategory(category);
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        Validation.validateId("Category", categoryId);

        return categoryDAO.getCategoryById(categoryId);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategory();
    }

    public List<Category> getIncomeCategories(int userId) throws SQLException {
        Validation.validateId("User", userId);
        return categoryDAO.getIncomeCategories(userId);
    }

    public List<Category> getExpenseCategories(int userId) throws SQLException {
        Validation.validateId("User", userId);
        return categoryDAO.getExpenseCategories(userId);
    }

    public List<Category> getIncomeCategoriesByUser(int userId) throws SQLException {
        Validation.validateId("User", userId);
        return categoryDAO.getIncomeCategoriesByUser(userId);
    }

    public List<Category> getExpenseCategoriesByUser(int userId) throws SQLException {
        Validation.validateId("User", userId);
        return categoryDAO.getExpenseCategoriesByUser(userId);
    }

    public boolean updateCategory(Category category) throws SQLException {
        Objects.requireNonNull(category, "Category cannot be null");
        validateCategory(category);
        Category exsisting = categoryDAO.getCategoryById(category.getCategoryId());
        if (exsisting == null) {
            throw new IllegalArgumentException("Update Failed. Category does not exist");
        }

        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        Validation.validateId("Category", categoryId);

        Category exsisting = categoryDAO.getCategoryById(categoryId);
        if (exsisting == null) {
            throw new IllegalArgumentException("Delete Failed. Category does not exist");
        }
        return categoryDAO.deleteCategory(categoryId);
    }

    private void validateCategory(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");
        if (!Validation.isNonEmpty(category.getName())) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        Validation.validateId("Category", category.getCategoryId());
    }

}
