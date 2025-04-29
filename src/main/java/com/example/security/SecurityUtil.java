package com.example.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getSessionUser() {                                   // Authentication - представляет токен запроса аутентификации или аутентифицированного пользователя в контексте Spring Security.
        Authentication authentication = SecurityContextHolder                 // SecurityContextHolder - хранит информацию о текущем контексте безопасности приложения. В него входит информация об аутентифицированном пользователе, его разрешениях и другие связанные данные.
                                                .getContext()                 // getContext() - возвращает текущий SecurityContext
                                                .getAuthentication();         // getAuthentication() - возвращает объект Authentication, представляющий текущего аутентифицированного пользователя.
        if (!(authentication instanceof AnonymousAuthenticationToken)) {      // AnonymousAuthenticationToken - представляет анонимную аутентификацию.
            String currentUsername = authentication.getName();                // getName() - возвращает имя текущего пользователя.
            return currentUsername;
        }
        return null;
    }
}
