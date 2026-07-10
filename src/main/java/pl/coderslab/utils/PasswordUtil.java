package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.entity.User;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String candidate, User user) {
        boolean matches = BCrypt.checkpw(candidate, user.getPassword());

        if (matches) {
            System.out.println("OK");
        } else {
            System.out.println("It does not match");
        }

        return matches;
    }

}
