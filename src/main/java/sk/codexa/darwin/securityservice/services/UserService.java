package sk.codexa.darwin.securityservice.services;

import sk.codexa.darwin.securityservice.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Person addUser(Person person);
    Optional<Person> login(String login, String password);
    Optional<Person> findByLogin(String login);
    Collection<Person> getAllUsers();
    void delete(String login);
}
