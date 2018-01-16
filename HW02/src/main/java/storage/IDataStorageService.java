package storage;

import entities.EmployeePersonalInfo;

import java.util.List;

public interface IDataStorageService {
    void initStorage();

    void saveEmployee(EmployeePersonalInfo employee);

    void importData() throws Exception;

    EmployeePersonalInfo getEmployee(long id);
    List<EmployeePersonalInfo> getAllEmployees();

}
