package ru.job4j.cinema.dto;

public class HallDto {

    private int id;
    private String name;
    private int[] rowCount;
    private int[] placeCount;
    private String description;

    public HallDto(int id, String name, int[] rowCount, int[] placeCount, String description) {
        this.id = id;
        this.name = name;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getRowCount() {
        return rowCount;
    }

    public void setRowCount(int[] rowCount) {
        this.rowCount = rowCount;
    }

    public int[] getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int[] placeCount) {
        this.placeCount = placeCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
