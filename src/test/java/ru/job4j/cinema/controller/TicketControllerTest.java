package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private TicketController controller;
    private FilmSessionService service;
    private HallService hallService;
    private TicketService ticketService;

    @BeforeEach
    public void initServices() {
        service = mock(FilmSessionService.class);
        hallService = mock(HallService.class);
        ticketService = mock(TicketService.class);
        controller = new TicketController(ticketService, service, hallService);
    }

    @Test
    void whenFilmSessionThenGetTicketBy() {
        var filmSession = new FilmSessionDto(1, "test", 1, "num1", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2), 120);
        var hallDto = new HallDto(1, "num1", new int[]{1, 2}, new int[]{1, 2}, "test");
        when(service.findById(filmSession.getId())).thenReturn(Optional.of(filmSession));
        when(hallService.findByName(filmSession.getHallsName())).thenReturn(Optional.of(hallDto));

        var model = new ConcurrentModel();
        var view = controller.buyTickedPlace(model, filmSession.getId());
        var actualSession = model.getAttribute("filmSession");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualSession).isEqualTo(filmSession);
    }

    @Test
    void whenErrorThenGetTicketBy() {
        var filmSession = new FilmSessionDto(1, "test", 1, "num1", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2), 120);
        when(service.findById(filmSession.getId())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = controller.buyTickedPlace(model, filmSession.getId());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Что-то пошло не так.");
    }

    @Test
    void whenErrorThenPostTicketSave() {
        String message = """
                Не удалось приобрести билет на заданное место.
                "Вероятно оно уже занято. Перейдите на страницу бронирования билетов
                и попробуйте снова.
                """;
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = controller.saveTicket(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualMessage).isEqualTo(message);
    }

    @Test
    void whenFilmSessionThenPostSaveTicket() {
        var ticket = new Ticket(1, 1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = controller.saveTicket(ticket, model);
        var actualRow = model.getAttribute("row");
        var actualPlace = model.getAttribute("place");

        assertThat(view).isEqualTo("tickets/save");
        assertThat(actualRow).isEqualTo(ticket.getRowNumber());
        assertThat(actualPlace).isEqualTo(ticket.getPlaceNumber());
    }
}