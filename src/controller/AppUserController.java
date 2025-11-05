package controller;

import context.AppContext;
import exceptions.InvalidEmailException;
import exceptions.InvalidMobileNumberException;
import exceptions.InvalidPasswordException;
import exceptions.MobileNumberMismatchException;
import exceptions.PasswordMismatchException;
import exceptions.UserNotFoundException;
import java.sql.SQLException;
import model.dto.AppUser;
import service.AppUserService;

public class AppUserController {

    private static AppUserController appUserController;
    private static final AppUserService appUserService = AppContext.getAppUserService();

    private AppUserController() {
    }

    public static AppUserController getInstance() {
        if (appUserController == null) {
            appUserController = new AppUserController();
        }
        return appUserController;
    }

    public int registerUser(AppUser user) {
        try {
            return appUserService.registerUser(user);
        } catch (InvalidEmailException | InvalidPasswordException | InvalidMobileNumberException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public AppUser login(String email, String password) {
        try {
            return appUserService.loginUser(email, password);
        } catch (SQLException | InvalidEmailException | InvalidPasswordException | PasswordMismatchException
                | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateUser(AppUser appUser) {
        try {
            return appUserService.updateUser(appUser);
        } catch (SQLException | InvalidEmailException | InvalidPasswordException | InvalidMobileNumberException
                | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        try {
            return appUserService.deleteUser(userId);
        } catch (SQLException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean resetPassword(String mobileNo, String email, String password) {
        try {
            return appUserService.resetPassword(mobileNo, email, password);
        } catch (SQLException | InvalidPasswordException | InvalidEmailException | InvalidMobileNumberException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean checkEmailAndMobileNoMatch(String email, String mobileNo) {
        try {
            return appUserService.checkEmailAndMobileNoMatch(email, mobileNo);
        } catch (SQLException | InvalidEmailException | InvalidMobileNumberException
                | MobileNumberMismatchException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public AppUser getUserByMobileNumber(String mobileNo) {
        try {
            return appUserService.getUserByMobileNumber(mobileNo);
        } catch (SQLException | InvalidMobileNumberException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public AppUser getUserByEmail(String email) {
        try {
            return appUserService.getUserByEmail(email);
        } catch (SQLException | InvalidEmailException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
