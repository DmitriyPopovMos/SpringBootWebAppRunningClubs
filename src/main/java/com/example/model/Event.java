package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data                                            // @Data - автоматически генерирует ГЕТТЕРЫ и СЕТТЕРЫ для всех НЕ финальных полей, МЕТОДЫ equals() и hashCode() и МЕТОД toString().
@Builder                                         // @Builder - генерирует ПЕРЕГРУЖЕННЫЕ Конструкторы для объектов Entity.
@NoArgsConstructor                               // @NoArgsConstructor - заменяет Конструктор БЕЗ параметров (библиотека lombok)
@AllArgsConstructor                              // @AllArgsConstructor - заменяет Конструктор со всеми параметрами (библиотека lombok)
@Entity                                          // @Entity - это Java Класс, который отображает информацию определенной таблицы в БД
public class Event {

    @Id                                                   // @Id - указывает какой столбец таблицы является PRIMARY KEY
//    @GeneratedValue(strategy = GenerationType.IDENTITY)   // @GeneratedValue - автогенерация PRIMARY KEY     // IDENTITY - стратегия, при которой PRIMARY KEY изменяется в соответствии с правилами, прописанными при создании таблицы
    @Column(name = "id")                                  // @Column(name = "id") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private Long id;

    @Column(name = "name")                                  // @Column(name = "name") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private String name;

    @Column(name = "start_time")                            // @Column(name = "start_time") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime startTime;

    @Column(name = "end_time")                              // @Column(name = "end_time") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime endTime;

    @Column(name = "type",                                  // @Column(name = "type") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
            length=1000,                                    // length=1000 - длинa строки - количество символов
            columnDefinition = "varchar")                   // columnDefinition = "varchar") - тип данных столбца
    private String type;

    @Column(name = "photo_url")                             // @Column(name = "photo_url") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private String photoUrl;

    @CreationTimestamp                                      // @CreationTimestamp - устанавливает дату и время создания при первом сохранении записи.
    @Column(name = "created_on")                            // @Column(name = "created_on") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime createdOn;

    @UpdateTimestamp                                        // @UpdateTimestamp - обновляет время последнего изменения при каждом обновлении записи.
    @Column(name = "updated_on")                            // @Column(name = "updated_on") - имя столбца в таблице. Делает привязку (mapping) переменной класса к столбцу таблицы ДБ
    private LocalDateTime updatedOn;

    @ManyToOne                                              // @ManyToOne — создаёт отношение «МНОГИЕ К ОДНОМУ» между двумя сущностями. Указывает, что МНОГО экземпляров 1 (одной) сущности, связаны только с 1 (ОДНИМ) экземпляром другой сущности.
    @JoinColumn(name = "club_id", nullable = false)         // @JoinColumn(name = "") - устанавливает столбец FOREIGN KEY (внешний ключ) для ВНЕШНЕЙ таблицы, необходимый для связи с PRIMARY KEY (Первичный Ключ) ТЕКУЩЕЙ таблицы
    private Club club;
}
