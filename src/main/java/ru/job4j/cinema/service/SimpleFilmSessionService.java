package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ThreadSafe
@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository repository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository repository,
                                    FilmRepository filmRepository, HallRepository hallRepository) {
        this.repository = repository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(filmSession -> new FilmSessionDto(
                        filmSession.getId(),
                        filmRepository.findById(filmSession.getFilmsId()).get().getName(),
                        filmSession.getFilmsId(),
                        hallRepository.findById(filmSession.getHallsId()).get().getName(),
                        filmSession.getStart(),
                        filmSession.getEnd(),
                        filmSession.getPrice()
                )).collect(Collectors.toList());
    }
}
