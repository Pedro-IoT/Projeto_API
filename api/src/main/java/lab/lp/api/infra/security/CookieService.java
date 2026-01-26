package lab.lp.api.infra.security;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseCookie;

@Service
public class CookieService {
    private static final String COOKIE_NAME = "token";
    private static final int EXPIRATION_TIME = 2 * 60 * 60; // 2 hours in seconds

    public ResponseCookie generateTokenCookie (String token) {
        return ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .path("/")
                .maxAge(EXPIRATION_TIME)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie getCleanTokenCookie () {
        return ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
