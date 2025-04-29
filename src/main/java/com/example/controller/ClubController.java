package com.example.controller;

import com.example.dto.ClubDto;
import com.example.model.Club;
import com.example.model.UserEntity;
import com.example.security.SecurityUtil;
import com.example.service.CLubService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller                                               // @Controller - определяет классы в качестве контроллеров в Spring MVC. Помогает Spring определить компоненты, которые будут обрабатывать входящие запросы.
public class ClubController {

    private final CLubService cLubService;
    private UserService userService;

    @Autowired                                            // @Autowired — используется для автоматического внедрения зависимостей (dependency injection)
    public ClubController(CLubService cLubService, UserService userService) {
        this.cLubService = cLubService;
        this.userService = userService;
    }

    @GetMapping("/clubs")                               // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String findAll(Model model) {                   // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        UserEntity user = new UserEntity();
        List<Club> clubs = cLubService.findAll();          // возвращает список всех Club из БД     // Sort.by(Sort.Direction.ASC, "id") - устанавливает сортировку в порядке возрастания по полю "id"
        String username = SecurityUtil.getSessionUser();   // вызываем наш класс SecurityUtil и его метод getSessionUser() из пакета com.example.security.SecurityUtil
        if (username != null) {
            user = userService.findByUsername(username);   // поиск User по username
            model.addAttribute("user", user);  // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        }
        model.addAttribute("user", user);      // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("clubs", clubs);    // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "clubs-list";
    }

    @GetMapping("/clubs/{id}")                          // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String clubDetail(@PathVariable(name = "id") Long id,   // @PathVariable(name = "имя_переменной") - Переменная пути - извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
                             Model model) {                        // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        UserEntity user = new UserEntity();
        Club club = cLubService.findById(id);              // поиск Club по id
        String username = SecurityUtil.getSessionUser();   // вызываем наш класс SecurityUtil и его метод getSessionUser() из пакета com.example.security.SecurityUtil
        if (username != null) {
            user = userService.findByUsername(username);   // поиск User по username
            model.addAttribute("user", user);  // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        }
        model.addAttribute("user", user);      // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("club", club);      // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "clubs-detail";
    }

    @GetMapping("/clubs/new")                           // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String createClubForm(Model model) {            // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        ClubDto clubDto = new ClubDto();
        model.addAttribute("club", clubDto);   // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "clubs-create";
    }

    @PostMapping("/clubs/save")                         // @PostMapping("/Путь") — Обрабатывает HTTP POST-запросы и возвращает ответ (Аналог INSERT INTO)
    public String saveClub(@Valid Club club,               // @Valid - активирует стандартную валидацию Java Bean Validation, обеспечивая проверку на уровне полей объектов и их вложенных структур. Однако она не может запускать валидацию параметров на уровне методов контроллеров и сервисов.
                           BindingResult result) {         // BindingResult - хранит ошибки валидации и привязки данных во время обработки формы.
        if (result.hasErrors()) {                          // hasErrors() — проверяет, есть ли какие-либо ошибки валидации в форме.
            return "clubs-create";
        }
        cLubService.save(club);                            // сохраняем (добавляем) новый Club в БД
        return "redirect:/clubs";                          // redirect:/Путь - переадресация на указанную страницу
    }
                                                              // @PathVariable(name = "имя_переменной") - Переменная пути - извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
    @GetMapping("/clubs/update/{id}")                      // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String updateClubForm(@PathVariable(name = "id") Long id, Model model) {    // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
    Club club = cLubService.findById(id);                    // поиск Club по id
    model.addAttribute("club", club);            // model.addAttribute - добавляет атрибут к модели
    return"clubs-update";
    }

    @PostMapping("/clubs/update")                       // @PostMapping("/Путь") — Обрабатывает HTTP POST-запросы и возвращает ответ (Аналог INSERT INTO)
    public String updateClub(@Valid Club club,             // @Valid - активирует стандартную валидацию Java Bean Validation, обеспечивая проверку на уровне полей объектов и их вложенных структур. Однако она не может запускать валидацию параметров на уровне методов контроллеров и сервисов.
                             BindingResult result,         // BindingResult - хранит ошибки валидации и привязки данных во время обработки формы.
                             Model model) {                // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        if (result.hasErrors()) {                          // hasErrors() — проверяет, есть ли какие-либо ошибки валидации в форме.
            model.addAttribute("club", club);  // model.addAttribute - добавляет атрибут к модели
            return "clubs-update";
        }
        cLubService.save(club);                        // сохраняем (добавляем) новый Club в БД
        return "redirect:/clubs";                      // redirect:/Путь - переадресация на указанную страницу
    }
                                                          // @PathVariable(name = "имя_переменной") - Переменная пути - извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
    @GetMapping("/clubs/delete/{id}")                  // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String deleteClub(@PathVariable(name = "id") Long id) {
        cLubService.delete(id);                           // удаляет Club по id
        return "redirect:/clubs";                         // redirect:/Путь - переадресация на указанную страницу
    }

    // Фильтры поиска:
    @GetMapping("/clubs/search")                                                        // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
    public String searchClubs(@RequestParam(name = "query") String query, Model model) {   // @RequestParam(name = "query") - извлекает ОТДЕЛЬНЫЕ параметры из HTTP-запроса БЕЗ обработки всего тела запроса.
        UserEntity user = new UserEntity();
        List<Club> clubs = cLubService.searchClubs(query);     // собственный фильтр LIKE '%title%'
        String username = SecurityUtil.getSessionUser();       // вызываем наш класс SecurityUtil и его метод getSessionUser() из пакета com.example.security.SecurityUtil
        if (username != null) {
            user = userService.findByUsername(username);       // поиск User по username
            model.addAttribute("user", user);      // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        }
        model.addAttribute("user", user);          // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("clubs", clubs);        // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "clubs-list";
    }
}
