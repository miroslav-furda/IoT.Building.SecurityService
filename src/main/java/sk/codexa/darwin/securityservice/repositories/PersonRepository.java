package sk.codexa.darwin.securityservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.codexa.darwin.securityservice.model.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByLogin(String login);
}
