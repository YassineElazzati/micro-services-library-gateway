package com.example.livre_service.controller;

import com.example.livre_service.entity.Client;
import com.example.livre_service.service.ClientService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }    

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
