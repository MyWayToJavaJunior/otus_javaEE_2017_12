package ru.otus.homework03.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePersonalInfo {
    private LocalDate birthDate;

    private String firstName;
    private String middleName;
    private String secondName;

    private String location;

}
