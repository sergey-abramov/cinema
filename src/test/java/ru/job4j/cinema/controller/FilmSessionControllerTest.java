package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionController controller;

    private FilmSessionService service;

    @BeforeEach
    public void initServices() {
        service = mock(FilmSessionService.class);
        controller = new FilmSessionController(service);
    }

    @Test
    void getAll() {
        var film1 = new FilmSessionDto(1L, "test", 1L, "1",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100);
        var film2 = new FilmSessionDto(1L, "test", 2L, "2",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 100);
        var expected = List.of(film1, film2);
        when(service.findAll()).thenReturn(expected);

        var model = new ConcurrentModel();
        var view = controller.getAll(model);
        var actualAttribute = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualAttribute).isEqualTo(expected);
    }
}