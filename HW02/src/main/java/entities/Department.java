package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {
    private long id;
    private String name;

    public Department(long id) {
        this.id = id;
    }
}
