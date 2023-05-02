package ru.job4j.cinema.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class FilmSessionDto {

    private int id;

    private String filmName;
    private int filmId;
    private String hallsName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;

    public FilmSessionDto(int id, String filmName, int filmId, String hallsName,
                          LocalDateTime start, LocalDateTime end, int price) {
        this.id = id;
        this.filmName = filmName;
        this.filmId = filmId;
        this.hallsName = hallsName;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallsName() {
        return hallsName;
    }

    public void setHallsName(String hallsName) {
        this.hallsName = hallsName;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
