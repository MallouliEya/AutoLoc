package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

import java.util.List;

public interface IClientService {

    List<ClientResponse> retrieveAllClients();

    ClientResponse retrieveClient(Long idClient);

    ClientResponse addClient(ClientRequest request);

    ClientResponse updateClient(Long idClient, ClientRequest request);

    void removeClient(Long idClient);
}