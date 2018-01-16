package storage.db;

import entities.Employee;
import entities.EmployeePersonalInfo;
import storage.IDataStorageService;

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
        databaseCreator.createTablesAndViews();
    }

    @Override
    public void saveEmployee(Employee employee) throws SQLException {
        EmployeesDAO employeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        employeesDAO.save(employee);
    }

    @Override
    public void importData() throws Exception {
        dataImporter.importData(classLoader.getResource(INITIAL_DATA_DIR).getFile());
    }

    @Override
    public Employee getEmployee(long id) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        EmployeesDAO employeesDAO = new EmployeesDAO(connectionFactory.getDBConnection());
        return employeesDAO.getAll();
    }
}
