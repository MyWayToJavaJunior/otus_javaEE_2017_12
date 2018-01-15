package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private long id;
    private Location location;
    private String firstName;
    private String secondName;
    private String middleName;
    private String personalEMail;
}
