package ru.otus.homework03;

import ru.otus.homework03.entities.Employee;

import javax.xml.bind.JAXBException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JAXBException {


        //Jsonb jsonb = JsonbBuilder.create();
        //Type listType = new ArrayList<Employee>() {}.getClass().getGenericSuperclass();
        //List<Employee> employees = jsonb.fromJson(Main.class.getClassLoader().getResourceAsStream("employees.json"), listType);

        //List<Employee> oddEmployes = IntStream.range(0, employees.size()).filter(i -> i % 2 != 0).mapToObj(i -> employees.get(i)).collect(Collectors.toList());


        List<Employee> employees = EmployeesHelper.initData();
        EmployeesHelper.saveDataToXMLFile(employees);



        System.out.println();
    }
}
