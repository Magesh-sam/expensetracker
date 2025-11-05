package view;

import context.AppContext;
import controller.AppUserController;
import model.dto.AppUser;
import model.dto.Credential;
import util.Input;

public class AppUserView {

    private static final AppUserController appUserController = AppContext.getAppUserController();
    private static AppUserView appUserView;

    public static AppUserView getInstance() {
        if (appUserView == null) {
            appUserView = new AppUserView();
        }
        return appUserView;
    }

    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Reset Password\n4. Exit");
            choice = Input.getInt("choice");
            switch (choice) {
                case 1 -> {
                    registerUser();
                }
                case 2 -> {
                    login();
                }
                case 3 -> {
                    resetPassword();
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private void login() {
        String email = Input.getEmail();
        String password = Input.getPassword();
        AppUser user = appUserController.login(email, password);
        if (user != null) {

            if (AppContext.getCurrentUser() == null) {

                AppContext.setCurrentUser(user);
                System.out.println("Welcome, " + user.getName());
                AppContext.getFunctionalView().displayMenu();
            }
        }

    }

    private void registerUser() {
        String name = Input.getString("Name");
        String email = Input.getEmail();
        String password = Input.getPassword();
        String mobileNo = Input.getMobileNo();
        Credential c = new Credential(email, password);
        AppUser user = new AppUser(name, c, mobileNo);

        int userId = appUserController.registerUser(user);
        if (userId > 0) {

            System.out.println("User Created Successfully!");
        } else {

            System.out.println("Something went wrong. User Registration Failed!");
        }

    }

    private void resetPassword() {
        String email = Input.getEmail();
        String mobileNo = Input.getMobileNo();
        if (!appUserController.checkEmailAndMobileNoMatch(email, mobileNo)) {
            System.out.println("Email and mobile number does not match");
            return;
        }
        String password = Input.getPassword();
        if (appUserController.resetPassword(mobileNo, email, password)) {
            System.out.println("Password reset Successfully!");
        } else {

            System.out.println("Something went wrong. Password Reset Failed!");
        }
    }
}
