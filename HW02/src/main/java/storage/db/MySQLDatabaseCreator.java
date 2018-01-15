package storage.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseCreator {
    private MySQLConnectionFactory connectionFactory;

    public MySQLDatabaseCreator(MySQLConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void createDataBase() {
        try (Connection connection = connectionFactory.getSystemDBConnection()) {
            Statement statement = connection.createStatement();

            statement.execute(String.format(DBConsts.DROP_DATABASE_SQL, connectionFactory.getDatabaseName()));
            statement.execute(String.format(DBConsts.CREATE_DATABASE_SQL, connectionFactory.getDatabaseName()));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void createIDNameTable(Connection connection, String tableName) throws SQLException {
        StringBuilder fields = new StringBuilder();
        fields.append(DBConsts.newIDFieldDesc(DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_NAME));
        Statement statement = connection.createStatement();
        statement.execute(String.format(DBConsts.CREATE_TABLE_SQL, tableName, fields.toString(), DBConsts.F_ID));
    }

    private void createDepartmentsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, DBConsts.TBL_DEPARTMENTS);
    }

    private void createLocationsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, DBConsts.TBL_LOCATIONS);
    }

    private void createPositionsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, DBConsts.TBL_POSITIONS);
    }

    private void createAccountsRolesTable(Connection connection) throws SQLException {
        createIDNameTable(connection, DBConsts.TBL_ACCOUNTS_ROLES);
    }

    private void createAccountsTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(DBConsts.newIDFieldDesc(DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_ACCOUNTS_ROLE_ID)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_LOGIN)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_PASSWORD)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_ACCOUNTS_ROLE_ID, DBConsts.TBL_ACCOUNTS_ROLES, DBConsts.F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(DBConsts.CREATE_TABLE_SQL, DBConsts.TBL_ACCOUNTS, fields.toString(), DBConsts.F_ID));
    }

    private void createEmployeesTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(DBConsts.newIDFieldDesc(DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_LOCATION_ID)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_FIRST_NAME)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_MIDDLE_NAME)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_SECOND_NAME)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_PERSONAL_EMAIL)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_LOCATION_ID, DBConsts.TBL_LOCATIONS, DBConsts.F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(DBConsts.CREATE_TABLE_SQL, DBConsts.TBL_EMPLOYEES, fields.toString(), DBConsts.F_ID));
    }

    private void createEmployeesCorporateInfoTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(DBConsts.newIDFieldDesc(DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_EMPLOYEE_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_ACCOUNT_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_DEPARTMENT_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_POSITION_ID)).append(",");
        fields.append(DBConsts.newStringFieldDesc(DBConsts.F_INTERNAL_PHONE_NUMBER)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_EMPLOYEE_ID, DBConsts.TBL_EMPLOYEES, DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_ACCOUNT_ID, DBConsts.TBL_ACCOUNTS, DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_DEPARTMENT_ID, DBConsts.TBL_DEPARTMENTS, DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_POSITION_ID, DBConsts.TBL_POSITIONS, DBConsts.F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(DBConsts.CREATE_TABLE_SQL, DBConsts.TBL_EMPLOYEES_CORPORATE_INFO, fields.toString(), DBConsts.F_ID));
    }


    private void createSalariesTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(DBConsts.newIDFieldDesc(DBConsts.F_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_EMPLOYEE_ID)).append(",");
        fields.append(DBConsts.newBigIntFieldDesc(DBConsts.F_SALARY)).append(",");
        fields.append(DBConsts.newForeignKey(DBConsts.F_EMPLOYEE_ID, DBConsts.TBL_EMPLOYEES, DBConsts.F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(DBConsts.CREATE_TABLE_SQL, DBConsts.TBL_SALARIES, fields.toString(), DBConsts.F_ID));
    }

    public void createTables() {
        try (Connection connection = connectionFactory.getDBConnection()) {
            createDepartmentsTable(connection);
            createLocationsTable(connection);
            createPositionsTable(connection);
            createAccountsRolesTable(connection);
            createAccountsTable(connection);
            createEmployeesTable(connection);
            createEmployeesCorporateInfoTable(connection);
            createSalariesTable(connection);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
