package sk.codexa.darwin.securityservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.codexa.darwin.securityservice.model.Client;
import sk.codexa.darwin.securityservice.repositories.ClientRepository;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ClientController {

    //TODO add service layer when business logic increases
    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Endpoint for Admin, to see all clients authorized to use resources managed by this authorization server.
     * @return
     */
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/clients", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientRepository.findAll(), OK);
    }
}
