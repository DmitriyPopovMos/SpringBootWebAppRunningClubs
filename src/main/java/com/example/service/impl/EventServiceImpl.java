package com.example.service.impl;

import com.example.dto.EventDto;
import com.example.model.Club;
import com.example.model.Event;
import com.example.repository.ClubRepository;
import com.example.repository.EventRepository;
import com.example.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service                                                       // @Service - указывает, что класс реализует бизнес-логику, относящуюся к определённому домену приложения.
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired                                                                                  // @Autowired — используется для автоматического внедрения зависимостей (dependency injection)
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public List<Event> findAllEvents() {                                              // выводим список всех Event из БД
        return eventRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));  // Sort.by(Sort.Direction.ASC, "id") - устанавливает сортировку в порядке возрастания по полю "id"
    }

    @Override
    public Event findByEventId(Long id) {
        return eventRepository.getById(id);                           // находим Event по id
    }

    @Override
    public void updateEvent(EventDto eventDto) {                      // обновляем Event по id
        Event event = mapToEvent(eventDto);                           // заполняем event данными из eventDto
        eventRepository.save(event);                                  // сохраняем event в БД
    }

    @Override
    public void delete(Long id) {                                     // удалить Event по id
        eventRepository.deleteById(id);
    }


    @Override
    public void createEvent(Long id, EventDto eventDto) {              // создаём новый Event
        Club club = clubRepository.getById(id);                        // находим Club по id
        Event event = mapToEventIncrementId(eventDto);                 // заполняем event данными из eventDto
        event.setClub(club);                                           // добавляем club к event
        eventRepository.save(event);                                   // сохраняем event в БД
    }


    /* Из-за конфликта @ModelAttribute и @GeneratedValue(strategy = GenerationType.IDENTITY) Hibrnate создаёт несколько одновременных
    потоков выполнения запроса вследствие чего приложение падает в ошибку.
        При отключении @GeneratedValue(strategy = GenerationType.IDENTITY) перестаёт автоинкрементироваться id в БД Event,
     и при добавлении каждой новой записи происходит затирание имеющейся под этим id записи.
        Если отключить @ModelAttribute, то данные перестают считываться с web формы на странице (Model)

     Для решения проблемы необходимо создать собственный автоикрементируемый id для Event*/
    public Long eventId() {                                              // устанавливаем автоинкремент для eventId
        long num = 1;                                                    // устанавливаем id по умолчанию при отсутствии записей в БД Event
        List<Event> event = eventRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));  // получаем все Event из БД
        if (!event.isEmpty()) {                                          // проверяем пуста ли БД или нет
            long maxId = event.stream().mapToInt(e -> Math.toIntExact(e.getId())).max().getAsInt();    // находим имеющийся maxId у Event в БД
            return maxId + 1;                                            // задаём автоинкремент для eventId
        }
        return num;                                                      // задаём id по умолчанию 1, если в БД нет записей
    }

    private Event mapToEventIncrementId(EventDto eventDto) {             // форма для заполнения данных принимаемых eventDto
        return Event.builder()
                .id(eventId())                                           // добавляем метод для автоинкремента ID в талице Event
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .type(eventDto.getType())
                .photoUrl(eventDto.getPhotoUrl())
                .createdOn(eventDto.getCreatedOn())
                .updatedOn(eventDto.getUpdatedOn())
                .build();
    }

    private Event mapToEvent(EventDto eventDto) {              // форма для заполнения данных принимаемых eventDto
        return Event.builder()
                .id(eventDto.getId())                          // получаем id из модели
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .type(eventDto.getType())
                .photoUrl(eventDto.getPhotoUrl())
                .createdOn(eventDto.getCreatedOn())
                .updatedOn(eventDto.getUpdatedOn())
                .club(eventDto.getClub())
                .build();
    }
}
