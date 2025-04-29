package com.example.service;

import com.example.dto.RegistrationDto;
import com.example.model.UserEntity;


public interface UserService {

    void saveUser(RegistrationDto registrationDto);            // сохраняем User в БД

    UserEntity findByEmail(String email);                       // поиск User по email

    UserEntity findByUsername(String username);                 // поиск User по username
}
