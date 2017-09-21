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
   // private final UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(PersonRepository personRepository/*, UserDetailsService userDetailsService*/) {
        this.personRepository = personRepository;
       // this.userDetailsService = userDetailsService;
    }

    @Override
    public Person addUser(Person person) {
        //TODO find better solution
        /*if (userDetailsService instanceof InMemoryUserDetailsManager){
            InMemoryUserDetailsManager inMemory = (InMemoryUserDetailsManager) userDetailsService;
            inMemory.createUser(withUsername(person.getLogin()).password
                    (person.getPassword()).roles(person.getRole().toString()).build());
        }*/
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
