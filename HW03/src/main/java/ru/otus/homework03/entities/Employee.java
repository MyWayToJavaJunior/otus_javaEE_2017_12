package ru.otus.homework03.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private EmployeePersonalInfo personalInfo;
    private String position;
    private String department;
    private long salary;
}
