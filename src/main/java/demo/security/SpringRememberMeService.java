package demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

public class SpringRememberMeService extends TokenBasedRememberMeServices {

    public static final int TOKEN_VALIDITY_SECONDS = 10 * 1; // 1 minutes

    public SpringRememberMeService(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected String extractRememberMeCookie(HttpServletRequest request) {
        String rememberMe = request.getHeader("remember-me");
        if (rememberMe == null) return null;
        int startIndex = "remember-me=".length();
        int endIndex = rememberMe.indexOf("; ", startIndex);
        return rememberMe.substring(startIndex, endIndex);
    }

    @Override
    protected int calculateLoginLifetime(HttpServletRequest request, Authentication authentication) {
        return TOKEN_VALIDITY_SECONDS;
    }
}
