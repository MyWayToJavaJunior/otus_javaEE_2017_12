package storage.interfaces;

import entities.Employee;
import entities.EmployeePersonalInfo;
import entities.Salary;

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
