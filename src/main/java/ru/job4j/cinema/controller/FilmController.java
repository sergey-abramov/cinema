package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;

@ThreadSafe
@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String getFilm(Model model, @PathVariable Long id) {
        model.addAttribute("film", service.findById(id).get());
        return "films/one";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", service.getAll());
        return "films/list";
    }
}
