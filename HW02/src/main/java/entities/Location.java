package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private long id;
    private String name;

    public Location(long id) {
        this.id = id;
    }
}
