package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService,
                            HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    @GetMapping("/buy/{id}")
    public String buyTickedPlace(Model model, @PathVariable int id) {
        Optional<FilmSessionDto> filmSessionDto = filmSessionService.findById(id);
        if (filmSessionDto.isPresent()) {
            var filmSession = filmSessionDto.get();
            Optional<HallDto> hallDto = hallService.findByName(filmSession.getHallsName());
            if (hallDto.isPresent()) {
                model.addAttribute("rowCounts", hallDto.get().getRowCount());
                model.addAttribute("placeCounts", hallDto.get().getPlaceCount());
                model.addAttribute("filmSession", filmSession);
                return "tickets/buy";
            }
        }
        model.addAttribute("message", "Что-то пошло не так.");
        return "errors/404";
    }

    @PostMapping("/buy")
    public String saveTicket(@ModelAttribute Ticket ticket, Model model) {
            var optionalTicket = ticketService.save(ticket);
            if (optionalTicket.isEmpty()) {
                model.addAttribute("message", """
                        Не удалось приобрести билет на заданное место.
                        "Вероятно оно уже занято. Перейдите на страницу бронирования билетов
                        и попробуйте снова.
                        """);
                return "errors/404";
            }
            model.addAttribute("row", optionalTicket.get().getRowNumber());
            model.addAttribute("place", optionalTicket.get().getPlaceNumber());
            return "tickets/save";
    }
}
