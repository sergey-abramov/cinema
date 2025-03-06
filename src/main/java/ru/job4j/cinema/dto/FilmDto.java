package ru.job4j.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FilmDto {

    private Long id;
    private String description;
    private String name;
    private int year;
    private String genreName;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;

}
