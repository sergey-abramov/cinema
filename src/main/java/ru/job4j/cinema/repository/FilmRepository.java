package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Optional<Film> findById(Long id);

    Collection<Film> getAll();
}
