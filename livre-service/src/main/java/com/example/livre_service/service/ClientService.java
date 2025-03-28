package com.example.livre_service.service;

import com.example.livre_service.entity.Client;

import java.util.List;

public interface ClientService {
    Client save(Client client);
    List<Client> getAll();
    Client getById(Long id);
    Client update(Long id, Client client);
    void delete(Long id);
}
