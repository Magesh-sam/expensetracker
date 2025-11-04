package controller;

import context.AppContext;
import model.dto.Category;
import service.CategoryService;

import java.sql.SQLException;
import java.util.List;

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
