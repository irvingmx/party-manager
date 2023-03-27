package irvingmx.microservices.friend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Friend {
    @Id
    private Long friendId;
    private String name;
    private String gift;

}
