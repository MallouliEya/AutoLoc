package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public List<AgenceResponse> retrieveAllAgences() {
        return StreamSupport.stream(agenceRepository.findAll().spliterator(), false)
                .map(agenceMapper::toResponse)
                .toList();
    }

    @Override
    public AgenceResponse retrieveAgence(Long idAgence) {
        return agenceMapper.toResponse(findAgence(idAgence));
    }

    @Override
    @Transactional
    public AgenceResponse addAgence(AgenceRequest request) {
        Agence agence = agenceMapper.toEntity(request);
        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public AgenceResponse updateAgence(Long idAgence, AgenceRequest request) {
        Agence agence = findAgence(idAgence);
        agenceMapper.updateEntity(agence, request);
        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public void removeAgence(Long idAgence) {
        agenceRepository.delete(findAgence(idAgence));
    }

    private Agence findAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }
}