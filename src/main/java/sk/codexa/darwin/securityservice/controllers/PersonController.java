package sk.codexa.darwin.securityservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.codexa.darwin.securityservice.exceptions.UserAlreadyExistsException;
import sk.codexa.darwin.securityservice.exceptions.UserNotFoundException;
import sk.codexa.darwin.securityservice.model.Person;
import sk.codexa.darwin.securityservice.model.building.Building;
import sk.codexa.darwin.securityservice.services.UserService;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final UserService userService;

    @Autowired
    public PersonController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_TENANT_ADMIN"})
    @RequestMapping(value = "/user", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@AuthenticationPrincipal User user, @RequestBody Person
            person) {

        //TODO check if user has rights to manipulate person

        if (userService.findByLogin(person.getLogin()).isPresent()){
            throw new UserAlreadyExistsException(person.getLogin());
        }

        userService.addUser(person);

        return new ResponseEntity<>(user, OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_TENANT_ADMIN"})
    @RequestMapping(value = "/users", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Person>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_TENANT_ADMIN"})
    @RequestMapping(value = "/users/{login}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getUser(@PathVariable String login) {
        Optional<Person> person = userService.findByLogin(login);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_TENANT_ADMIN"})
    @RequestMapping(value = "/rights/{login}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Building> getRightsForUser(@PathVariable String login) {

        Optional<Person> person = userService.findByLogin(login);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get().getBuilding(), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/user/{login}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteUser(@AuthenticationPrincipal User user, @PathVariable String
            login) {
        if (userService.findByLogin(login).isPresent()) {
            userService.delete(login);
            return new ResponseEntity<>(new Response("success"), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }
}
