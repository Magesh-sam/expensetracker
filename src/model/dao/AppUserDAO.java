package model.dao;

import context.AppContext;
import interfaces.IAppUserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.dto.AppUser;
import model.dto.Credential;

public class AppUserDAO implements IAppUserDAO {

    private static final Connection conn = AppContext.getDBConnection();
    private static IAppUserDAO appUserDAO;

    private AppUserDAO() {

    }

    public static IAppUserDAO getInstance() {
        if (appUserDAO == null) {
            appUserDAO = new AppUserDAO();
        }
        return appUserDAO;
    }

    @Override
    public int registerUser(AppUser appuser) throws SQLException {
        String sql = "INSERT INTO app_user (name,email,password,mobile_number) VALUES (?,?,?,?) ";
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, appuser.getName());
            pstmt.setString(2, appuser.getLoginCredential().getEmail());
            pstmt.setString(3, appuser.getLoginCredential().getPassword());
            pstmt.setString(4, appuser.getMobileNumber());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("User Creation Failed. no ID obtained");
            }

        }
    }

    @Override
    public AppUser getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM app_user where user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }

            }

        }
        return null;
    }

    @Override
    public AppUser getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM app_user where LOWER(email) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }

            }

        }
        return null;
    }

    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM app_user where LOWER(email) = LOWER(?) AND LOWER(password) = LOWER(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;

            }

        }
    }

    // public List<AppUser> getAllUsers() throws SQLException {
    // String sql= "SELECT * FROM app_user";
    // List<AppUser> users = new ArrayList<>();
    // try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs =
    // pstmt.executeQuery()) {
    // while (rs.next()) {
    // users.add(map(rs));
    // }
    // }
    // return users;
    // }
    @Override
    public boolean updateUser(AppUser user) throws SQLException {
        String sql = "UPDATE app_user set name = ?, password = ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getLoginCredential().getPassword());
            pstmt.setInt(3, user.getUserId());
            return pstmt.executeUpdate() > 0;

        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean resetPassword(String mobileNo, String password) throws SQLException {
        String sql = "UPDATE app_user SET password = ? WHERE mobile_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, mobileNo);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean userExists(String email) throws SQLException {
        String sql = "SELECT * FROM app_user WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
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
        u.setMobileNumber(rs.getString("mobile_number"));
        return u;

    }

}
