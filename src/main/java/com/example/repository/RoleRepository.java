package com.example.repository;

import com.example.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository                                                           // @Repository — указывает, что аннотированный класс является репозиторием данных, используемым для хранения, извлечения и поиска данных из БД
public interface RoleRepository extends JpaRepository<Role, Long> {   // JpaRepository<T, ID> — это основной интерфейс Spring Data JPA, предоставляет базовые операции CRUD (Create, Read, Update, Delete) для сущностей.

    Role findByName(String name);                // поиск Role по name
}
