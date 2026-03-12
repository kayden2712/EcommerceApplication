package org.example.ecommerceapplication.common.security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

    public static final String ACCESS_TOKEN_COOKIE = "accessToken";
    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    // Access token: 15 minutes (in seconds)
    private static final int ACCESS_TOKEN_MAX_AGE = 15 * 60;
    
    // Refresh token: 7 days (in seconds)
    private static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60;

    /**
     * Tạo httpOnly cookie cho access token
     */
    public void createAccessTokenCookie(HttpServletResponse response, String token) {
        createCookie(response, ACCESS_TOKEN_COOKIE, token, ACCESS_TOKEN_MAX_AGE);
    }

    /**
     * Tạo httpOnly cookie cho refresh token
     */
    public void createRefreshTokenCookie(HttpServletResponse response, String token) {
        createCookie(response, REFRESH_TOKEN_COOKIE, token, REFRESH_TOKEN_MAX_AGE);
    }

    /**
     * Tạo httpOnly, secure cookie
     */
    private void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);  // Không truy cập được từ JavaScript (chống XSS)
        cookie.setSecure(false);   // TODO: Set true khi deploy (HTTPS)
        cookie.setPath("/");       // Available cho toàn bộ application
        cookie.setMaxAge(maxAge);  // Thời gian sống
        response.addCookie(cookie);
    }

    /**
     * Đọc cookie từ request
     */
    public Optional<String> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        
        return Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    /**
     * Đọc access token từ cookie
     */
    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookie(request, ACCESS_TOKEN_COOKIE);
    }

    /**
     * Đọc refresh token từ cookie
     */
    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookie(request, REFRESH_TOKEN_COOKIE);
    }

    /**
     * Xóa cookie (set maxAge = 0)
     */
    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);   // TODO: Set true khi deploy (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(0);  // Xóa ngay lập tức
        response.addCookie(cookie);
    }

    /**
     * Xóa cả access và refresh token cookies (dùng cho logout)
     */
    public void deleteAuthCookies(HttpServletResponse response) {
        deleteCookie(response, ACCESS_TOKEN_COOKIE);
        deleteCookie(response, REFRESH_TOKEN_COOKIE);
    }
}
