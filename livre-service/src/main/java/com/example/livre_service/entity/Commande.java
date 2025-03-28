package com.example.livre_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formation")
    private Long id;

    private Double produit;

    private String montant;

@ManyToOne
@JoinColumn(name = "id_etudiant", nullable = false)
@JsonBackReference
private Client client;

}
