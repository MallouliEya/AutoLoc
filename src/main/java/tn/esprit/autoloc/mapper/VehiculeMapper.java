package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequest request) {
        Vehicule vehicule = new Vehicule();
        updateEntity(vehicule, request);
        return vehicule;
    }

    /**
     * Copie les champs simples. L'agence est résolue par le service
     * (elle nécessite un accès au repository).
     */
    public void updateEntity(Vehicule vehicule, VehiculeRequest request) {
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
    }

    public VehiculeResponse toResponse(Vehicule vehicule) {
        Long idAgence = vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null;
        return new VehiculeResponse(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                idAgence);
    }
}