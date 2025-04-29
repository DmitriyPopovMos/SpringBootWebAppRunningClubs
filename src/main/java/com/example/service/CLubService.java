package com.example.service;

import com.example.model.Club;
import com.example.model.UserEntity;
import com.example.repository.ClubRepository;
import com.example.repository.UserRepository;
import com.example.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service                                                       // @Service - указывает, что класс реализует бизнес-логику, относящуюся к определённому домену приложения.
public class CLubService {

    private ClubRepository clubRepository;
    private UserRepository userRepository;

    @Autowired
    public CLubService(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    public List<Club> findAll() {                                                    // возвращает список всех Club из БД
        return clubRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));  // Sort.by(Sort.Direction.ASC, "id") - устанавливает сортировку в порядке возрастания по полю "id"
    }

    public Club save(Club club) {                                     // сохраняем (добавляем) новый Club в БД
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);    // Поиск и получение данных Авторизованных User по Username в БД
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    public Club findById(Long id) {                   // поиск Club по id
        return clubRepository.getById(id);
    }

    public void delete(Long id) {                     // удаляет Club по id
        clubRepository.deleteById(id);
    }

    public List<Club> searchClubs(String query) {     // собственный фильтр iLIKE '%title%'
        return clubRepository.searchClubs(query);
    }
}
