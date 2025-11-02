package view;

import context.AppContext;
import controller.AppUserController;
import model.dto.AppUser;
import model.dto.Credential;
import util.Input;

public class AppUserView {

    private static final AppUserController appUserController = AppContext.getAppUserController();

    public void displayMenu() {
        System.out.println("1. Register\n2. Login\n3. Exit");
        int choice = 0;
        while (choice < 1 || choice > 3) {
            choice = Input.getInt(" choice");
        }
        switch (choice) {
            case 1 -> {
                registerUser();
                System.out.println("User Created Successfully!");
            }
            case 2 -> {
                login();
                System.out.println("User logged in Successfully!");
            }
            case 3 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }
    }

    private void login() {
        String email = Input.getEmail();
        String password = Input.getPassword();
        appUserController.login(email, password);
    }

    private void registerUser() {
        String name = Input.getString("Name");
        String email = Input.getEmail();
        String password = Input.getPassword();
        String mobileNo = Input.getMobileNo();
        Credential c = new Credential(email, password);
        AppUser user = new AppUser(name, c, mobileNo);

        appUserController.registerUser(user);
    }
}
