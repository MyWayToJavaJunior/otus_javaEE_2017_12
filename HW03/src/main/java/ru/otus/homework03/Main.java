package ru.otus.homework03;

import ru.otus.homework03.entities.Employee;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {




        List<Employee> employees = HomeWork03Helper.initData();
        HomeWork03Helper.saveDataToXMLFile(employees, HomeWork03Helper.FILE_EMPLOYEES_XML);
        //List<Employee> employees2 = HomeWork03Helper.loadDataForEmployeesWhoHaveSalaryHigherThanAverageFromXMLFile();

        HomeWork03Helper.convertXMLFileToJSONFile();
        //List<Employee> employeesOdd = HomeWork03Helper.getListOfOddEmployeesFromJSONFile();
        //System.out.println(employeesOdd);

        //HomeWork03Helper.createXSDSchema();
    }
}
