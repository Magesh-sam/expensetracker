package service;

import context.AppContext;
import interfaces.ICategoryDAO;
import model.dto.Category;
import util.Validation;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
        validateCategory(category);
        return categoryDAO.createCategory(category);
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
        return categoryDAO.getCategoryById(categoryId);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategory();
    }

    public boolean updateCategory(Category category) throws SQLException {
        Objects.requireNonNull(category, "Category cannot be null");
        validateCategory(category);
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
        return categoryDAO.deleteCategory(categoryId);
    }

    private void validateCategory(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");
        if (!Validation.isNonEmpty(category.getName())) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid Category Id");

        }
    }
}
