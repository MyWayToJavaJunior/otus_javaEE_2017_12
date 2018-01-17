package ru.otus.homework02.storage.interfaces;

import ru.otus.homework02.entities.*;

import java.sql.SQLException;

public interface ICatalogsDAO {
    void save(AccountRole role) throws SQLException;
    void save(Location location) throws SQLException;
    void save(Position position) throws SQLException;
    void save(Department department) throws SQLException;
    void save(Account account) throws SQLException;
}
