package sk.codexa.darwin.securityservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.codexa.darwin.securityservice.exceptions.UserAlreadyExistsException;
import sk.codexa.darwin.securityservice.exceptions.UserNotFoundException;
import sk.codexa.darwin.securityservice.model.Person;
import sk.codexa.darwin.securityservice.model.building.Building;
import sk.codexa.darwin.securityservice.services.UserService;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    private final UserService userService;

    @Autowired
    public PersonController(UserService userService) {
        this.userService = userService;
    }

    //@Secured({"Admin", "Tenant Admin"})
    @RequestMapping(value = "/createUser", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> addTenantAdmin(/*@AuthenticationPrincipal User user, */@RequestBody Person
            person) {

        //TODO check if user has rights to manipulate person

        if (userService.findByLogin(person.getLogin()).isPresent()){
            throw new UserAlreadyExistsException(person.getLogin());
        }

        Person newPerson = userService.addUser(person);
        if (newPerson == null) {
            return new ResponseEntity<>(new Response("failure"), UNAUTHORIZED);
        }

        return new ResponseEntity<>(new Response("success"), OK);
    }

    //@Secured({"Admin"})
    @RequestMapping(value = "/users", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Person>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }

    //@Secured({"Admin"})
    @RequestMapping(value = "/users/{login}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getUser(@PathVariable String login) {
        Optional<Person> person = userService.findByLogin(login);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    //@Secured({"Admin", "Tenant Admin", "Tenant", "Guest"})
    @RequestMapping(value = "/rights/{login}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Building> getRightsForUser(@PathVariable String login) {

        Optional<Person> person = userService.findByLogin(login);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get().getBuilding(), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    //@Secured({"Admin", "Tenant Admin"})
    @RequestMapping(value = "/deleteUser/{login}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteTenantAdmin(/*@AuthenticationPrincipal User user,*/ @PathVariable String
            login) {
        if (userService.findByLogin(login).isPresent()) {
            userService.delete(login);
            return new ResponseEntity<>(new Response("success"), OK);
        } else {
            throw new UserNotFoundException(login);
        }
    }
}
