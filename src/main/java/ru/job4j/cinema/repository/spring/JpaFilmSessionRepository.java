package ru.job4j.cinema.repository.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmSessionRepository;

@Repository
public interface JpaFilmSessionRepository extends JpaRepository<FilmSession, Long>, FilmSessionRepository {
}
