package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponse> retrieveAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    public ClientResponse retrieveClient(Long idClient) {
        return clientMapper.toResponse(findClient(idClient));
    }

    @Override
    @Transactional
    public ClientResponse addClient(ClientRequest request) {
        Client client = clientMapper.toEntity(request);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ClientResponse updateClient(Long idClient, ClientRequest request) {
        Client client = findClient(idClient);
        clientMapper.updateEntity(client, request);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void removeClient(Long idClient) {
        clientRepository.delete(findClient(idClient));
    }

    private Client findClient(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
    }
}