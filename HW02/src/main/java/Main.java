import storage.db.MySQLConnectionFactory;
import storage.db.MySQLDataStorageService;

public class Main {

    private static final String DBSETTINGS_PROPERTY_FILE = "dbsettings.property";

    public static void main(String[] args) {
        MySQLConnectionFactory factory = new MySQLConnectionFactory();
        factory.loadConfigurationProperties(Main.class.getClassLoader().getResource(DBSETTINGS_PROPERTY_FILE).getFile());

        MySQLDataStorageService storageService = new MySQLDataStorageService(factory);
        storageService.initStorage();
    }
}
