package util;

import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        return p.matcher(email).matches();

    }

    public static boolean isValidMobileNo(String mobileNo) {
        if (mobileNo == null || mobileNo.trim().isEmpty()) {
            return false;
        }
        String mobileNoRegex = "^\\d{10}$";
        Pattern p = Pattern.compile(mobileNoRegex);
        return p.matcher(mobileNo).matches();
    }
}
