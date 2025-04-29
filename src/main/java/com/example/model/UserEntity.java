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
@Table(name = "users")                                   // @Table(name = "users") - имя таблицы в БД. Показывает к какой именно таблице мы привязываем Класс
public class UserEntity {

    @Id                                                  // @Id - указывает какой столбец таблицы является PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // @GeneratedValue - автогенерация PRIMARY KEY     // IDENTITY - стратегия, при которой PRIMARY KEY изменяется в соответствии с правилами, прописанными при создании таблицы
    private Long id;

    private String username;
    private String email;
    private String password;

    @ManyToMany(                                                   // @ManyToMany — создаёт отношение «МНОГИЕ КО МНОГИМ» между двумя сущностями. Указывает, что МНОГО экземпляров 1 (одной) сущности, связаны с МНОГИМИ экземплярами другой сущности.
            fetch = FetchType.EAGER,                               // (fetch = FetchType.EAGER) - указывает, что при загрузке родительской сущности будут загружены и все её дочерние сущности (ЖАДНАЯ загрузка).
            cascade = CascadeType.ALL)                             // (cascade = CascadeType.ALL) — cascade означает, что ВСЕ ДЕЙСТВИЯ (ALL), выполненные с родительским объектом, нужно повторить и для его зависимых объектов
    @JoinTable(                                                    // @JoinTable(name = "table_name") - отображает связь между ДВУМЯ сущностями, через промежуточную таблицу, в отношениях «многие-ко-многим» @ManyToMany.
            name = "users_roles",                                  // name = "users_roles" - промежуточная таблица, в отношениях «многие-ко-многим» @ManyToMany.
            joinColumns = {                                        // joinColumns - указывает столбцы внешнего ключа (FOREIGN KEY) от владеющей сущности к таблице соединения.
                            @JoinColumn(                           // @JoinColumn(name = "") - устанавливает столбец FOREIGN KEY (внешний ключ) для ВНЕШНЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ТЕКУЩЕЙ таблицы
                            name = "user_id",                      // name = "user_id" - столбец FOREIGN KEY (внешний ключ) для ВНЕШНЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ТЕКУЩЕЙ таблицы
                            referencedColumnName = "id")},         // referencedColumnName = «id» - указывает, что при определении связи между сущностями ссылаться нужно на столбец «id» таблицы с PRIMARY KEY
            inverseJoinColumns = {                                 // inverseJoinColumns - указывает на поле, использующееся для обратной связи.
                                    @JoinColumn(                   // @JoinColumn(name = "") - устанавливает столбец FOREIGN KEY (внешний ключ) для ВНЕШНЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ТЕКУЩЕЙ таблицы
                                    name = "role_id",              // name = "user_id" - столбец FOREIGN KEY (внешний ключ) для ВНЕШНЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ТЕКУЩЕЙ таблицы
                                    referencedColumnName = "id")}  // referencedColumnName = «id» - указывает, что при определении связи между сущностями ссылаться нужно на столбец «id» таблицы с PRIMARY KEY
    )
    private List<Role> roles = new ArrayList<>();
}
