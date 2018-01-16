package storage.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static storage.db.DBConsts.*;

public class MySQLDatabaseCreator {
    private MySQLConnectionFactory connectionFactory;

    public MySQLDatabaseCreator(MySQLConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void createDataBase() {
        try (Connection connection = connectionFactory.getSystemDBConnection()) {
            Statement statement = connection.createStatement();

            statement.execute(String.format(DROP_DATABASE_SQL, connectionFactory.getDatabaseName()));
            statement.execute(String.format(CREATE_DATABASE_SQL, connectionFactory.getDatabaseName()));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void createIDNameTable(Connection connection, String tableName) throws SQLException {
        StringBuilder fields = new StringBuilder();
        fields.append(newIDFieldDesc(F_ID)).append(",");
        fields.append(newStringFieldDesc(F_NAME));
        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_TABLE_SQL, tableName, fields.toString(), F_ID));
    }

    private void createDepartmentsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, TBL_DEPARTMENTS);
    }

    private void createLocationsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, TBL_LOCATIONS);
    }

    private void createPositionsTable(Connection connection) throws SQLException {
        createIDNameTable(connection, TBL_POSITIONS);
    }

    private void createAccountsRolesTable(Connection connection) throws SQLException {
        createIDNameTable(connection, TBL_ACCOUNTS_ROLES);
    }

    private void createAccountsTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(newIDFieldDesc(F_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_ACCOUNTS_ROLE_ID)).append(",");
        fields.append(newStringFieldDesc(F_LOGIN)).append(",");
        fields.append(newStringFieldDesc(F_PASSWORD)).append(",");
        fields.append(newForeignKey(F_ACCOUNTS_ROLE_ID, TBL_ACCOUNTS_ROLES, F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_TABLE_SQL, TBL_ACCOUNTS, fields.toString(), F_ID));
    }

    private void createEmployeesPersonalInfoTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(newIDFieldDesc(F_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_LOCATION_ID)).append(",");
        fields.append(newStringFieldDesc(F_FIRST_NAME)).append(",");
        fields.append(newStringFieldDesc(F_MIDDLE_NAME)).append(",");
        fields.append(newStringFieldDesc(F_SECOND_NAME)).append(",");
        fields.append(newStringFieldDesc(F_PERSONAL_EMAIL)).append(",");
        fields.append(newForeignKey(F_LOCATION_ID, TBL_LOCATIONS, F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_TABLE_SQL, TBL_EMPLOYEES_PERSONAL_INFO, fields.toString(), F_ID));
    }

    private void createEmployeesTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(newIDFieldDesc(F_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_PERSONAL_INFO_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_ACCOUNT_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_DEPARTMENT_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_POSITION_ID)).append(",");
        fields.append(newStringFieldDesc(F_INTERNAL_PHONE_NUMBER)).append(",");
        fields.append(newForeignKey(F_PERSONAL_INFO_ID, TBL_EMPLOYEES_PERSONAL_INFO, F_ID)).append(",");
        fields.append(newForeignKey(F_ACCOUNT_ID, TBL_ACCOUNTS, F_ID)).append(",");
        fields.append(newForeignKey(F_DEPARTMENT_ID, TBL_DEPARTMENTS, F_ID)).append(",");
        fields.append(newForeignKey(F_POSITION_ID, TBL_POSITIONS, F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_TABLE_SQL, TBL_EMPLOYEES, fields.toString(), F_ID));
    }


    private void createSalariesTable(Connection connection) throws SQLException {
        StringBuilder fields = new StringBuilder();

        fields.append(newIDFieldDesc(F_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_EMPLOYEE_ID)).append(",");
        fields.append(newBigIntFieldDesc(F_SALARY)).append(",");
        fields.append(newForeignKey(F_EMPLOYEE_ID, TBL_EMPLOYEES, F_ID));

        Statement statement = connection.createStatement();
        statement.execute(String.format(CREATE_TABLE_SQL, TBL_SALARIES, fields.toString(), F_ID));
    }

    private void createAccountsView(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT A.*, R." + F_NAME + " " + F_ROLE_NAME + " " +
                     "FROM " + TBL_ACCOUNTS + " A LEFT JOIN " +
                               TBL_ACCOUNTS_ROLES + " R ON A." + F_ACCOUNTS_ROLE_ID + " = R." + F_ID;

        statement.execute(String.format(CREATE_VIEW_SQL, VIEW_ACCOUNTS, sql));
    }

    private void createEmployeesPersonalInfoView(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT PI.*, L." + F_NAME + " " + F_LOCATION_NAME + " " +
                     "FROM " + TBL_EMPLOYEES_PERSONAL_INFO + " PI LEFT JOIN " +
                               TBL_LOCATIONS + " L ON PI." + F_LOCATION_ID + " = L." + F_ID;

        statement.execute(String.format(CREATE_VIEW_SQL, VIEW_EMPLOYEES_PERSONAL_INFO, sql));
    }


    private void createEmployeesView(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT " + "E." + F_ID + ", " +
                                 "A." + F_ACCOUNTS_ROLE_ID + ", " +
                                 "E." + F_ACCOUNT_ID + ", " +
                                 "E." + F_PERSONAL_INFO_ID + ", " +
                                 "PI." + F_LOCATION_ID + ", " +
                                 "E." + F_DEPARTMENT_ID + ", " +
                                 "E." + F_POSITION_ID + ", " +
                                 "S." + F_ID + " " + F_SALARY_ID + ", " +

                                 "A." + F_ROLE_NAME + ", " +
                                 "A." + F_LOGIN + ", " +
                                 "A." + F_PASSWORD + ", " +

                                 "PI." + F_LOCATION_NAME + ", " +
                                 "PI." + F_FIRST_NAME + ", " +
                                 "PI." + F_MIDDLE_NAME + ", " +
                                 "PI." + F_SECOND_NAME + ", " +
                                 "PI." + F_PERSONAL_EMAIL + ", " +
                                 "E." + F_INTERNAL_PHONE_NUMBER + ", " +

                                 "D." + F_NAME + " " + F_DEPARTMENT_NAME + ", " +
                                 "P." + F_NAME + " " + F_POSITION_NAME + ", " +
                                 "S." + F_SALARY + " " +

                     "FROM ((((" + TBL_EMPLOYEES + " E LEFT JOIN " +
                               VIEW_ACCOUNTS + " A ON E." + F_ACCOUNT_ID + " = A." + F_ID + ") LEFT JOIN " +
                               VIEW_EMPLOYEES_PERSONAL_INFO + " PI ON E." + F_PERSONAL_INFO_ID + " = PI." + F_ID + ") LEFT JOIN " +
                               TBL_DEPARTMENTS + " D ON E." + F_DEPARTMENT_ID + " = D." + F_ID + ") LEFT JOIN " +
                               TBL_POSITIONS + " P ON E." + F_POSITION_ID + " = P." + F_ID + ") LEFT JOIN " +
                               TBL_SALARIES + " S ON E." + F_ID + " = S." + F_EMPLOYEE_ID;

        sql = String.format(CREATE_VIEW_SQL, VIEW_EMPLOYEES, sql);
        statement.execute(sql);
    }

    private void createDeleteEmployeesProc(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = String.format(CREATE_PROC_SQL, String.format(SP_DELETE_EMPLOYEE_BY_ID, "IN EmployeeID BIGINT"),
                "DELETE FROM " + TBL_EMPLOYEES + " WHERE ID = EmployeeID;");
        statement.execute(sql);
    }

    private void createGetMaxSalaryFun(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = String.format(CREATE_FUNCTION_SQL, FN_GET_MAX_SALARY, "BIGINT",
                "DECLARE MaxSalary BIGINT; SELECT MAX(Salary) INTO MaxSalary FROM " + TBL_SALARIES + ";", "MaxSalary");
        statement.execute(sql);
    }

    private void createSelectEmployeesWithMaxSalaryProc(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = String.format(CREATE_PROC_SQL, SP_GET_ALL_EMPLOYEES_WITH_MAX_SALARY,
                "SELECT * FROM " + VIEW_EMPLOYEES + " WHERE Salary = " + FN_GET_MAX_SALARY + ";", "MaxSalary");
        statement.execute(sql);
    }

    public void createDatabaseStructure() {
        try (Connection connection = connectionFactory.getDBConnection()) {
            createDepartmentsTable(connection);
            createLocationsTable(connection);
            createPositionsTable(connection);
            createAccountsRolesTable(connection);
            createAccountsTable(connection);
            createEmployeesPersonalInfoTable(connection);
            createEmployeesTable(connection);
            createSalariesTable(connection);

            createAccountsView(connection);
            createEmployeesPersonalInfoView(connection);
            createEmployeesView(connection);

            createDeleteEmployeesProc(connection);

            createGetMaxSalaryFun(connection);
            createSelectEmployeesWithMaxSalaryProc(connection);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
