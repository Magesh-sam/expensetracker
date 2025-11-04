package interfaces;

import java.sql.SQLException;

import model.dto.AppUser;

public interface IAppUserDAO {

    int registerUser(AppUser appuser) throws SQLException;

    AppUser getUserById(int userId) throws SQLException;

    AppUser getUserByEmail(String email) throws SQLException;

    AppUser getUserByEmailAndPassword(String email, String password) throws SQLException;

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
    boolean updateUser(AppUser user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;

    boolean resetPassword(String mobileNo, String password) throws SQLException;

    boolean userExists(String email) throws SQLException;

}