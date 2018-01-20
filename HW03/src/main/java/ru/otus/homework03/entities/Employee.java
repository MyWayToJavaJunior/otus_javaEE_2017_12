package ru.otus.homework03.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static ru.otus.homework03.xml.XMLConsts.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = ELEMENT_EMPLOYEE)
public class Employee {
    @XmlElement(name = ELEMENT_PERSONAL_INFO)
    private EmployeePersonalInfo personalInfo;

    @XmlElement(name = ELEMENT_POSITION)
    private String position;

    @XmlElement(name = ELEMENT_DEPARTMENT)
    private String department;

    @XmlElement(name = ELEMENT_SALARY)
    private long salary;
}
