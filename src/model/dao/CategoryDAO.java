package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import context.AppContext;
import interfaces.ICategoryDAO;
import model.dto.Category;

public class CategoryDAO implements ICategoryDAO {

    private static ICategoryDAO categoryDAO;
    private static final Connection conn = AppContext.getDBConnection();

    private CategoryDAO() {
    }

    public static ICategoryDAO getInstance() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        return categoryDAO;

    }

    @Override
    public int createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getName());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Category creation failed. No ID obtained");
                }
            }

        }
    }

    @Override
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM category where category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategory() throws SQLException {
        String sql = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(map(rs));
                }
            }
        }
        return categories;
    }

    @Override
    public boolean categoryExists(String category) throws SQLException {
        String sql = "  SELECT * FROM category where lower(name) = lower(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE category SET name = ? WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getCategoryId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM category  WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Category map(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt(1));
        c.setName(rs.getString("name"));
        return c;

    }
}
