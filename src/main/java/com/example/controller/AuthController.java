package com.example.controller;

import com.example.dto.RegistrationDto;
import com.example.model.UserEntity;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller                                             // @Controller - определяет классы в качестве контроллеров в Spring MVC. Помогает Spring определить компоненты, которые будут обрабатывать входящие запросы.
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")                            // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")                         // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String getRegisterForm(Model model) {        // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);   // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "register";
    }

    @PostMapping("/register/save")                                           // @PostMapping("/Путь") — Обрабатывает HTTP POST-запросы и возвращает ответ (Аналог INSERT INTO)
    public String register(    @Valid                                           // @Valid - активирует стандартную валидацию Java Bean Validation, обеспечивая проверку на уровне полей объектов и их вложенных структур.
                               @ModelAttribute("user")                          // @ModelAttribute("") - автоматически связывает значения полей объектов модели с элементами формы.
                               RegistrationDto user,
                               BindingResult result,                            // BindingResult - хранит ошибки валидации и привязки данных во время обработки формы.
                               Model model) {                                   // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());// поиск User по email
        if (existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "There is already a user with this email/username");  // rejectValue() - добавляет ошибку проверки к объекту BindingResult
            return "redirect:/register?fail";                                   // redirect:/Путь - переадресация на указанную страницу
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername());
        if (existingUsername != null && existingUsername.getUsername() != null
                && !existingUsername.getUsername().isEmpty()) {
            result.rejectValue("username", "There is already a user with this email/username");  // rejectValue() - добавляет ошибку проверки к объекту BindingResult
            return "redirect:/register?fail";                                   // redirect:/Путь - переадресация на указанную страницу
        }
        if (result.hasErrors()) {                                               // hasErrors() — проверяет, есть ли какие-либо ошибки валидации в форме.
            model.addAttribute("user", user);                       // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
            return "register";
        }
        userService.saveUser(user);                              // сохраняем User в БД
        return "redirect:/clubs?success";                        // redirect:/Путь - переадресация на указанную страницу
    }
}
