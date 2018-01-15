package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCorporateInfo {
    private long id;
    private Employee employee;
    private Account account;
    private Department department;
    private Position position;
    private String internalPhoneNumber;
}
