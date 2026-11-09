package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;

import java.util.List;

public interface IAgenceService {

    List<AgenceResponse> retrieveAllAgences();

    AgenceResponse retrieveAgence(Long idAgence);

    AgenceResponse addAgence(AgenceRequest request);

    AgenceResponse updateAgence(Long idAgence, AgenceRequest request);

    void removeAgence(Long idAgence);
}