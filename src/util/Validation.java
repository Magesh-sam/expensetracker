package util;

import java.util.regex.Pattern;

public class Validation {

    private Validation() {
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return p.matcher(email).matches();

    }

    public static boolean isValidMobileNo(String mobileNo) {
        if (mobileNo == null || mobileNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number cannot be empty");
        }
        String mobileNoRegex = "^\\d{10}$";
        Pattern p = Pattern.compile(mobileNoRegex);
        return p.matcher(mobileNo).matches();
    }

    public static boolean isValidPassword(String password) {
        if (!isNonEmpty(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern p = Pattern.compile(passwordRegex);
        return p.matcher(password).matches();
    }
}
