package ru.otus.homework03;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.otus.homework03.entities.Employee;
import ru.otus.homework03.entities.EmployeePersonalInfo;
import ru.otus.homework03.xml.EmployeesRoot;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
1. Создание XML

Используя технологию JAXB, произвести процедуру маршалинга списка объектов класса Employee во внешний файл employees.xml.
Рекомендуется использовать отдельный сервлет MarshalXMLServlet.


2. Поиск XPath.

Считав содержимое xml-документа employee.xml в объект org.w3c.dom.Document или org.xml.sax.InputSource, необходимо найти все xml-теги сотрудников, у которых зарплата превышает среднее значение.
Допустимо логику описать в том же сервлете MarshalXMLServlet либо создать запускаемый класс.


3. Конвертация XML в JSON

Используя возможности библиотеки JSON или любой другой, преобразовать содержимое файла empolyee.xml в JSON-формат.
Результат вывести в качестве ответа сервлета, одновременно сохранив в виде внешнего файла employees.json.


4. Отображение внешнего JSON-файла на объектную модель.

Используя содержимое employees.json и технологию JSON-B, получить список объектов Employee и вывести информацию о сотрудниках с нечетными индексами в списке.
Результат вывести в качестве ответа сервлета.

 */

public class HomeWork03Helper {

    private static final String FILES_LOCATION = "./src/main/webapp/files/";
    public static final String FILE_EMPLOYEES_XML = FILES_LOCATION + "employees.xml";
    public static final String FILE_EMPLOYEES_XSD = FILES_LOCATION + "employees.xsd";
    public static final String FILE_EMPLOYEES_JSON = FILES_LOCATION + "employees.json";

    private static final String EMPLOYEES_AVG_SALARY_XPATH_TEXT = "/employees/employee[*]/salary/text()";
    private static final String EMPLOYEES_WHO_HAVE_SALARY_HIGHER_THAN_AVERAGE_XPATH_TEXT = "/employees/employee[number(salary/text()) > %d]";


    public static List<Employee> initData() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1975-10-11"), "Всеволод", "Андреевич", "Перебейнос", "Саратов"), "Сантехник", "Поволжье", 5000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1978-06-14"), "Жинтынбек", "Курганович", "Степь", "Саратов"), "Плотник", "Поволжье", 10000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1983-03-09"), "Василий", "Илларионович", "Каптча", "Саратов"), "Программист", "Поволжье", 20000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1985-09-24"), "Иван", "Сидоревич", "Петров", "Самара"), "Кадровый работник", "Поволжье", 100000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1987-05-17"), "Геннадий", "Акинфеевич", "Пульт", "Самара"), "Кадровый работник", "Поволжье", 100000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1971-07-03"), "Феодосия", "Игоревна", "Резерфорд", "Москва"), "Главный бухгалтер", "Центральный", 700000));
        employees.add(new Employee(new EmployeePersonalInfo(LocalDate.parse("1967-01-01"), "Франсуа", "Людвикович", "Гугенот", "Москва"), "Генеральный директор", "Центральный", 1000000));

        return employees;
    }

    // Первый пункт
    public static boolean saveDataToXMLFile(List<Employee> employees, String xmlFileName) {
        File xmlFile =  new File(xmlFileName);
        if (xmlFile.exists()){
            if (!xmlFile.delete()) {
                return false;
            }
        }

        try {
            JAXBContext context = JAXBContext.newInstance(EmployeesRoot.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(new EmployeesRoot(employees), xmlFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createXSDSchema() {

    }

    public static boolean validateXMLFile() {
        createXSDSchema();

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        boolean result = false;
        try {
            Schema schema = factory.newSchema(new File(FILE_EMPLOYEES_XSD));
            Validator validator = schema.newValidator();
            //validator.validate(new StreamSource(Files.newInputStream(Paths.get(FILE_EMPLOYEES_XML))));
            validator.validate(new StreamSource(FILE_EMPLOYEES_XML));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Второй пункт ч. 1
    private static long getAvgSalaryFromXMLFile() throws Exception {
        InputSource inputSource = new InputSource(Files.newInputStream(Paths.get(FILE_EMPLOYEES_XML)));
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(EMPLOYEES_AVG_SALARY_XPATH_TEXT);
        NodeList nodeList = (NodeList) expr.evaluate(inputSource, XPathConstants.NODESET);
        if (nodeList.getLength() == 0) return 0;

        long sum = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            sum += Long.parseLong(nodeList.item(i).getNodeValue());
        }
        return sum / nodeList.getLength();
    }

    // Второй пункт ч. 2
    public static List<Employee> loadDataForEmployeesWhoHaveSalaryHigherThanAverageFromXMLFile() throws Exception {
        List<Employee> employees = new ArrayList<>();

        long avgSalary = getAvgSalaryFromXMLFile();

        JAXBContext context = JAXBContext.newInstance(Employee.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputSource inputSource = new InputSource(Files.newInputStream(Paths.get(FILE_EMPLOYEES_XML)));

        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(String.format(EMPLOYEES_WHO_HAVE_SALARY_HIGHER_THAN_AVERAGE_XPATH_TEXT, avgSalary));

        NodeList nodeList = (NodeList) expr.evaluate(inputSource, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Employee employee = (Employee) unmarshaller.unmarshal(nodeList.item(i));
            employees.add(employee);
        }

        return employees;
    }

    // Третий пункт
    public static void convertXMLFileToJSONFile() throws Exception{
        JAXBContext context = JAXBContext.newInstance(EmployeesRoot.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputSource inputSource = new InputSource(Files.newInputStream(Paths.get(FILE_EMPLOYEES_XML)));
        EmployeesRoot employees = (EmployeesRoot) unmarshaller.unmarshal(inputSource);

        JsonbConfig config = new JsonbConfig();
        config.withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        jsonb.toJson(employees, EmployeesRoot.class, Files.newOutputStream(Paths.get(FILE_EMPLOYEES_JSON)));
    }

    // Четвертый пункт
    public static List<Employee> getListOfOddEmployeesFromJSONFile() throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        EmployeesRoot employees = jsonb.fromJson(Files.newInputStream(Paths.get(FILE_EMPLOYEES_JSON)), EmployeesRoot.class);
        List<Employee> oddEmployes = IntStream.range(0, employees.getEmployees().size()).filter(i -> i % 2 != 0)
                .mapToObj(i -> employees.getEmployees().get(i))
                .collect(Collectors.toList());
        return oddEmployes;
    }

}