import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Employee;
import entities.Salary;
import storage.interfaces.IDataStorageService;
import storage.db.MySQLDataStorageService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {


        IDataStorageService storageService = new MySQLDataStorageService();
        storageService.initStorage();
        storageService.importData();

        List<Employee> employees = storageService.getAllEmployeesWithMaxSalary();

        Gson gson = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass() == Salary.class && f.getName().equalsIgnoreCase("employee");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
        Type lsitType =  new TypeToken<ArrayList<Employee>>(){}.getType();
        String employeesJSON = gson.toJson(employees, lsitType);
        System.out.println(employeesJSON);

    }
}
