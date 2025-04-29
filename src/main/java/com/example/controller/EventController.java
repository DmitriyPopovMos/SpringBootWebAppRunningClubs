package com.example.controller;

import com.example.dto.EventDto;
import com.example.model.Event;
import com.example.model.UserEntity;
import com.example.security.SecurityUtil;
import com.example.service.EventService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;


@Controller                                            // @Controller - определяет классы в качестве контроллеров в Spring MVC. Помогает Spring определить компоненты, которые будут обрабатывать входящие запросы.
public class EventController {


    private EventService eventService;
    private UserService userService;

    @Autowired                                          // @Autowired — используется для автоматического внедрения зависимостей (dependency injection)
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    @GetMapping("/events")                                        // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String eventList(Model model) {                           // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        UserEntity user = new UserEntity();
        List<Event> events = eventService.findAllEvents();           // находим список всех Event в БД
        String username = SecurityUtil.getSessionUser();             // вызываем наш класс SecurityUtil и его метод getSessionUser() из пакета com.example.security.SecurityUtil
        if (username != null) {
            user = userService.findByUsername(username);             // поиск User по username
            model.addAttribute("user", user);            // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        }
        model.addAttribute("user", user);                // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("events", events);            // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "events-list";
    }


    @GetMapping("/events/{id}")                                    // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String viewEvent(@PathVariable(name = "id") Long id,      // @PathVariable(name = "имя_переменной") - Переменная пути. Извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
                            Model model) {                           // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        UserEntity user = new UserEntity();
        Event event = eventService.findByEventId(id);                // находим Event по id
        String username = SecurityUtil.getSessionUser();             // вызываем наш класс SecurityUtil и его метод getSessionUser() из пакета com.example.security.SecurityUtil
        if (username != null) {
            user = userService.findByUsername(username);             // поиск User по username
            model.addAttribute("user", user);            // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        }
        model.addAttribute("club", event);               // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("user", user);                // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("event", event);              // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "events-detail";
    }


    @GetMapping("/events/new/{id}")                                // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String createEventForm(@PathVariable(name = "id") Long id, // @PathVariable(name = "имя_переменной") - Переменная пути. Извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
                                  Model model) {                      // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        Event event = new Event();
        model.addAttribute("id", id);                     // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        model.addAttribute("event", event);               // model.addAttribute(KEY, VALUE) - добавляет атрибут к модели
        return "events-create";
    }


    @PostMapping("/events/{id}")                                      // @PostMapping("/Путь") — Обрабатывает HTTP POST-запросы и возвращает ответ (Аналог INSERT INTO)
    public String createEvent(@PathVariable(name = "id") Long id,        // @PathVariable(name = "имя_переменной") - Переменная пути. Извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
                              @ModelAttribute("event")EventDto eventDto, // @ModelAttribute("") - автоматически связывает значения полей объектов модели с элементами формы.
                              BindingResult result,                      // BindingResult - хранит ошибки валидации и привязки данных во время обработки формы.
                              Model model) {                             // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>

        if (result.hasErrors()) {                                     // hasErrors() — проверяет, есть ли какие-либо ошибки валидации в форме.
            model.addAttribute("event", eventDto);        // model.addAttribute - добавляет атрибут к модели
            return "clubs-create";
        }

        eventService.createEvent(id, eventDto);                          // создаём новый Event
        return "redirect:/clubs/" + id;                                  // redirect:/Путь - переадресация на указанную страницу
    }

    @GetMapping("/events/update/{id}")                              // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String updateEventForm(@PathVariable(name = "id") Long id, Model model) {    // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        Event event = eventService.findByEventId(id);                 // поиск Event по id
        model.addAttribute("event", event);                // model.addAttribute - добавляет атрибут к модели
        return"events-update";
    }
                                                                      // @ModelAttribute("") - автоматически связывает значения полей объектов модели с элементами формы.
    @PostMapping("/events/update/{id}")                            // @PostMapping("/Путь") — Обрабатывает HTTP POST-запросы и возвращает ответ (Аналог INSERT INTO)
    public String updateEvent(@PathVariable(name = "id") Long id,     // @PathVariable(name = "имя_переменной") - Переменная пути. Извлекает ОТДЕЛЬНЫЕ значения из переменных шаблона URI в URL-адресе запроса.
                              @Valid @ModelAttribute("event") EventDto eventDto,    // @Valid - активирует стандартную валидацию Java Bean Validation, обеспечивая проверку на уровне полей объектов и их вложенных структур. Однако она не может запускать валидацию параметров на уровне методов контроллеров и сервисов.
                              BindingResult result,                   // BindingResult - хранит ошибки валидации и привязки данных во время обработки формы.
                              Model model) {                          // Model - интерфейс из фреймворка Spring MVC для добавления атрибутов к модели <KEY, VALUE>. Похож на Map<K, V>
        if (result.hasErrors()) {                                     // hasErrors() — проверяет, есть ли какие-либо ошибки валидации в форме.
            model.addAttribute("event", eventDto);        // model.addAttribute - добавляет атрибут к модели
            return "events-update";
        }
        Event event = eventService.findByEventId(id);
        eventDto.setId(id);
        eventDto.setClub(event.getClub());
        eventService.updateEvent(eventDto);              // сохраняем (добавляем) новый Event в БД
        return "redirect:/events";                       // redirect:/Путь - переадресация на указанную страницу
    }

    @GetMapping("/events/delete/{id}")                  // @GetMapping("/Путь") - Обрабатывает HTTP GET-запросы и возвращает ответ (Аналог SELECT)
    public String deleteEvent(@PathVariable(name = "id") Long id) {
        eventService.delete(id);                           // удаляет Event по id
        return "redirect:/events";                         // redirect:/Путь - переадресация на указанную страницу
    }
}
