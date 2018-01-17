package ru.otus.homework02.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePersonalInfo {
    private long id;
    private Location location;
    private String firstName;
    private String secondName;
    private String middleName;
    private String personalEMail;

    public EmployeePersonalInfo(long id) {
        this.id = id;
    }
}
