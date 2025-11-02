package util;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    private Validation() {
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        return EMAIL_PATTERN.matcher(email).matches();

    }

    public static boolean isValidMobileNo(String mobileNo) {
        if (mobileNo == null || mobileNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number cannot be empty");
        }
        return MOBILE_PATTERN.matcher(mobileNo).matches();
    }

    public static boolean isValidPassword(String password) {
        if (!isNonEmpty(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static void requireValidEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }
    }

    public static void requireValidMobileNo(String mobileNo) throws IllegalArgumentException {
        if (!isValidMobileNo(mobileNo)) {
            throw new IllegalArgumentException("Mobile number is not valid");
        }
    }

    public static void requireValidPassword(String password) throws IllegalArgumentException {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password is not valid");
        }
    }
}
