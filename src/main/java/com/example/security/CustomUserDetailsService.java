package com.example.security;

import com.example.model.UserEntity;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


@Service                                                              // @Service - указывает, что класс реализует бизнес-логику, относящуюся к определённому домену приложения.
public class CustomUserDetailsService implements UserDetailsService { // UserDetailsService - Находит пользователя по имени в БД и загружает пользовательские данные.
    private UserRepository userRepository;

    @Autowired                                                        // @Autowired — используется для автоматического внедрения зависимостей (dependency injection)
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override                                                                                 // loadUserByUsername() - принимает в качестве параметра имя пользователя и возвращает полностью заполненный объект UserDetails. Этот объект представляет аутентифицированного пользователя в рамках Spring Security и содержит такие детали, как имя пользователя, пароль, полномочия (роли) и дополнительные атрибуты.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // UsernameNotFoundException - исключение, которое возникает в рамках Spring Security, когда пользователь пытается войти в систему с именем пользователя, не существующим в базе данных пользователей системы.
        UserEntity user = userRepository.findFirstByUsername(username);                       // поиск по первому username в списке
        if (user != null) {
            User authUser = new User(
                    user.getUsername(),                                                      // Поиск и получение данных Авторизованных User по Username в БД
                    user.getPassword(),                                                      // Поиск и получение данных Авторизованных User по Password в БД
                    user.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))        // SimpleGrantedAuthority - это базовая реализация интерфейса GrantedAuthority в контексте Spring Security.
                            .collect(Collectors.toList())
            );
            return authUser;
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password");              // UsernameNotFoundException - исключение, которое возникает в рамках Spring Security, когда пользователь пытается войти в систему с именем пользователя, не существующим в базе данных пользователей системы.
        }
    }
}
