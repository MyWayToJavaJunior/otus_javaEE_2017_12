package ru.otus.homework02.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long id;
    private EmployeePersonalInfo personalInfo;
    private Account account;
    private Department department;
    private Position position;
    private String internalPhoneNumber;

    private Salary salary;

    public Employee(long id) {
        this.id = id;
    }
}
