package sk.codexa.darwin.securityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import sk.codexa.darwin.securityservice.model.Person;
import sk.codexa.darwin.securityservice.repositories.PersonRepository;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserServiceImpl implements UserService {

    private final PersonRepository personRepository;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PersonRepository personRepository, InMemoryUserDetailsManager inMemoryUserDetailsManager,
                           PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Person addUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        inMemoryUserDetailsManager.createUser(withUsername(person.getLogin()).password
                (person.getPassword()).roles(person.getRole().toString()).build());


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
        person.ifPresent(p -> {
            inMemoryUserDetailsManager.deleteUser(p.getLogin());
            personRepository.delete(p);
        });
    }
}
