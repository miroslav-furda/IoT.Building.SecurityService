package sk.codexa.darwin.securityservice.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import sk.codexa.darwin.securityservice.model.Person;
import sk.codexa.darwin.securityservice.model.building.Building;
import sk.codexa.darwin.securityservice.model.building.Department;
import sk.codexa.darwin.securityservice.model.building.Floor;
import sk.codexa.darwin.securityservice.model.building.Office;
import sk.codexa.darwin.securityservice.repositories.PersonRepository;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static sk.codexa.darwin.securityservice.model.Role.*;

/**
 * TODO remove -> serves to fill temporary, in memory h2 database
 */
@Component
public class TempDatabaseInitializer {

    private final PersonRepository personRepository;

    @Autowired
    public TempDatabaseInitializer(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void createTempUsers() {
        personRepository.save(new Person(ADMIN, "Jaro", encodePassword("12345"), createBuilding("building1")));

        personRepository.save(new Person(TENANT_ADMIN, "Peter", encodePassword("12345"), createBuilding("building1")));
        personRepository.save(new Person(TENANT_ADMIN, "Marian", encodePassword("12345"), createBuilding("building1")));
        personRepository.save(new Person(TENANT_ADMIN, "Lukas", encodePassword("12345"), createBuilding("building1")));
        personRepository.save(new Person(TENANT_ADMIN, "Miro", encodePassword("12345"), createBuilding("building1")));

        personRepository.save(new Person(TENANT, "Miso", encodePassword("12345"), createBuilding("building1")));
        personRepository.save(new Person(GUEST, "Zuzana", encodePassword("12345"), createBuilding("building1")));
    }

    private String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    private Building createBuilding(String name) {
        return new Building(new HashSet<>(asList(createFloor("floor1"), createFloor(
                "floor2"))), name);
    }

    private Floor createFloor(String name) {
        return new Floor(new HashSet<>(asList(createDepartment("department1"), createDepartment(
                "department2"))), name);
    }

    private Department createDepartment(String name) {
        return new Department(new HashSet<>(asList(createOffice("office1"), createOffice("office2"))),
                name);
    }

    private Office createOffice(String name) {
        return new Office(name);
    }
}
