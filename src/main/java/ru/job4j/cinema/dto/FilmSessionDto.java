package ru.job4j.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FilmSessionDto {

    private Long id;
    private String filmName;
    private Long filmId;
    private String hallsName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;

}
