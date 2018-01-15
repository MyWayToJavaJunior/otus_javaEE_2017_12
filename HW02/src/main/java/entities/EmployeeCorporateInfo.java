package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCorporateInfo {
    private long id;
    private long accountId;
    private Employee employee;
    private Department department;
    private Position position;
    private String internalPhoneNumber;
}
