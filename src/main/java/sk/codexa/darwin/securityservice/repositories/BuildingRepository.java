package sk.codexa.darwin.securityservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.codexa.darwin.securityservice.model.building.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
