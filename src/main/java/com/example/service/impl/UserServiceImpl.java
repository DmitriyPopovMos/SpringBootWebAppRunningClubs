package com.example.service.impl;

import com.example.dto.RegistrationDto;
import com.example.model.Role;
import com.example.model.UserEntity;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service                                                       // @Service - указывает, что класс реализует бизнес-логику, относящуюся к определённому домену приложения.
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;                 // PasswordEncoder -  Помогает управлять паролями в приложении. Основная цель PasswordEncoder — сопоставить пароль, введённый пользователем, с паролем, хранящимся в объекте UserDetails в SecurityContext.

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role role = roleRepository.findByName("USER");         // поиск Role по name
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);                             // сохраняем User в БД
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);               // поиск User по email
    }

    @Override
    public UserEntity findByUsername(String username) {         // поиск User по username
        return userRepository.findByUsername(username);
    }
}
