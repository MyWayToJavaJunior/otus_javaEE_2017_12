package ru.otus.homework03.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.entities.Employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static ru.otus.homework03.xml.XMLConsts.ELEMENT_EMPLOYEE;
import static ru.otus.homework03.xml.XMLConsts.ELEMENT_EMPLOYEES;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = ELEMENT_EMPLOYEES)
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesRoot {
    @XmlElement(name = ELEMENT_EMPLOYEE)
    private List<Employee> employees;
}
