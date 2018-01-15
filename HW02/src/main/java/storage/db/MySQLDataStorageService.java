package storage.db;

import entities.Employee;
import storage.IDataStorageService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySQLDataStorageService implements IDataStorageService {

    private static final String DBSETTINGS_PROPERTY_FILE = "dbsettings.property";
    private static final String INITIAL_DATA_DIR = "initial_data/";

    private ClassLoader classLoader;

    private MySQLConnectionFactory connectionFactory;
    private MySQLDatabaseCreator databaseCreator;
    private MySQLDataImporter dataImporter;

    CatalogsDAO catalogsDAO;
    EmployeesDAO employeesDAO;

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
        databaseCreator.createTables();
    }

    @Override
    public void saveEmployee(Employee employee) {

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
    public List<Employee> getAllEmployees() {
        return null;
    }
}
