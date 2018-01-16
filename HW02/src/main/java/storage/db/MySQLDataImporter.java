package storage.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
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
    private final String DATA_FILE_EMPLOYEES_PERSONAL_INFO = "employees_presonal_info.json";
    private final String DATA_FILE_EMPLOYEES = "employees.json";
    private final String DATA_FILE_SALARIES = "salaries.json";

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
            account.setRole(new AccountRole(Long.valueOf((String)jo.get("role_id"))));
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


    private void importEmployeesPersonalInfo(EmployeesDAO employeesDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        JSONArray array = (JSONArray) (new JSONParser()).parse(content);

        for (Object o: array) {
            JSONObject jo = (JSONObject)o;
            EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
            personalInfo.setId(Long.valueOf((String)jo.get("id")));
            personalInfo.setLocation(new Location(Long.valueOf((String)jo.get("location_id"))));
            personalInfo.setFirstName((String)jo.get("first_name"));
            personalInfo.setMiddleName((String)jo.get("middle_name"));
            personalInfo.setSecondName((String)jo.get("second_name"));
            personalInfo.setPersonalEMail((String)jo.get("personal_email"));
            employeesDAO.save(personalInfo);
        }
    }

    private void importEmployees(EmployeesDAO employeesDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        JSONArray array = (JSONArray) (new JSONParser()).parse(content);

        for (Object o: array) {
            JSONObject jo = (JSONObject)o;
            Employee employee = new Employee();
            employee.setId(Long.valueOf((String)jo.get("id")));
            employee.setPersonalInfo(new EmployeePersonalInfo(Long.valueOf((String)jo.get("personal_info_id"))));
            employee.setAccount(new Account(Long.valueOf((String)jo.get("account_id"))));
            employee.setDepartment(new Department(Long.valueOf((String)jo.get("department_id"))));
            employee.setPosition(new Position(Long.valueOf((String)jo.get("position_id"))));
            employee.setInternalPhoneNumber((String)jo.get("internal_phone_number"));
            employeesDAO.save(employee);
        }
    }

    private void importSalaries(EmployeesDAO employeesDAO, String datafile) throws Exception {
        String content = new String(Files.readAllBytes((new File(datafile)).toPath()));
        JSONArray array = (JSONArray) (new JSONParser()).parse(content);

        for (Object o: array) {
            JSONObject jo = (JSONObject)o;
            Salary salary = new Salary();
            salary.setId(Long.valueOf((String)jo.get("id")));
            salary.setEmployee(new EmployeePersonalInfo(Long.valueOf((String)jo.get("employee_id"))));
            salary.setSalary(Long.valueOf((String)jo.get("salary")));
            employeesDAO.save(salary);
        }
    }

    public void importData(String dataDir) throws Exception {
        dataDir = new File(dataDir).toString();
        Connection connection = connectionFactory.getDBConnection();
        CatalogsDAO catalogsDAO = new CatalogsDAO(connection);
        EmployeesDAO employeesDAO = new EmployeesDAO(connection);

        importAccountsRoles(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_ACCOUNTS_ROLES).toString());
        importLocations(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_LOCATIONS).toString());
        importPositions(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_POSITIONS).toString());
        importDepartments(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_DEPARTMENTS).toString());
        importAccounts(catalogsDAO, Paths.get(dataDir).resolve(DATA_FILE_ACCOUNTS).toString());

        importEmployeesPersonalInfo(employeesDAO, Paths.get(dataDir).resolve(DATA_FILE_EMPLOYEES_PERSONAL_INFO).toString());
        importEmployees(employeesDAO, Paths.get(dataDir).resolve(DATA_FILE_EMPLOYEES).toString());
        importSalaries(employeesDAO, Paths.get(dataDir).resolve(DATA_FILE_SALARIES).toString());

    }
}
