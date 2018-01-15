package storage.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MySQLDataImporter {
    private final String DATA_FILE_ACCOUNTS_ROLES = "accounts_roles.json";
    private final String DATA_FILE_ACCOUNTS = "accounts.json";
    private final String DATA_FILE_LOCATIONS = "locations.json";
    private final String DATA_FILE_POSITIONS = "positions.json";
    private final String DATA_FILE_DEPARTMENTS = "departments.json";

    private MySQLConnectionFactory connectionFactory;
    private Gson gson;

    public MySQLDataImporter(MySQLConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.gson = new Gson();
    }

    private void importAccountsRoles(CatalogsDAO catalogsDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        List<AccountRole> roles = gson.fromJson(content, new TypeToken<ArrayList<AccountRole>>(){}.getType());
        for (AccountRole role: roles) {
            catalogsDAO.save(role);
        }
    }


    private void importAccounts(CatalogsDAO catalogsDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        JSONArray array = (JSONArray) (new JSONParser()).parse(content);

        for (Object o: array) {
            JSONObject jo = (JSONObject)o;
            Account account = new Account();
            account.setId(Long.valueOf((String)jo.get("id")));
            account.setRole(new AccountRole(Long.valueOf((String)jo.get("role_id")), ""));
            account.setLogin((String)jo.get("login"));
            account.setPassword((String)jo.get("password"));
            catalogsDAO.save(account);
        }
    }


    private void importLocations(CatalogsDAO catalogsDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        List<Location> locations = gson.fromJson(content, new TypeToken<ArrayList<Location>>(){}.getType());
        for (Location location: locations) {
            catalogsDAO.save(location);
        }
    }

    private void importPositions(CatalogsDAO catalogsDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        List<Position> positions = gson.fromJson(content, new TypeToken<ArrayList<Position>>(){}.getType());
        for (Position position: positions) {
            catalogsDAO.save(position);
        }
    }

    private void importDepartments(CatalogsDAO catalogsDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        List<Department> departments = gson.fromJson(content, new TypeToken<ArrayList<Department>>(){}.getType());
        for (Department department: departments) {
            catalogsDAO.save(department);
        }
    }


    public void importData(String dataDir) throws Exception {
        dataDir = new File(dataDir).toString();
        Connection connection = connectionFactory.getDBConnection();
        CatalogsDAO catalogsDAO = new CatalogsDAO(connection);

        importAccountsRoles(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_ACCOUNTS_ROLES).toString());
        importLocations(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_LOCATIONS).toString());
        importPositions(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_POSITIONS).toString());
        importDepartments(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_DEPARTMENTS).toString());
        importAccounts(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_ACCOUNTS).toString());
    }
}
