package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

@Repository
public interface JpaFilmRepository extends JpaRepository<Film, Long>, FilmRepository {
}
