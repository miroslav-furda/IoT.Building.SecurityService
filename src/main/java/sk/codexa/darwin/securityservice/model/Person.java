package sk.codexa.darwin.securityservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sk.codexa.darwin.securityservice.model.building.Building;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.ALL;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Role role;

    @Column(unique = true)
    private String login;
    @JsonIgnore
    private String password;

    @OneToOne(cascade = ALL)
    private Building building;

    public Person(Role role, String login, String password, Building building) {
        this.role = role;
        this.login = login;
        this.building = building;
        this.password = password;
    }


}
