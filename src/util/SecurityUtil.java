package util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {

    //to store the password in db
    public static String hashPassword(String password) {

        // gensalt's log_rounds parameter determines the complexity
        // the work factor is 2**log_rounds, and the default is 10
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    //checking the user password and db opassword same or not
    public static boolean verifyPassword(String candidate, String hashedPassword) {

        // Check that an unencrypted password matches one that has
        // previously been hashed
        return BCrypt.checkpw(candidate, hashedPassword);

    }
}
