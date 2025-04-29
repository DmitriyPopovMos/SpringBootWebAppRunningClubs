package com.example.repository;

import com.example.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository                                                           // @Repository — указывает, что аннотированный класс является репозиторием данных, используемым для хранения, извлечения и поиска данных из БД
public interface ClubRepository extends JpaRepository<Club, Long> {   // JpaRepository<T, ID> — это основной интерфейс Spring Data JPA, предоставляет базовые операции CRUD (Create, Read, Update, Delete) для сущностей.

    Optional<Club> findByTitle(String url);               // собственный метод для поиска по Title

    @Query("SELECT c FROM Club c WHERE c.title iLIKE CONCAT('%', :query, '%')")    // собственный фильтр iLIKE '%title%'
    List<Club> searchClubs(String query);
                                                                    // @Query("sql_запрос_к_БД") - позволяет выполнять запросы к Базе Данных на подобии SQL (HQL, JPQL)
}                                                                   // LIKE - выводит данные содержащиеся в поиске '%тар' / 'тар%' / '%тар%' (iLIKE - игнорирует регистр)
                                                                    // CONCAT(str1, 'char', strN) - Возвращает строку, созданную путем объединения всех аргументов.