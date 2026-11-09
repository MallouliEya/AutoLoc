package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public List<VehiculeResponse> retrieveAllVehicules() {
        return StreamSupport.stream(vehiculeRepository.findAll().spliterator(), false)
                .map(vehiculeMapper::toResponse)
                .toList();
    }

    @Override
    public VehiculeResponse retrieveVehicule(Long idVehicule) {
        return vehiculeMapper.toResponse(findVehicule(idVehicule));
    }

    @Override
    @Transactional
    public VehiculeResponse addVehicule(VehiculeRequest request) {
        Vehicule vehicule = vehiculeMapper.toEntity(request);
        vehicule.setAgence(resolveAgence(request.idAgence()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeResponse updateVehicule(Long idVehicule, VehiculeRequest request) {
        Vehicule vehicule = findVehicule(idVehicule);
        vehiculeMapper.updateEntity(vehicule, request);
        vehicule.setAgence(resolveAgence(request.idAgence()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public void removeVehicule(Long idVehicule) {
        vehiculeRepository.delete(findVehicule(idVehicule));
    }

    private Vehicule findVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }

    private Agence resolveAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }
}