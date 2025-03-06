package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public SimpleFilmSessionService(@Qualifier("jpaFilmSessionRepository")FilmSessionRepository repository,
                                    @Qualifier("jpaFilmRepository")FilmRepository filmRepository,
                                    @Qualifier("jpaHallRepository")HallRepository hallRepository) {
        this.repository = repository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<FilmSessionDto> findById(Long id) {
        var optionalFilmSession = repository.findById(id);
        FilmSessionDto filmSessionDto = null;
        if (optionalFilmSession.isPresent()) {
           var filmSession = optionalFilmSession.get();
           filmSessionDto = new FilmSessionDto(filmSession.getId(),
                   filmRepository.findById(filmSession.getFilmId()).get().getName(),
                   filmSession.getFilmId(),
                   hallRepository.findById(filmSession.getHallsId()).get().getName(),
                   filmSession.getStart(),
                   filmSession.getEnd(),
                   filmSession.getPrice());

        }
        return Optional.ofNullable(filmSessionDto);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(filmSession -> new FilmSessionDto(
                        filmSession.getId(),
                        filmRepository.findById(filmSession.getFilmId()).get().getName(),
                        filmSession.getFilmId(),
                        hallRepository.findById(filmSession.getHallsId()).get().getName(),
                        filmSession.getStart(),
                        filmSession.getEnd(),
                        filmSession.getPrice()
                )).collect(Collectors.toList());
    }
}
