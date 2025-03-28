package com.example.livre_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etudiant")
    private Long id;

    private String nom;

    private String email;

@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonManagedReference
private List<Commande> commandes;

}
