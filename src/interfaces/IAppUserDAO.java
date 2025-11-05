package interfaces;

import java.sql.SQLException;
import model.dto.AppUser;

public interface IAppUserDAO {

    int registerUser(AppUser appuser) throws SQLException;

    AppUser getUserById(int userId) throws SQLException;

    AppUser getUserByEmail(String email) throws SQLException;

    AppUser getUserByEmailAndPassword(String email, String password) throws SQLException;

    boolean updateUser(AppUser user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;

    boolean resetPassword(String mobileNo, String password) throws SQLException;

    boolean userExists(String email) throws SQLException;

    AppUser getUserByMobileNumber(String mobileNo) throws SQLException;

    boolean changePassword(int userId, String newPassword) throws SQLException;

}