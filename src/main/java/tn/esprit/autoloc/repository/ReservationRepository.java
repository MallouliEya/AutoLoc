package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Vehicule;

public interface ReservationRepository extends CrudRepository<Vehicule, Long> {
}