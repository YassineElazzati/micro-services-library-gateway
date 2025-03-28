package com.example.livre_service.service.impl;

import com.example.livre_service.entity.Commande;
import com.example.livre_service.repository.CommandeRepository;
import com.example.livre_service.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    @Override
    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }

    @Override
    public List<Commande> getAll() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande getById(Long id) {
        return commandeRepository.findById(id).orElseThrow();
    }

    @Override
    public Commande update(Long id, Commande commande) {
        Commande existing = getById(id);
        existing.setProduit(commande.getProduit());
        existing.setMontant(commande.getMontant());
        existing.setClient(commande.getClient());
        return commandeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        commandeRepository.deleteById(id);
    }
}
