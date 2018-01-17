package ru.otus.homework02.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long id;
    private String login;
    private String password;
    private AccountRole role;

    public Account(long id) {
        this.id = id;
    }
}