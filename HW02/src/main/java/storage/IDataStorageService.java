package storage;

import entities.Employee;

import java.util.List;

public interface IDataStorageService {
    void initStorage();

    void saveEmployee();
    void importEmployees();

    Employee getEmployee(long id);
    List<Employee> getAllEmployees();

}
