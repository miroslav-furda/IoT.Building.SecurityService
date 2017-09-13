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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Department implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(cascade = ALL, fetch = EAGER)
    private Set<Office> offices;
    private String departmentName;

    public Department(Set<Office> offices, String departmentName) {
        this.offices = offices;
        this.departmentName = departmentName;
    }
}
