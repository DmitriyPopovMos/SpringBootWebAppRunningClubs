package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter                                                  // @Getter - заменяет геттеры в коде (библиотека lombok)
@Setter                                                  // @Setter - заменяет сеттеры в коде (библиотека lombok)
@NoArgsConstructor                                       // @NoArgsConstructor - заменяет Конструктор БЕЗ параметров (библиотека lombok)
@AllArgsConstructor                                      // @AllArgsConstructor - заменяет Конструктор со всеми параметрами (библиотека lombok)
@Entity                                                  // @Entity - это Java Класс, который отображает информацию определенной таблицы в БД
@Table(name = "roles")                                   // @Table(name = "roles") - имя таблицы в БД. Показывает к какой именно таблице мы привязываем Класс
public class Role {

    @Id                                                  // @Id - указывает какой столбец таблицы является PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // @GeneratedValue - автогенерация PRIMARY KEY     // IDENTITY - стратегия, при которой PRIMARY KEY изменяется в соответствии с правилами, прописанными при создании таблицы
    private Long id;

    private String name;

    @ManyToMany(                                         // @ManyToMany — создаёт отношение «МНОГИЕ КО МНОГИМ» между двумя сущностями. Указывает, что МНОГО экземпляров 1 (одной) сущности, связаны с МНОГИМИ экземплярами другой сущности.
            mappedBy = "roles")                          // (mappedBy = "roles") — указывает на сторону ВЛАДЕЛЬЦА ОТНОШЕНИЙ и что связь происходит через поле "roles" в отношении между двумя сущностями.
    private List<UserEntity> users = new ArrayList<>();
}
