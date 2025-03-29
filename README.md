# API Gateway - Spring Cloud Gateway

Ce projet représente un microservice `gateway-service` utilisant **Spring Cloud Gateway** pour router les appels HTTP vers d'autres microservices (comme `auteur-service` et `livre-service`).

## 🔧 1. Création du projet via Spring Initializr

Tu peux générer ton projet ici : [https://start.spring.io](https://start.spring.io)

**Choix à faire :**
- Project : Maven
- Language : Java
- Spring Boot : 3.4.4 (ou plus récent)
- Dependencies :
  - **Spring Reactive Web** *(webflux)* → permet à la Gateway d’être non-bloquante.
  - **Spring Cloud Gateway** → le cœur du système de routage.
  - **Spring Boot DevTools** *(optionnel)* → pour le hot-reload pendant le dev.
  - **Spring Boot Admin Server** *(optionnel)* → pour surveiller les microservices.
  - **Spring Boot Starter Test** + **Reactor Test** → pour écrire des tests réactifs.

## 🧱 2. Dépendances Maven à ajouter

```xml
<dependencies>
    <!-- WebFlux : moteur réactif obligatoire pour Gateway -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <!-- Spring Cloud Gateway : permet de créer une API Gateway réactive -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <!-- Spring Boot Admin Server : pour monitorer les microservices (optionnel) -->
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
    </dependency>

    <!-- DevTools : hot reload en local (optionnel) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Tests -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

> 📌 Le module `spring-cloud-starter-gateway` repose sur **WebFlux**, donc il ne fonctionne pas avec `spring-boot-starter-web`.

### 🎯 Spring Cloud ?
`Spring Cloud` est un ensemble de projets (Gateway, Config, Eureka, etc.) qui facilite le développement **d'applications distribuées et microservices**. Ici, on utilise **Spring Cloud Gateway** pour faire du **reverse proxy** entre le client et les autres services (auteur, livre...)

## ⚙️ 3. Configuration `application.properties`

```properties
# Port d'écoute de la gateway
server.port=8080
spring.application.name=gateway-service

# Gateway tourne avec WebFlux (obligatoire)
spring.main.web-application-type=reactive

# Routes

# Pour /api/auteurs → microservice auteur
spring.cloud.gateway.routes[0].id=auteur-service
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/auteurs/**

# Pour /api/livres → microservice auteur
spring.cloud.gateway.routes[1].id=livre-auteur-service
spring.cloud.gateway.routes[1].uri=http://localhost:8081
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/livres/**

# Pour /api/clients → microservice livre
spring.cloud.gateway.routes[2].id=client-livre-service
spring.cloud.gateway.routes[2].uri=http://localhost:8082
spring.cloud.gateway.routes[2].predicates[0]=Path=/api/clients/**

# Pour /api/commandes → microservice livre
spring.cloud.gateway.routes[3].id=commande-livre-service
spring.cloud.gateway.routes[3].uri=http://localhost:8082
spring.cloud.gateway.routes[3].predicates[0]=Path=/api/commandes/**

# Évite que Spring cherche des fichiers statiques
spring.web.resources.add-mappings=false
spring.thymeleaf.check-template-location=false

# Logs
logging.level.org.springframework.cloud.gateway=DEBUG
logging.level.org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping=TRACE
```

## ✅ Exemple de test via Postman

GET : `http://localhost:8080/api/auteurs` → redirige vers `auteur-service`

GET : `http://localhost:8080/api/livres` → redirige vers `livre-service`

