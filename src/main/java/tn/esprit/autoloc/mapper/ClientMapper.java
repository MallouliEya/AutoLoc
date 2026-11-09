package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;
import java.time.ZoneId;

import java.time.LocalDate;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        updateEntity(client, request);
        if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now(ZoneId.of("Africa/Tunis")));
        }
        return client;
    }

    public void updateEntity(Client client, ClientRequest request) {
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
        if (request.dateInscription() != null) {
            client.setDateInscription(request.dateInscription());
        }
    }

    public ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription());
    }
}