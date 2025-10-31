package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import context.AppContext;
import model.pojo.Category;

public class CategoryDAO {
    private static CategoryDAO categoryDAO;
    private static Connection conn = AppContext.getDBConnection();

    private CategoryDAO() {
    }

    public static CategoryDAO getInstance() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        return categoryDAO;

    }

    public int createCategory(Category category) throws SQLException {
        String SQL = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
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

    public Category getCategoryById(int categoryId) throws SQLException {
        String SQL = "SELECT * FROM category where category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public Category getAllCategory() throws SQLException {
        String SQL = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(map(rs));
                }
            }
        }
        return null;
    }

    public boolean updateCategory(Category category) throws SQLException {
        String SQL = "UPDATE category SET name = ? WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getCategoryId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        String SQL = "DELETE FROM category  WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Category map(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt("category_id"));
        c.setName(rs.getString("name"));
        return c;

    }
}
