package entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRole {
    private long id;
    private String name;

    public AccountRole(long id) {
        this.id = id;
    }
}
