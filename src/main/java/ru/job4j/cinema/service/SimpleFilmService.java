package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ThreadSafe
@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository repository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository repository, GenreRepository genreRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<FilmDto> findById(Long id) {
        var optionalFilm = repository.findById(id);
        FilmDto filmDto = null;
        if (optionalFilm.isPresent()) {
            var film = optionalFilm.get();
            filmDto = new FilmDto(film.getId(), film.getDescription(), film.getName(),
                    film.getYear(), getGenre(film.getGenreId()),
                    film.getMinimalAge(), film.getDurationInMinutes(), film.getFileId());
        }
        return Optional.ofNullable(filmDto);
    }

    @Override
    public Collection<FilmDto> getAll() {
        return repository.getAll()
                .stream()
                .map(film -> new FilmDto(
                film.getId(), film.getDescription(), film.getName(),
                film.getYear(), getGenre(film.getGenreId()),
                film.getMinimalAge(), film.getDurationInMinutes(),
                film.getFileId())).collect(Collectors.toList());
    }

    private String getGenre(Long id) {
        return genreRepository.findById(id).get().getName();
    }

}
