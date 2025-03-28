package com.example.livre_service.controller;

import com.example.livre_service.entity.Commande;
import com.example.livre_service.service.CommandeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public Commande create(@RequestBody Commande commande) {
        return commandeService.save(commande);
    }

    @GetMapping
    public List<Commande> getAll() {
        return commandeService.getAll();
    }

    @GetMapping("/{id}")
    public Commande getById(@PathVariable Long id) {
        return commandeService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        return ResponseEntity.ok(commandeService.update(id, commande));
    }    

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandeService.delete(id);
    }
}
