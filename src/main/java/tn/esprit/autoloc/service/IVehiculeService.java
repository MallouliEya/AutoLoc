package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

import java.util.List;

public interface IVehiculeService {

    List<VehiculeResponse> retrieveAllVehicules();

    VehiculeResponse retrieveVehicule(Long idVehicule);

    VehiculeResponse addVehicule(VehiculeRequest request);

    VehiculeResponse updateVehicule(Long idVehicule, VehiculeRequest request);

    void removeVehicule(Long idVehicule);
}