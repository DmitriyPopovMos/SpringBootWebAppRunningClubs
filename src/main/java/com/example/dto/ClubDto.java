package com.example.dto;

import com.example.model.UserEntity;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Data                                            // @Data - автоматически генерирует ГЕТТЕРЫ и СЕТТЕРЫ для всех НЕ финальных полей, МЕТОДЫ equals() и hashCode() и МЕТОД toString().
public class ClubDto {

    private Long id;
    private String title;
    private String photoUrl;
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDto> events;
}
