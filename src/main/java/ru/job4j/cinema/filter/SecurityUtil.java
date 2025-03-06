package ru.job4j.cinema.filter;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static boolean isAcceptTextHtml(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        return acceptHeader != null && (acceptHeader.contains("text/html")
                || acceptHeader.contains("*/*") || acceptHeader.contains("*/html")
                || acceptHeader.contains("text/*")) && (xRequestedWith == null
                || !xRequestedWith.contains("XMLHttpRequest"));
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        } catch (Exception var1) {
            return false;
        }
    }

}
