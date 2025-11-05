package service;

import context.AppContext;
import exceptions.InvalidEmailException;
import exceptions.InvalidMobileNumberException;
import exceptions.InvalidPasswordException;
import exceptions.MobileNumberMismatchException;
import exceptions.PasswordMismatchException;
import exceptions.UserNotFoundException;
import interfaces.IAppUserDAO;
import java.sql.SQLException;
import java.util.Objects;
import model.dto.AppUser;
import util.SecurityUtil;
import util.Validation;

public class AppUserService {

    private static final IAppUserDAO appUserDAO = AppContext.getAppUserDAO();
    private static AppUserService appUserService;

    private AppUserService() {

    }

    public static AppUserService getInstance() {
        if (appUserService == null) {
            appUserService = new AppUserService();
        }
        return appUserService;
    }

    public int registerUser(AppUser appUser)
            throws SQLException, InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException {

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

    public AppUser loginUser(String email, String password)
            throws SQLException, InvalidEmailException, InvalidPasswordException, PasswordMismatchException,
            UserNotFoundException {
        if (!Validation.isValidEmail(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidPassword(password)) {
            throw new InvalidPasswordException("Password is not valid");
        }
        AppUser user = appUserDAO.getUserByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found for email: " + email);
        }

        if (SecurityUtil.verifyPassword(password, user.getLoginCredential().getPassword())) {
            return user;
        } else {
            throw new PasswordMismatchException("Password did not match");
        }
    }

    public boolean updateUser(AppUser appUser)
            throws SQLException, InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException,
            UserNotFoundException {
        Objects.requireNonNull(appUser, "AppUser cannot be null");
        validateAppUser(appUser);
        AppUser existing = appUserDAO.getUserById(appUser.getUserId());
        if (existing == null) {
            throw new UserNotFoundException("User Not Found with user id: " + appUser.getUserId());
        }
        String hashedPassword = SecurityUtil.hashPassword(appUser.getLoginCredential().getPassword());
        appUser.getLoginCredential().setPassword(hashedPassword);
        return appUserDAO.updateUser(appUser);
    }

    public boolean deleteUser(int userId) throws SQLException, UserNotFoundException {
        Validation.validateId("User", userId);
        AppUser existing = appUserDAO.getUserById(userId);
        if (existing == null) {
            throw new UserNotFoundException("User Not Found with user id: " + userId);
        }
        return appUserDAO.deleteUser(userId);
    }

    public boolean resetPassword(String mobileNo, String email, String password)
            throws SQLException, InvalidPasswordException, InvalidEmailException, InvalidMobileNumberException {
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
        String hashedPassword = SecurityUtil.hashPassword(password);
        return appUserDAO.resetPassword(mobileNo, hashedPassword);
    }

    public boolean checkEmailAndMobileNoMatch(String email, String mobileNo)
            throws SQLException, InvalidEmailException, InvalidMobileNumberException, MobileNumberMismatchException {
        if (!Validation.isValidEmail(email)) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidMobileNo(mobileNo)) {
            throw new InvalidMobileNumberException("Mobile number is not valid");
        }
        AppUser existingUser = appUserDAO.getUserByEmail(email);
        if (existingUser == null) {
            throw new MobileNumberMismatchException("Email and mobile number does not match");
        }
        return existingUser.getMobileNumber().equals(mobileNo);
    }

    public AppUser getUserBYMobileNumber(String mobileNo) throws SQLException, InvalidMobileNumberException {

        if (!Validation.isValidMobileNo(mobileNo)) {
            throw new InvalidMobileNumberException("Mobile number is not valid");
        }
        return appUserDAO.getUserByMobileNumber(mobileNo);
    }

    private void validateAppUser(AppUser appUser)
            throws InvalidEmailException, InvalidPasswordException, InvalidMobileNumberException {
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
