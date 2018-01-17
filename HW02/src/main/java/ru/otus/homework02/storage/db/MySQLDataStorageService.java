package ru.otus.homework02.storage.db;

import ru.otus.homework02.entities.Employee;
import ru.otus.homework02.entities.EmployeePersonalInfo;
import ru.otus.homework02.entities.Salary;
import ru.otus.homework02.storage.interfaces.IDataStorageService;
import ru.otus.homework02.storage.interfaces.IEmployeesDAO;

import java.sql.SQLException;
import java.util.List;

public class MySQLDataStorageService implements IDataStorageService {

    private static final String DBSETTINGS_PROPERTY_FILE = "dbsettings.property";
    private static final String INITIAL_DATA_DIR = "initial_data/";

    private ClassLoader classLoader;

    private MySQLConnectionFactory connectionFactory;
    private MySQLDatabaseCreator databaseCreator;
    private MySQLDataImporter dataImporter;

    public MySQLDataStorageService() {
        classLoader = MySQLDataStorageService.class.getClassLoader();

        this.connectionFactory = new MySQLConnectionFactory();
        connectionFactory.loadConfigurationProperties(classLoader.getResource(DBSETTINGS_PROPERTY_FILE).getFile());

        this.databaseCreator = new MySQLDatabaseCreator(connectionFactory);
        this.dataImporter = new MySQLDataImporter(connectionFactory);
    }

    @Override
    public void initStorage() {
        databaseCreator.createDataBase();
        databaseCreator.createDatabaseStructure();
    }

    @Override
    public void saveEmployeePersonalInfo(EmployeePersonalInfo personalInfo) throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        IEmployeesDAO.save(personalInfo);
    }

    @Override
    public void saveEmployeeSalary(Salary salary) throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        IEmployeesDAO.save(salary);
    }

    @Override
    public void saveEmployee(Employee employee) throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        IEmployeesDAO.save(employee);
    }

    @Override
    public void importData() throws Exception {
        dataImporter.importData(classLoader.getResource(INITIAL_DATA_DIR).getFile());
    }

    @Override
    public Employee getOneEmployee(long id) throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        return IEmployeesDAO.getOneEmployee(id);
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        return IEmployeesDAO.getAllEmployees();
    }

    @Override
    public List<Employee> getAllEmployeesWithMaxSalary() throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        return IEmployeesDAO.getAllEmployeesWithMaxSalary();
    }

    @Override
    public void deleteOneEmployee(long id) throws SQLException {
        IEmployeesDAO IEmployeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        IEmployeesDAO.deleteOneEmployee(id);
    }
}
