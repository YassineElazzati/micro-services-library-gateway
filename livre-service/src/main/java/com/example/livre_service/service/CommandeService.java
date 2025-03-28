package com.example.livre_service.service;

import com.example.livre_service.entity.Commande;

import java.util.List;

public interface CommandeService {
    Commande save(Commande commande);
    List<Commande> getAll();
    Commande getById(Long id);
    Commande update(Long id, Commande commande);
    void delete(Long id);
}
