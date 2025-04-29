package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration                                         // @Configuration — указывает, что объект является источником определений бина.
@EnableWebSecurity                                     // @EnableWebSecurity - включает безопасность (аутентификацию и авторизацию) для веб-приложения на основе Spring.
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired                                                             // @Autowired — используется для автоматического внедрения зависимостей (dependency injection)
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean                                              // @Bean - аннотация определяющая бин. Указывает, что метод создаёт, настраивает и инициализирует новый объект, управляемый Spring IoC контейнером
    public static PasswordEncoder passwordEncoder() {  // PasswordEncoder -  Помогает управлять паролями в приложении. Основная цель PasswordEncoder — сопоставить пароль, введённый пользователем, с паролем, хранящимся в объекте UserDetails в SecurityContext.
        return new BCryptPasswordEncoder();            // BCryptPasswordEncoder - это реализация PasswordEncoder, которая использует функцию сильного хэширования BCrypt.
    }

    @Bean                                              // @Bean - аннотация определяющая бин. Указывает, что метод создаёт, настраивает и инициализирует новый объект, управляемый Spring IoC контейнером
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {   // SecurityFilterChain - определяет цепочку фильтров для проверки безопасности в рамках фреймворка Spring Security.
                                                                                   // HttpSecurity - помогает настраивать безопасность для конкретных HTTP-запросов.
        http.csrf().disable()                                                      // csrf().disable() - отключает защиту CSRF    // csrf() - настраивает защиту от CSRF-атак (Cross Site Request Forgery) в цепочке фильтров безопасности.
                .authorizeRequests()                                               // authorizeRequests() - ограничивает доступ на основе реализаций RequestMatcher.
                .requestMatchers("/login", "/register", "/clubs", "/css/**", "/js/**")    // requestMatchers() - сопоставляет URL-адреса. С его помощью можно указать, какие запросы следует разрешить или аутентифицировать.
                .permitAll()                                                       // permitAll() - разрешает всем ролям доступ к определённому шаблону URL или любому методу сервиса и возвращает значение true для всех.
                .and()
                .formLogin(form -> form                                            // formLogin() - вызывает класс FormLoginConfigurer по умолчанию. Этот класс загружает страницу входа для аутентификации с помощью имени пользователя и пароля и перенаправляет пользователя к соответствующим обработчикам успеха или неудачи.
                        .loginPage("/login")                                       // loginPage() - указывает на настраиваемую страницу входа.
                        .defaultSuccessUrl("/clubs")                               // defaultSuccessUrl() - определяет URL по умолчанию, на который будет перенаправлен пользователь после успешной аутентификации.
                        .loginProcessingUrl("/login")                              // loginProcessingUrl() - указывает URL для обработки запросов входа.
                        .failureUrl("/login?error=true")         // failureUrl() - указывает URL, по которому будет происходить перенаправление пользователя в случае неудачи входа в систему.
                        .permitAll())                                                 // permitAll() - разрешает всем ролям доступ к определённому шаблону URL или любому методу сервиса и возвращает значение true для всех.
                .logout(logout -> logout                                              // logout() - метод, который выводит пользователя из системы в рамках фреймворка Spring Security.
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // logoutRequestMatcher() -  устанавливает RequestMatcher, вызывающий выход из системы.
                        .permitAll());                                                // AntPathRequestMatcher() — сопоставитель запросов в Spring. Он использует паттерны путей в стиле Ant для сопоставления URI запроса.
        return http.build();                                                          // permitAll() - разрешает всем ролям доступ к определённому шаблону URL или любому методу сервиса и возвращает значение true для всех.
    }                                                                                 // build() — метод класса HttpSecurity, который позволяет настраивать безопасность веб-запросов.

    public void configure(AuthenticationManagerBuilder builder) throws Exception {   // AuthenticationManagerBuilder - создаёт AuthenticationManager. Позволяет легко настроить аутентификацию по памяти, LDAP, на основе JDBC, добавить UserDetailsService и AuthenticationProvider
        builder.userDetailsService(userDetailsService)                               // UserDetailsService - Находит пользователя по имени в БД и загружает пользовательские данные.
                .passwordEncoder(passwordEncoder());                                 // PasswordEncoder - Помогает управлять паролями в приложении. Основная цель PasswordEncoder — сопоставить пароль, введённый пользователем, с паролем, хранящимся в объекте UserDetails в SecurityContext.
    }
}
