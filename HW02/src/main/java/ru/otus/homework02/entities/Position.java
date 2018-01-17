package ru.otus.homework02.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private long id;
    private String name;

    public Position(long id) {
        this.id = id;
    }
}
