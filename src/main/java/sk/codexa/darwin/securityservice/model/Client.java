package sk.codexa.darwin.securityservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String clientName;
    private String secret;
    @ElementCollection(fetch = EAGER)
    private Set<String> scopes;
    @ElementCollection(fetch = EAGER)
    private Set<String> grantTypes;

    public Client(String clientName, String secret, Set<String> scopes, Set<String> grantTypes) {
        this.clientName = clientName;
        this.secret = secret;
        this.scopes = scopes;
        this.grantTypes = grantTypes;
    }
}
