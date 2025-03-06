package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Hall;

import java.util.Optional;

public interface HallService {

    Optional<Hall> findById(Long id);

    Optional<HallDto> findByName(String name);
}
