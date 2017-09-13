package sk.codexa.darwin.securityservice.model.building;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Building implements Serializable {
    @GeneratedValue
    @Id
    private Long id;
    @OneToMany(cascade = ALL, fetch = EAGER)
    private Set<Floor> floors;
    private String buildingName;

    public Building(Set<Floor> floors, String buildingName) {
        this.floors = floors;
        this.buildingName = buildingName;
    }
}
