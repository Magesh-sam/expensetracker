package service;

import context.AppContext;
import exceptions.InvalidEmailException;
import exceptions.InvalidMobileNumberException;
import exceptions.InvalidPasswordException;
import java.sql.SQLException;
import java.util.Objects;
import model.dao.AppUserDAO;
import model.dto.AppUser;
import util.SecurityUtil;
import util.Validation;

public class AppUserService {

    private static final AppUserDAO appUserDAO = AppContext.getAppUserDAO();
    private static AppUserService appUserService;

    private AppUserService() {

    }

    public static AppUserService getInstance() {
        if (appUserService == null) {
            appUserService = new AppUserService();
        }
        return appUserService;
    }

    public int registerUser(AppUser appUser) throws SQLException, InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException {

        Objects.requireNonNull(appUser, "AppUser cannot be null");
        validateAppUser(appUser);
        String hashedPassword = SecurityUtil.hashPassword(appUser.getLoginCredential().getPassword());
        appUser.getLoginCredential().setPassword(hashedPassword);
        boolean userExists = appUserDAO.userExists(appUser.getLoginCredential().getEmail());
        if (userExists) {
            throw new IllegalArgumentException("User already exists");
        }
        return appUserDAO.registerUser(appUser);
    }

    public AppUser loginUser(String email, String password) throws SQLException, InvalidEmailException, InvalidPasswordException {
        if (!Validation.isValidEmail(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidPassword(password)) {
            throw new InvalidPasswordException("Password is not valid");
        }
        password = SecurityUtil.hashPassword(password);
        return appUserDAO.getUserByEmailAndPassword(email, password);
    }

    public boolean updateUser(AppUser appUser) throws SQLException, InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException {
        Objects.requireNonNull(appUser, "AppUser cannot be null");
        validateAppUser(appUser);
        return appUserDAO.updateUser(appUser);
    }

    public boolean deleteUser(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }
        return appUserDAO.deleteUser(userId);
    }

    public boolean resetPassword(String mobileNo, String email, String password) throws SQLException, InvalidPasswordException, InvalidEmailException, InvalidMobileNumberException {
        if (!Validation.isValidMobileNo(mobileNo)) {
            throw new InvalidMobileNumberException("Mobile number is not valid");
        }
        if (!Validation.isValidEmail(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidPassword(password)) {
            throw new InvalidPasswordException("Password is not valid");
        }
        AppUser existingUser = appUserDAO.getUserByEmail(email);
        if (existingUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (!existingUser.getMobileNumber().equals(mobileNo)) {
            throw new IllegalArgumentException("Mobile number does not match");
        }
        return appUserDAO.resetPassword(mobileNo, password);
    }

    private void validateAppUser(AppUser appUser) throws InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException {
        if (!Validation.isNonEmpty(appUser.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (!Validation.isValidEmail(appUser.getLoginCredential().getEmail())) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidPassword(appUser.getLoginCredential().getPassword())) {
            throw new InvalidPasswordException("Password is not valid");
        }
        if (!Validation.isValidMobileNo(appUser.getMobileNumber())) {
            throw new InvalidMobileNumberException("Mobile number is not valid");
        }

    }

}
