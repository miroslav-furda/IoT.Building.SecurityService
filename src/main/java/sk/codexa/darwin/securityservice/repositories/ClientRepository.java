package sk.codexa.darwin.securityservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.codexa.darwin.securityservice.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
