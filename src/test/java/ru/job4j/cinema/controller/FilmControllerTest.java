package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmControllerTest {

    private FilmController controller;
    private FilmService service;

    @BeforeEach
    public void initServices() {
        service = mock(FilmService.class);
        controller = new FilmController(service);
    }

    @Test
    void getFilm() {
        var filmDto = new FilmDto(1, "test", "test", 2002, "test", 15, 1, 1);
        when(service.findById(filmDto.getId())).thenReturn(Optional.of(filmDto));

        var model = new ConcurrentModel();
        var view = controller.getFilm(model, filmDto.getId());
        var actualAttribute = model.getAttribute("film");

        assertThat(view).isEqualTo("films/one");
        assertThat(actualAttribute).isEqualTo(filmDto);
    }

    @Test
    void getAll() {
        var filmDto = new FilmDto(1, "test", "test", 2002, "test", 15, 1, 1);
        var filmDto1 = new FilmDto(1, "test", "test", 2002, "test", 15, 1, 1);
        var expected = List.of(filmDto, filmDto1);
        when(service.getAll()).thenReturn(expected);

        var model = new ConcurrentModel();
        var view = controller.getAll(model);
        var actualAttribute = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualAttribute).isEqualTo(expected);
    }
}