package model.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import context.AppContext;
import model.dto.AppUser;
import model.dto.Credential;

public class AppUserDAO {

    private static final Connection conn = AppContext.getDBConnection();
    private static AppUserDAO appUserDAO;

    private AppUserDAO() {

    }

    public static AppUserDAO getInstance() {
        if (appUserDAO == null) {
            appUserDAO = new AppUserDAO();
        }
        return appUserDAO;
    }

    public int registerUser(AppUser appuser) throws SQLException {
        String SQL = "INSERT INTO app_user (name,email,password) VALUES (?,?,?)";
        try (
                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, appuser.getName());
            pstmt.setString(2, appuser.getLoginCredential().getEmail());
            pstmt.setString(3, appuser.getLoginCredential().getPassword());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("User Creation Failed. no ID obtained");
            }

        }
    }

    public AppUser getUserById(int userId) throws SQLException {
        String SQL = "SELECT * FROM app_user where user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL);) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }

            }

        }
        return null;
    }

    public AppUser getUserByEmail(String email) throws SQLException {
        String SQL = "SELECT * FROM app_user where LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL);) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }

            }

        }
        return null;
    }

    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        String SQL = "SELECT * FROM app_user where LOWER(email) = LOWER(?) AND LOWER(password) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL);) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }

            }

        }
        return null;
    }

    // public List<AppUser> getAllUsers() throws SQLException {
    // String SQL = "SELECT * FROM app_user";
    // List<AppUser> users = new ArrayList<>();
    // try (PreparedStatement pstmt = conn.prepareStatement(SQL); ResultSet rs =
    // pstmt.executeQuery()) {
    // while (rs.next()) {
    // users.add(map(rs));
    // }
    // }
    // return users;
    // }
    public boolean updateUser(AppUser user) throws SQLException {
        String SQL = "UPDATE app_user set name = ?, email = ?, password = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLoginCredential().getEmail());
            pstmt.setString(3, user.getLoginCredential().getPassword());
            pstmt.setInt(4, user.getUserId());
            return pstmt.executeUpdate() > 0;

        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        String SQL = "DELETE FROM app_user WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private AppUser map(ResultSet rs) throws SQLException {
        AppUser u = new AppUser();
        u.setUserId(rs.getInt(1));
        u.setName(rs.getString("name"));
        Credential c = new Credential();
        c.setEmail(rs.getString("email"));
        c.setPassword(rs.getString("password"));
        u.setLoginCredential(c);
        return u;

    }

}
