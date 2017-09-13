package sk.codexa.darwin.securityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.codexa.darwin.securityservice.model.Person;
import sk.codexa.darwin.securityservice.repositories.PersonRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PersonRepository personRepository;

    @Autowired
    public UserServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person addUser(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> login(String login, String password) {
        Optional<Person> person = personRepository.findByLogin(login);

        return person.filter(p -> password.equals(p.getPassword()));
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    @Override
    public Collection<Person> getAllUsers() {
        return personRepository.findAll();
    }

    @Override
    public void delete(String login) {
        Optional<Person> person = personRepository.findByLogin(login);
        person.ifPresent(personRepository::delete);
    }
}
