package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data                                              // @Data - автоматически генерирует ГЕТТЕРЫ и СЕТТЕРЫ для всех НЕ финальных полей, МЕТОДЫ equals() и hashCode() и МЕТОД toString().
@AllArgsConstructor                                // @AllArgsConstructor - заменяет Конструктор со всеми параметрами (библиотека lombok)
@NoArgsConstructor                                 // @NoArgsConstructor - заменяет Конструктор БЕЗ параметров (библиотека lombok)
@Builder                                           // @Builder - генерирует ПЕРЕГРУЖЕННЫЕ Конструкторы для объектов Entity.
@Entity                                            // @Entity - это Java Класс, который отображает информацию определенной таблицы в БД
@Table(name = "clubs")                             // @Table(name = "clubs") - имя таблицы в БД. Показывает к какой именно таблице мы привязываем Класс
public class Club {

    @Id                                                   // @Id - указывает какой столбец таблицы является PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // @GeneratedValue - автогенерация PRIMARY KEY     // IDENTITY - стратегия, при которой PRIMARY KEY изменяется в соответствии с правилами, прописанными при создании таблицы
    @Column(name = "id")                                  // @Column(name = "id") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private Long id;

    @NotEmpty(message = "Club title should not be empty") // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    @Column(name = "title")                               // @Column(name = "title") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private String title;

    @NotEmpty(message = "Photo link should not be empty") // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    @Column(name = "photo_url")                           // @Column(name = "photoUrl") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private String photoUrl;

    @NotEmpty(message = "Content should not be empty")    // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    @Column(name = "content",                             // @Column(name = "content") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
                    length=1000,                          // length=1000 - длинa строки - количество символов
                    columnDefinition = "varchar")         // columnDefinition = "varchar") - тип данных столбца
    private String content;

    @CreationTimestamp                               // @CreationTimestamp - устанавливает дату и время создания при первом сохранении записи.
    @Column(name = "created_on")                     // @Column(name = "createdOn") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime createdOn;

    @UpdateTimestamp                                 // @UpdateTimestamp - обновляет время последнего изменения при каждом обновлении записи.
    @Column(name = "updated_on")                     // @Column(name = "updatedOn") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime updatedOn;

    @ManyToOne                                       // @ManyToOne — создаёт отношение «МНОГИЕ К ОДНОМУ» между двумя сущностями. Указывает, что МНОГО экземпляров 1 (одной) сущности, связаны только с 1 (ОДНИМ) экземпляром другой сущности.
    @JoinColumn(name = "created_by",                 // @JoinColumn(name = "") - устанавливает столбец FOREIGN KEY (внешний ключ) для ТЕКУЩЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ВНЕШНЕЙ таблицы
                        nullable = false)            // (nullable = false) - указывает, что значение должно быть NOT NULL
    private UserEntity createdBy;

    @OneToMany(                                             // @OneToMany — создаёт отношения «ОДИН КО МНОГИМ» между сущностями. Указывает на то, что ОДИН экземпляр 1 (одной) сущности, связан с МНОГИМИ экземпляром другой сущности.
            mappedBy = "club",                              // (mappedBy = "club") — указывает на сторону ВЛАДЕЛЬЦА ОТНОШЕНИЙ и что связь происходит через поле "club" в отношении между двумя сущностями.  Для отношения @OneToMany, стороной-владельцем может быть только сторона многих (Many)
            cascade = CascadeType.REMOVE)                   // (cascade = CascadeType.REMOVE) - означает, что если в базе удаляется родительский объект, то это же нужно сделать и с его зависимыми объектами.
    private List<Event> events = new ArrayList<>();


    @Override
    public String toString() {
        return "\nClub{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
