package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Salary {
    private long id;
    private Employee employee;
    private long Salary;
}
