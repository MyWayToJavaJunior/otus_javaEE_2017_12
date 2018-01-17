package ru.otus.homework02.storage.interfaces;

import ru.otus.homework02.entities.Employee;
import ru.otus.homework02.entities.EmployeePersonalInfo;
import ru.otus.homework02.entities.Salary;

import java.sql.SQLException;
import java.util.List;

public interface IDataStorageService {
    void initStorage();

    void saveEmployeePersonalInfo(EmployeePersonalInfo personalInfo) throws SQLException;
    void saveEmployeeSalary(Salary salary) throws SQLException;
    void saveEmployee(Employee employee) throws SQLException;

    void importData() throws Exception;

    Employee getOneEmployee(long id) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;

    List<Employee> getAllEmployeesWithMaxSalary() throws SQLException;

    void deleteOneEmployee(long id) throws SQLException;

}
