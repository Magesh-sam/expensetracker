package service;

import context.AppContext;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import java.util.Objects;
import model.dao.AppUserDAO;
import model.dto.AppUser;
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

    public int registerAppUser(AppUser appUser) {
        Objects.requireNonNull(appUser, "AppUser cannot be null");
        validateAppUser(appUser);
        return 0;
    }

    private void validateAppUser(AppUser appUser) {
        if (!Validation.isNonEmpty(appUser.getName())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!Validation.isValidEmail(appUser.getLoginCredential().getEmail())) {
            throw new InvalidEmailException("Email is not valid");
        }
        if (!Validation.isValidPassword(appUser.getLoginCredential().getPassword())) {
            throw new InvalidPasswordException("Password is not valid");
        }

    }

}
