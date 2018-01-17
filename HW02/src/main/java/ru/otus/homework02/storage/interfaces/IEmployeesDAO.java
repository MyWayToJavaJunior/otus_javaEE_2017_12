package ru.otus.homework02.storage.interfaces;

import ru.otus.homework02.entities.Employee;
import ru.otus.homework02.entities.EmployeePersonalInfo;
import ru.otus.homework02.entities.Salary;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeesDAO {
    void save(EmployeePersonalInfo employeePersonalInfo) throws SQLException;

    void save(Employee employee) throws SQLException;

    void save(Salary salary) throws SQLException;

    List<Employee> getAllEmployees() throws SQLException;

    List<Employee> getAllEmployeesWithMaxSalary() throws SQLException;

    Employee getOneEmployee(long id) throws SQLException;

    void deleteOneEmployee(long id) throws SQLException;
}
