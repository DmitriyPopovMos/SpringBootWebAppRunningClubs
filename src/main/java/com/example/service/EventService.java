package com.example.service;

import com.example.dto.EventDto;
import com.example.model.Event;
import org.springframework.stereotype.Service;
import java.util.List;


@Service                                             // @Service - указывает, что класс реализует бизнес-логику, относящуюся к определённому домену приложения.
public interface EventService {

    void createEvent(Long id, EventDto eventDto);    // создаёт Event

    List<Event> findAllEvents();                     // находим список всех Event в БД

    Event findByEventId(Long id);                    // находим Event по id

    void updateEvent(EventDto eventDto);             // сохраняем (добавляем) новый Event в БД

    void delete(Long id);                            // удалить Event по id
}