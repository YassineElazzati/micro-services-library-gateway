package com.example.livre_service.service.impl;

import com.example.livre_service.entity.Client;
import com.example.livre_service.repository.ClientRepository;
import com.example.livre_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public Client update(Long id, Client client) {
        Client existing = getById(id);
        existing.setNom(client.getNom());
        existing.setEmail(client.getEmail());
        return clientRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
