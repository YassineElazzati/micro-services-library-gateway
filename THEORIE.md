# Introduction aux Microservices

## LES MICROSERVICES

### • De quoi parle-t-on ?
Les microservices sont une manière moderne de concevoir des applications. L'idée est de découper une grosse application en **petites briques autonomes**, spécialisées sur un seul domaine métier. Chaque brique peut être **développée, testée et déployée indépendamment**. Ensemble, elles forment une application cohérente.

Une bonne analogie : une entreprise avec plusieurs pôles (RH, logistique, compta...) où chaque équipe gère sa mission, en autonomie, dans un cadre organisé.

---

### • Les caractéristiques des microservices

- ✅ **Autonome** : déploiement, tests, mises à jour sans impacter les autres services  
  ⚒ Ex : Le microservice "Paiement" peut être mis à jour sans toucher au microservice "Panier".

- ✅ **Responsabilité unique** : chaque service est centré sur un domaine fonctionnel  
  📎 Ex : un service "Facturation", un autre "Commandes", un autre "Utilisateurs".

- ✅ **Communication via API** (REST, RabbitMQ, Kafka...)  
  ⟳ Ex : "Commande" notifie "Livraison" lorsqu'une commande est validée.

- ✅ **Scalabilité indépendante** : chaque service peut être répliqué selon sa charge  
  📈 Ex : pendant une promo, le service "Produits" passe à 5 instances.

- ✅ **Résilience** : si un service tombe, les autres continuent à fonctionner  
  💥 Ex : "Avis clients" plante, "Commande" continue normalement.

- ✅ **Base de données dédiée (souvent)** : évite les conflits  
  💣 Ex : "Utilisateurs" → PostgreSQL, "Paiement" → MongoDB.

> Cette architecture suit le principe "**High Cohesion / Loose Coupling**".

---

### • La différence entre un microservice et un Webservice

#### WebService :
- Interface technique exposée sur le réseau (REST ou SOAP)
- Utilisé pour fournir des données ou actions à un client (navigateur, appli mobile)

🧠 **Exemple :**
> Une méthode `GET /produits` dans un monolithe Java EE → C'est un WebService, **pas** un microservice.

#### Microservice :
- Mini-application à part entière (logique, base, API)
- Autonome : déploiement, redémarrage, scalabilité isolés

🧠 **Exemple :**
> Un service `ProduitService` avec Spring Boot :
> - Contrôleur REST `GET /produits`
> - Service métier `ProduitService`
> - Repository JPA + base PostgreSQL
> → C'est un microservice.

> **Tous les microservices exposent des WebServices, mais tous les WebServices ne sont pas des microservices.**

---

### • Le Cloud et les microservices

#### Le Cloud, c'est quoi ?
- Des serveurs accessibles à distance, hébergés par AWS, Azure, GCP...
- Vos applis tournent sur Internet, pas en local

#### Les 3 modèles Cloud :
- **IaaS** : serveurs à configurer soi-même (ex : EC2)
- **PaaS** : plateforme prête à l'emploi (ex : Heroku)
- **SaaS** : application clé-en-main (ex : Gmail)

#### Pourquoi le Cloud est idéal pour les microservices ?
- ♻ Déploiement rapide via CI/CD
- 📊 Scalabilité automatique selon le trafic
- ⚡️ Lancer/arrêter des instances à la volée
- 🔍 Monitoring, logs et supervision native

🧠 **Exemple :**
> Un service "Paiement" surchargé pendant les soldes → on passe de 2 à 10 instances en quelques secondes

---

## IMPLÉMENTER UN MICROSERVICE AVEC SPRING BOOT

- Les étapes de conception d'un microservice
- Création de projet avec **Spring Initializr**
- Implémentation d'un **contrôleur REST**
- Configuration de l'environnement de dév (IntelliJ, Postman, etc.)
- Test local avec `localhost:8080`
- Déploiement avec **Spring Boot** + **Spring Cloud**
- Activer les "Cross-Origin" (CORS)
- Utilisation de la **CLI Spring Boot** (en option avancée)

---

## SPRING CLOUD ET L'ÉQUILIBRAGE DE CHARGE

- Définition de l'équilibrage de charge
- Outils Spring : **Spring Cloud LoadBalancer**, **Gateway**, **Ribbon** (ancien)
- API Gateway = point d'entrée unique

---

## LE SERVICE DISCOVERY DE SPRING CLOUD

- Pourquoi **multi-instancier** une API ?
- Prérequis : instances multiples, scalabilité, résilience
- Utilisation d'**Eureka** pour la **découverte de services**
- Centralisation de la config et enregistrement dynamique

---

Prêt pour passer à la pratique avec Spring Boot et vos premiers microservices ! 🚀
