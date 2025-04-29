package com.example.repository;

import com.example.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository                                                                 // @Repository — указывает, что аннотированный класс является репозиторием данных, используемым для хранения, извлечения и поиска данных из БД
public interface UserRepository extends JpaRepository<UserEntity, Long> {   // JpaRepository<T, ID> — это основной интерфейс Spring Data JPA, предоставляет базовые операции CRUD (Create, Read, Update, Delete) для сущностей.

    UserEntity findByEmail(String email);         // поиск User по email

    UserEntity findByUsername(String userName);   // поиск User по username

    UserEntity findFirstByUsername(String username);  // поиск по первому username в списке
}
