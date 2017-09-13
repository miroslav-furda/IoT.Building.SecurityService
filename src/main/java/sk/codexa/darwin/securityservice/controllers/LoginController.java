package sk.codexa.darwin.securityservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.codexa.darwin.securityservice.services.UserService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> login(/*@RequestBody User user*/) {
       /* Optional<Person> person = userService.login(user.getUsername(), user.getPassword());

        if (person == null) {
            return new ResponseEntity<>(new Response("Failure"), UNAUTHORIZED);
        }*/

        return new ResponseEntity<>(new Response("Success"), OK);
    }
}
