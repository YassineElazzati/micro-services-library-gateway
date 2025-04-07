# 🧠 TUTO – API Gateway avec Spring Cloud Gateway

Ce tutoriel vous montre comment mettre en place une **API Gateway** avec **Spring Cloud Gateway**, pour diriger le trafic vers vos microservices (`auteur-service`, `livre-service`, etc.).

> ⚠️ Ce tutoriel n’inclut **pas encore le load balancing (LB)** avec Eureka – on le verra plus tard.

---

## 🧰 Pré-requis

- Java 17
- Spring Boot 3.4.x
- Spring Cloud 2024.x
- Maven
- Docker (optionnel mais conseillé)
- Microservices déjà fonctionnels (ex: `auteur-service`, `livre-service`)

---

## 📁 Structure du projet

Voici la structure simplifiée qu’on utilise :

```
microservices-library-gateway
├── gateway-service
│   ├── src
│   │   └── main
│   │       └── java/com/example/gateway_service/GatewayServiceApplication.java
│   │       └── resources/application.properties
│   └── pom.xml
```

---

## 1. 🚀 Créer le projet `gateway-service`

Dans Spring Initializr (ou via Maven), créez un projet avec les **dépendances suivantes** :

- `Spring Reactive Web` (`spring-boot-starter-webflux`)
- `Spring Cloud Gateway`
- `Spring Boot Devtools` (optionnel)
- `Spring Boot Admin Server` (optionnel)

---

## 2. 🧹 Exemple de `pom.xml`

Voici un exemple prêt à l’emploi :

```xml
<dependencies>
    <!-- WebFlux -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <!-- Spring Cloud Gateway -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <!-- DevTools (rechargement à chaud) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2024.0.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

## 3. ⚙️ Configuration dans `application.properties`

Voici un exemple de configuration **statique** (sans Eureka, IP/ports manuels) :

```properties
# Port de la gateway
server.port=8080

# Nom du microservice
spring.application.name=gateway-service

# Routes manuelles
spring.cloud.gateway.routes[0].id=auteur-service
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/auteurs/**

spring.cloud.gateway.routes[1].id=livre-service
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/clients/**, /api/commandes/**
```

> ✅ Cela veut dire que tout appel vers `http://localhost:8080/api/auteurs/**` sera redirigé vers `http://localhost:8081`

---

## 4. 🧠 Code minimal de `GatewayServiceApplication`

```java
package com.example.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
```

---

## 5. 🧪 Exemple de test des routes

### Lancer les services :
- `auteur-service` (sur le port `8081`)
- `livre-service` (sur le port `8082`)
- `gateway-service` (sur le port `8080`)

### Appeler l'API Gateway :

```http
GET http://localhost:8080/api/auteurs
→ redirige vers http://localhost:8081/api/auteurs

GET http://localhost:8080/api/clients
→ redirige vers http://localhost:8082/api/clients
```

---

## ✅ Résultat attendu

| Appel via Gateway                       | Redirection réelle                      |
|----------------------------------------|------------------------------------------|
| `GET /api/auteurs`                     | `http://localhost:8081/api/auteurs`      |
| `POST /api/clients`                    | `http://localhost:8082/api/clients`      |
| `DELETE /api/commandes/1`              | `http://localhost:8082/api/commandes/1`  |

---

## 🧼 Astuce : Comment rendre ça dynamique (prochaine étape)

> Pour ne pas avoir à coder chaque URL en dur (IP + port), on utilisera plus tard **Eureka + load balancing automatique**.  
> On pourra écrire des URI comme :  
> `lb://auteur-service` ou `lb://livre-service`.

👉 **Mais ça, on le verra dans le prochain cours !**

---

## 🔚 Conclusion

Avec cette configuration, vous avez une **API Gateway 100% fonctionnelle**. Elle intercepte les requêtes entrantes et les redirige vers le bon microservice.

---

