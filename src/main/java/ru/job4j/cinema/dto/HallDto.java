package ru.job4j.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HallDto {

    private Long id;
    private String name;
    private int[] rowCount;
    private int[] placeCount;
    private String description;

}
