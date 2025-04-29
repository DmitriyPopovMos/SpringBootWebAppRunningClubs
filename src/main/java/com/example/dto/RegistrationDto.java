package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data                                             // @Data - автоматически генерирует ГЕТТЕРЫ и СЕТТЕРЫ для всех НЕ финальных полей, МЕТОДЫ equals() и hashCode() и МЕТОД toString().
public class RegistrationDto {

    private Long id;

    @NotEmpty                                     // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    private String username;

    @NotEmpty                                     // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    private String email;

    @NotEmpty                                     // @NotEmpty(message = "message_text") - проверяет, что поле или коллекция не равны null и содержат хотя бы один элемент. Если поле окажется пустым, то будет выдано сообщение с текстом «message_text».
    private String password;
}
