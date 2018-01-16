package entities;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    private long id;

    @Expose(serialize = false, deserialize = false)
    private Employee employee;

    private long Salary;
}
