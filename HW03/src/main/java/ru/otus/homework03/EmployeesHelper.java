package ru.otus.homework03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xml.sax.InputSource;
import ru.otus.homework03.entities.Employee;
import ru.otus.homework03.entities.EmployeePersonalInfo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

public class EmployeesHelper {

    private static final String FILE_EMPLOYEES_XML = "./employees.xml";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlRootElement(name = "employees")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class EmployeesRoot {
        @XmlElement(name = "employee")
        private List<Employee> employees;
    }

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

    public static void saveDataToXMLFile(List<Employee> employees) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(EmployeesRoot.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        m.marshal(new EmployeesRoot(employees), new File(FILE_EMPLOYEES_XML));
    }

    public static List<Employee> loadDataFromXMLFile() throws IOException, XPathExpressionException {

        InputSource inputSource = new InputSource(Files.newInputStream(Paths.get(FILE_EMPLOYEES_XML)));
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile("");
        String result = expr.evaluate(inputSource);

        return null;
    }

}