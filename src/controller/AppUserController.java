package controller;

import context.AppContext;
import exceptions.InvalidEmailException;
import exceptions.InvalidMobileNumberException;
import exceptions.InvalidPasswordException;
import java.sql.SQLException;
import model.dto.AppUser;
import service.AppUserService;

public class AppUserController {

    private static AppUserController appUserController;
    private static final AppUserService appUserService = AppContext.getAppUserService();

    private AppUserController() {
        // Private constructor for singleton
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
        } catch (SQLException | InvalidEmailException | InvalidPasswordException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
