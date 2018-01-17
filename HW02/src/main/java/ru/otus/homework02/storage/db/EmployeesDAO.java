package ru.otus.homework02.storage.db;

import ru.otus.homework02.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.homework02.storage.db.DBConsts.*;

public class EmployeesDAO implements ru.otus.homework02.storage.interfaces.IEmployeesDAO {

    private Connection connection;

    private PreparedStatement employeePersonalInfoInsertStatement;
    private PreparedStatement employeePersonalInfoInsertOrUpdateStatement;

    private PreparedStatement employeeInsertStatement;
    private PreparedStatement employeeInsertOrUpdateStatement;

    private PreparedStatement salaryInsertStatement;
    private PreparedStatement salaryInsertOrUpdateStatement;

    private PreparedStatement employeeSelectByIDStatement;
    private PreparedStatement employeeSelectAllStatement;

    private CallableStatement employeeDeletByIDStatement;

    private CallableStatement employeeSelectAllWithMaxSalaryStatement;

    public EmployeesDAO(Connection connection) {
        this.connection = connection;
        prepareStatments();
    }


    private PreparedStatement prepareEmployeePersonalInfoInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(INSERT_OR_UPDATE_SQL,
                TBL_EMPLOYEES_PERSONAL_INFO,
                F_ID + "," + F_LOCATION_ID + "," + F_FIRST_NAME + "," + F_MIDDLE_NAME + "," + F_SECOND_NAME + "," + F_PERSONAL_EMAIL,
                "?, ?, ?, ?, ?, ?",
                F_LOCATION_ID + " = ?, " + F_FIRST_NAME + " = ?, " + F_MIDDLE_NAME + " = ?," + F_SECOND_NAME + " = ?," + F_PERSONAL_EMAIL + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeePersonalInfoInsertStatments() throws SQLException {
        String sql = String.format(INSERT_INTO_SQL,
                TBL_EMPLOYEES_PERSONAL_INFO,
                F_ID + "," + F_LOCATION_ID + "," + F_FIRST_NAME + "," + F_MIDDLE_NAME + "," + F_SECOND_NAME + "," + F_PERSONAL_EMAIL,
                "?, ?, ?, ?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeeInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(INSERT_OR_UPDATE_SQL,
                TBL_EMPLOYEES,
                F_ID + "," + F_PERSONAL_INFO_ID + "," + F_ACCOUNT_ID + "," + F_DEPARTMENT_ID + "," + F_POSITION_ID + "," + F_INTERNAL_PHONE_NUMBER,
                "?, ?, ?, ?, ?, ?",
                F_PERSONAL_INFO_ID + " = ?, " + F_ACCOUNT_ID + " = ?, " + F_DEPARTMENT_ID + " = ?," + F_POSITION_ID + " = ?," + F_INTERNAL_PHONE_NUMBER + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeeInsertStatments() throws SQLException {
        String sql = String.format(INSERT_INTO_SQL,
                TBL_EMPLOYEES,
                F_ID + "," + F_PERSONAL_INFO_ID + "," + F_ACCOUNT_ID + "," + F_DEPARTMENT_ID + "," + F_POSITION_ID + "," + F_INTERNAL_PHONE_NUMBER,
                "?, ?, ?, ?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareSalaryInsertOrUpdateStatments() throws SQLException {
        String sql = String.format(INSERT_OR_UPDATE_SQL, TBL_SALARIES, F_ID + "," + F_EMPLOYEE_ID + "," + F_SALARY, "?, ?, ?",
                F_EMPLOYEE_ID + " = ?, " + F_SALARY + " = ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareSalaryInsertStatments() throws SQLException {
        String sql = String.format(INSERT_INTO_SQL, TBL_SALARIES, F_ID + "," + F_EMPLOYEE_ID + "," + F_SALARY, "?, ?, ?");
        return connection.prepareStatement(sql);
    }

    private PreparedStatement prepareEmployeeSelectByIDStatement() throws SQLException {
        return connection.prepareStatement("SELECT * FROM " + VIEW_EMPLOYEES + " WHERE " + F_ID + " = ?");
    }

    private PreparedStatement prepareEmployeeSelectAllStatement() throws SQLException {
        return connection.prepareStatement("SELECT * FROM " + VIEW_EMPLOYEES + " ORDER BY " + F_ID + " DESC");
    }

    private CallableStatement prepareEmployeeDeletByIDStatement() throws SQLException {
        return connection.prepareCall("call " + String.format(SP_DELETE_EMPLOYEE_BY_ID, "?"));
    }

    private CallableStatement prepareEmployeeSelectAllWithMaxSalaryStatement() throws SQLException {
        return connection.prepareCall("call " + SP_GET_ALL_EMPLOYEES_WITH_MAX_SALARY);
    }

    private void prepareStatments() {
        try {
            employeePersonalInfoInsertStatement = prepareEmployeePersonalInfoInsertStatments();
            employeePersonalInfoInsertOrUpdateStatement = prepareEmployeePersonalInfoInsertOrUpdateStatments();

            employeeInsertStatement = prepareEmployeeInsertStatments();
            employeeInsertOrUpdateStatement = prepareEmployeeInsertOrUpdateStatments();

            salaryInsertStatement = prepareSalaryInsertStatments();
            salaryInsertOrUpdateStatement = prepareSalaryInsertOrUpdateStatments();

            employeeSelectAllStatement = prepareEmployeeSelectAllStatement();
            employeeSelectByIDStatement = prepareEmployeeSelectByIDStatement();

            employeeDeletByIDStatement = prepareEmployeeDeletByIDStatement();

            employeeSelectAllWithMaxSalaryStatement = prepareEmployeeSelectAllWithMaxSalaryStatement();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(EmployeePersonalInfo employeePersonalInfo) throws SQLException {
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

    @Override
    public void save(Employee employee) throws SQLException {
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


    @Override
    public void save(Salary salary) throws SQLException {
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

    private Employee parseEmployeeResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();

        Location location = new Location(rs.getLong(F_LOCATION_ID), rs.getNString(F_LOCATION_NAME));

        EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
        personalInfo.setId(rs.getLong(F_PERSONAL_INFO_ID));
        personalInfo.setLocation(location);
        personalInfo.setFirstName(rs.getString(F_FIRST_NAME));
        personalInfo.setMiddleName(rs.getString(F_MIDDLE_NAME));
        personalInfo.setSecondName(rs.getString(F_SECOND_NAME));
        personalInfo.setPersonalEMail(rs.getString(F_PERSONAL_EMAIL));

        AccountRole accountRole = new AccountRole(rs.getLong(F_ACCOUNTS_ROLE_ID), rs.getString(F_ROLE_NAME));
        Account account = new Account(rs.getLong(F_ACCOUNT_ID), rs.getString(F_LOGIN), rs.getString(F_PASSWORD), accountRole);

        Department department = new Department(rs.getLong(F_DEPARTMENT_ID), rs.getString(F_DEPARTMENT_NAME));
        Position position = new Position(rs.getLong(F_POSITION_ID), rs.getString(F_POSITION_NAME));
        Salary salary = new Salary(rs.getLong(F_SALARY_ID), employee, rs.getLong(F_SALARY));


        employee.setId(rs.getLong(F_ID));
        employee.setPersonalInfo(personalInfo);
        employee.setAccount(account);
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setInternalPhoneNumber(rs.getNString(F_INTERNAL_PHONE_NUMBER));
        employee.setSalary(salary);


        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();

        employeeSelectAllStatement.execute();
        ResultSet rs = employeeSelectAllStatement.getResultSet();
        while (rs.next()) {
            employees.add(parseEmployeeResultSet(rs));
        }

        return employees;
    }

    @Override
    public List<Employee> getAllEmployeesWithMaxSalary() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();

        employeeSelectAllWithMaxSalaryStatement.execute();
        ResultSet rs = employeeSelectAllWithMaxSalaryStatement.getResultSet();
        while (rs.next()) {
            employees.add(parseEmployeeResultSet(rs));
        }

        return employees;
    }

    @Override
    public Employee getOneEmployee(long id) throws SQLException {
        employeeSelectByIDStatement.setLong(1, id);
        employeeSelectByIDStatement.execute();
        ResultSet rs = employeeSelectByIDStatement.getResultSet();
        if (rs.next()) {
            return parseEmployeeResultSet(rs);
        }
        return null;
    }

    @Override
    public void deleteOneEmployee(long id) throws SQLException{
        employeeDeletByIDStatement.setLong(1, id);
        employeeDeletByIDStatement.execute();
    }



}
