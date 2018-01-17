package ru.otus.homework02.storage.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatmentHelper {

    public static long executeAndGetID(PreparedStatement statement, boolean returnDefault, long defaultID) throws SQLException {
        statement.executeUpdate();
        long newId = defaultID;

        if (!returnDefault) {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
        }
        return newId;
    }
}
