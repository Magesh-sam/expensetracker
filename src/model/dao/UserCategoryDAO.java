package model.dao;

import context.AppContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCategoryDAO {

    private static final Connection conn = AppContext.getDBConnection();
    private static UserCategoryDAO userCategoryDAO;

    private UserCategoryDAO() {
    }

    public static UserCategoryDAO getInstance() {
        if (userCategoryDAO == null) {
            userCategoryDAO = new UserCategoryDAO();
        }
        return userCategoryDAO;
    }

    public int addCategoryToUser(int userId, int categoryId) throws SQLException {
        String sql = "INSERT INTO user_category (user_id, category_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Category addition failed. No ID obtained");
            }
        }
    }

    public void addDefaultCategoriesToUser(int userId) throws SQLException {
        String sql = "INSERT INTO user_category (app_user_id, category_id) SELECT ?, id FROM category ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Category added: " + rs.getInt(1));
                }
            }
        }
    }

    public boolean removeCategoryFromUser(int userId, int categoryId) throws SQLException {
        String sql = "DELETE FROM user_category WHERE user_id = ? AND category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateCategoryForUser(int userId, int oldCategoryId, int newCategoryId) throws SQLException {
        String sql = "UPDATE user_category SET category_id = ? WHERE user_id = ? AND category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newCategoryId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, oldCategoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

}
