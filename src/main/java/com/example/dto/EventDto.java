package com.example.dto;

import com.example.model.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data                                            // @Data - автоматически генерирует ГЕТТЕРЫ и СЕТТЕРЫ для всех НЕ финальных полей, МЕТОДЫ equals() и hashCode() и МЕТОД toString().
@Builder                                         // @Builder - генерирует ПЕРЕГРУЖЕННЫЕ Конструкторы для объектов Entity.
@NoArgsConstructor                               // @NoArgsConstructor - заменяет Конструктор БЕЗ параметров (библиотека lombok)
@AllArgsConstructor                              // @AllArgsConstructor - заменяет Конструктор со всеми параметрами (библиотека lombok)
public class EventDto {

    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String type;
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}
