package storage;

import entities.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IDataStorageService {
    void initStorage();

    void saveEmployee(Employee employee) throws SQLException;

    void importData() throws Exception;

    Employee getEmployee(long id);
    List<Employee> getAllEmployees() throws SQLException;

}
