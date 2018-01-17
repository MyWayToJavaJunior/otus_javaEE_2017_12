package ru.otus.homework02.storage.db;

import ru.otus.homework02.entities.*;
import ru.otus.homework02.storage.interfaces.ICatalogsDAO;

import java.sql.*;

class CatalogsDAO implements ICatalogsDAO {
    private Connection connection;
    private PreparedStatement accountsRoleInsertStatement;
    private PreparedStatement accountsRoleInsertOrUpdateStatement;

    private PreparedStatement accountInsertStatement;
    private PreparedStatement accountInsertOrUpdateStatement;

    private PreparedStatement locationInsertStatement;
    private PreparedStatement locationInsertOrUpdateStatement;

    private PreparedStatement departmnetInsertStatement;
    private PreparedStatement departmnetInsertOrUpdateStatement;

    private PreparedStatement positionInsertStatement;
    private PreparedStatement positionInsertOrUpdateStatement;


    CatalogsDAO(Connection connection) {
        this.connection = connection;
        prepareStatments();
    }

    private PreparedStatement prepareIDNameInsertOrUpdateStatments(String tableName) throws SQLException {
        String sql = String.format(DBConsts.INSERT_OR_UPDATE_SQL, tableName, DBConsts.F_ID + "," + DBConsts.F_NAME, "?, ?", DBConsts.F_NAME + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareIDNameInsertStatments(String tableName) throws SQLException {
        String sql = String.format(DBConsts.INSERT_INTO_SQL, tableName, DBConsts.F_NAME, "?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareAccountInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_OR_UPDATE_SQL,
                DBConsts.TBL_ACCOUNTS,
                DBConsts.F_ID + "," + DBConsts.F_ACCOUNTS_ROLE_ID + "," + DBConsts.F_LOGIN + "," + DBConsts.F_PASSWORD,
                "?, ?, ?, ?",
                DBConsts.F_ACCOUNTS_ROLE_ID + " = ?, " + DBConsts.F_LOGIN + " = ?, " + DBConsts.F_PASSWORD + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareAccountInsertStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_INTO_SQL, DBConsts.TBL_ACCOUNTS, DBConsts.F_ACCOUNTS_ROLE_ID + "," + DBConsts.F_LOGIN + "," + DBConsts.F_PASSWORD, "?, ?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private void prepareStatments() {
        try {
            accountsRoleInsertStatement = prepareIDNameInsertStatments(DBConsts.TBL_ACCOUNTS_ROLES);
            accountsRoleInsertOrUpdateStatement = prepareIDNameInsertOrUpdateStatments(DBConsts.TBL_ACCOUNTS_ROLES);

            locationInsertStatement = prepareIDNameInsertStatments(DBConsts.TBL_LOCATIONS);
            locationInsertOrUpdateStatement = prepareIDNameInsertOrUpdateStatments(DBConsts.TBL_LOCATIONS);

            departmnetInsertStatement = prepareIDNameInsertStatments(DBConsts.TBL_DEPARTMENTS);
            departmnetInsertOrUpdateStatement = prepareIDNameInsertOrUpdateStatments(DBConsts.TBL_DEPARTMENTS);

            positionInsertStatement = prepareIDNameInsertStatments(DBConsts.TBL_POSITIONS);
            positionInsertOrUpdateStatement = prepareIDNameInsertOrUpdateStatments(DBConsts.TBL_POSITIONS);

            accountInsertStatement = prepareAccountInsertStatments();
            accountInsertOrUpdateStatement = prepareAccountInsertOrUpdateStatments();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private long saveIDNameRec(PreparedStatement insertStatment, PreparedStatement insertOrUpdateStatment, long id, String name) throws SQLException {
        PreparedStatement statement;
        if (id <= 0) {
            statement = insertStatment;
            statement.setString(1, name);
        } else {
            statement = insertOrUpdateStatment;
            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setString(3, name);
        }
        return PreparedStatmentHelper.executeAndGetID(statement, id > 0, id);
    }

    public void save(AccountRole role) throws SQLException {
        long id = saveIDNameRec(accountsRoleInsertStatement, accountsRoleInsertOrUpdateStatement, role.getId(), role.getName());
        role.setId(id);
    }

    public void save(Location location) throws SQLException {
        long id = saveIDNameRec(locationInsertStatement, locationInsertOrUpdateStatement, location.getId(), location.getName());
        location.setId(id);
    }

    public void save(Position position) throws SQLException {
        long id = saveIDNameRec(positionInsertStatement, positionInsertOrUpdateStatement, position.getId(), position.getName());
        position.setId(id);
    }

    public void save(Department department) throws SQLException {
        long id = saveIDNameRec(departmnetInsertStatement, departmnetInsertOrUpdateStatement, department.getId(), department.getName());
        department.setId(id);
    }

    public void save(Account account) throws SQLException {
        boolean isInsert = (account.getId() <= 0);
        int parIndex = isInsert? 1: 2;
        PreparedStatement statement = isInsert? accountInsertStatement: accountInsertOrUpdateStatement;

        statement.setLong(parIndex++, account.getRole().getId());
        statement.setString(parIndex++, account.getLogin());
        statement.setString(parIndex++, account.getPassword());
        if (!isInsert) {
            statement.setLong(1, account.getId());
            statement.setLong(parIndex++, account.getRole().getId());
            statement.setString(parIndex++, account.getLogin());
            statement.setString(parIndex, account.getPassword());
        }
        account.setId(PreparedStatmentHelper.executeAndGetID(statement, !isInsert, account.getId()));
    }
}
