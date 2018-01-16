package storage.db;

import entities.Employee;
import entities.EmployeePersonalInfo;
import entities.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeesDAO {

    private Connection connection;

    private PreparedStatement employeePersonalInfoInsertStatement;
    private PreparedStatement employeePersonalInfoInsertOrUpdateStatement;

    private PreparedStatement employeeInsertStatement;
    private PreparedStatement employeeInsertOrUpdateStatement;

    private PreparedStatement salaryInsertStatement;
    private PreparedStatement salaryInsertOrUpdateStatement;

    public EmployeesDAO(Connection connection) {
        this.connection = connection;
        prepareStatments();
    }


    private PreparedStatement prepareEmployeePersonalInfoInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_OR_UPDATESQL,
                DBConsts.TBL_EMPLOYEES_PERSONAL_INFO,
                DBConsts.F_ID + "," + DBConsts.F_LOCATION_ID + "," + DBConsts.F_FIRST_NAME + "," + DBConsts.F_MIDDLE_NAME + "," + DBConsts.F_SECOND_NAME + "," + DBConsts.F_PERSONAL_EMAIL,
                "?, ?, ?, ?, ?, ?",
                DBConsts.F_LOCATION_ID + " = ?, " + DBConsts.F_FIRST_NAME + " = ?, " + DBConsts.F_MIDDLE_NAME + " = ?," + DBConsts.F_SECOND_NAME + " = ?," + DBConsts.F_PERSONAL_EMAIL + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeePersonalInfoInsertStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_INTO_SQL,
                DBConsts.TBL_EMPLOYEES_PERSONAL_INFO,
                DBConsts.F_ID + "," + DBConsts.F_LOCATION_ID + "," + DBConsts.F_FIRST_NAME + "," + DBConsts.F_MIDDLE_NAME + "," + DBConsts.F_SECOND_NAME + "," + DBConsts.F_PERSONAL_EMAIL,
                "?, ?, ?, ?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeeInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_OR_UPDATESQL,
                DBConsts.TBL_EMPLOYEES,
                DBConsts.F_ID + "," + DBConsts.F_PERSONAL_INFO_ID + "," + DBConsts.F_ACCOUNT_ID + "," + DBConsts.F_DEPARTMENT_ID + "," + DBConsts.F_POSITION_ID + "," + DBConsts.F_INTERNAL_PHONE_NUMBER,
                "?, ?, ?, ?, ?, ?",
                DBConsts.F_PERSONAL_INFO_ID + " = ?, " + DBConsts.F_ACCOUNT_ID + " = ?, " + DBConsts.F_DEPARTMENT_ID + " = ?," + DBConsts.F_POSITION_ID + " = ?," + DBConsts.F_INTERNAL_PHONE_NUMBER + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeeInsertStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_INTO_SQL,
                DBConsts.TBL_EMPLOYEES,
                DBConsts.F_ID + "," + DBConsts.F_PERSONAL_INFO_ID + "," + DBConsts.F_ACCOUNT_ID + "," + DBConsts.F_DEPARTMENT_ID + "," + DBConsts.F_POSITION_ID + "," + DBConsts.F_INTERNAL_PHONE_NUMBER,
                "?, ?, ?, ?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareSalaryInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_OR_UPDATESQL, DBConsts.TBL_SALARIES, DBConsts.F_ID + "," + DBConsts.F_EMPLOYEE_ID + "," + DBConsts.F_SALARY, "?, ?, ?",
                DBConsts.F_EMPLOYEE_ID + " = ?, " + DBConsts.F_SALARY + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareSalaryInsertStatments() throws SQLException {
        String sql = String.format(DBConsts.INSERT_INTO_SQL, DBConsts.TBL_SALARIES, DBConsts.F_ID + "," + DBConsts.F_EMPLOYEE_ID + "," + DBConsts.F_SALARY, "?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private void prepareStatments() {
        try {
            employeePersonalInfoInsertStatement = prepareEmployeePersonalInfoInsertStatments();
            employeePersonalInfoInsertOrUpdateStatement = prepareEmployeePersonalInfoInsertOrUpdateStatments();

            employeeInsertStatement = prepareEmployeeInsertStatments();
            employeeInsertOrUpdateStatement = prepareEmployeeInsertOrUpdateStatments();

            salaryInsertStatement = prepareSalaryInsertStatments();
            salaryInsertOrUpdateStatement = prepareSalaryInsertOrUpdateStatments();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    void save(EmployeePersonalInfo employeePersonalInfo) throws SQLException {
        boolean isInsert = (employeePersonalInfo.getId() <= 0);
        int parIndex = isInsert? 1: 2;
        PreparedStatement statement = isInsert? employeePersonalInfoInsertStatement: employeePersonalInfoInsertOrUpdateStatement;

        statement.setLong(parIndex++, employeePersonalInfo.getLocation().getId());
        statement.setString(parIndex++, employeePersonalInfo.getFirstName());
        statement.setString(parIndex++, employeePersonalInfo.getMiddleName());
        statement.setString(parIndex++, employeePersonalInfo.getSecondName());
        statement.setString(parIndex++, employeePersonalInfo.getPersonalEMail());
        if (!isInsert) {
            statement.setLong(1, employeePersonalInfo.getId());
            statement.setLong(parIndex++, employeePersonalInfo.getLocation().getId());
            statement.setString(parIndex++, employeePersonalInfo.getFirstName());
            statement.setString(parIndex++, employeePersonalInfo.getMiddleName());
            statement.setString(parIndex++, employeePersonalInfo.getSecondName());
            statement.setString(parIndex, employeePersonalInfo.getPersonalEMail());
        }
        employeePersonalInfo.setId(PreparedStatmentHelper.executeAndGetID(statement, !isInsert, employeePersonalInfo.getId()));
    }

    void save(Employee employee) throws SQLException {
        boolean isInsert = (employee.getId() <= 0);
        int parIndex = isInsert? 1: 2;
        PreparedStatement statement = isInsert? employeeInsertStatement: employeeInsertOrUpdateStatement;

        statement.setLong(parIndex++, employee.getPersonalInfo().getId());
        statement.setLong(parIndex++, employee.getAccount().getId());
        statement.setLong(parIndex++, employee.getDepartment().getId());
        statement.setLong(parIndex++, employee.getPosition().getId());
        statement.setString(parIndex++, employee.getInternalPhoneNumber());
        if (!isInsert) {
            statement.setLong(1, employee.getId());
            statement.setLong(parIndex++, employee.getPersonalInfo().getId());
            statement.setLong(parIndex++, employee.getAccount().getId());
            statement.setLong(parIndex++, employee.getDepartment().getId());
            statement.setLong(parIndex++, employee.getPosition().getId());
            statement.setString(parIndex++, employee.getInternalPhoneNumber());
        }
        employee.setId(PreparedStatmentHelper.executeAndGetID(statement, !isInsert, employee.getId()));
    }


    void save(Salary salary) throws SQLException {
        boolean isInsert = (salary.getId() <= 0);
        int parIndex = isInsert? 1: 2;
        PreparedStatement statement = isInsert? salaryInsertStatement: salaryInsertOrUpdateStatement;

        statement.setLong(parIndex++, salary.getEmployee().getId());
        statement.setLong(parIndex++, salary.getSalary());
        if (!isInsert) {
            statement.setLong(1, salary.getId());
            statement.setLong(parIndex++, salary.getEmployee().getId());
            statement.setLong(parIndex++, salary.getSalary());
        }
        salary.setId(PreparedStatmentHelper.executeAndGetID(statement, !isInsert, salary.getId()));
    }



}
