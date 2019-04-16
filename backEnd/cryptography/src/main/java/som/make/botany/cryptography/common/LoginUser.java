package som.make.botany.cryptography.common;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUser {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
