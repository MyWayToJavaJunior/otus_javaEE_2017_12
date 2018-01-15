package storage;

import entities.Employee;

import java.io.IOException;
import java.util.List;

public interface IDataStorageService {
    void initStorage();

    void saveEmployee(Employee employee);

    void importData() throws Exception;

    Employee getEmployee(long id);
    List<Employee> getAllEmployees();

}
