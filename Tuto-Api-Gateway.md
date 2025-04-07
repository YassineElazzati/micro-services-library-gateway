# 🧠 TUTO – API Gateway avec Spring Cloud Gateway (Projet complet)

Ce tutoriel vous montre comment mettre en place une **API Gateway** avec **Spring Cloud Gateway**, pour diriger le trafic vers vos microservices `auteur-service` et `livre-service` dans un projet réel.

> ⚠️ Ce tuto ne traite **pas encore du load balancing avec Eureka**. Il s'agit d'une config **statique**. La suite viendra plus tard.

---

## 🧰 Pré-requis

- Java 17
- Spring Boot 3.4.4
- Spring Cloud 2024.0.1
- Maven
- Docker (optionnel mais recommandé)
- Microservices `auteur-service` (port **8081**) et `livre-service` (port **8082**)

---

## 📁 Structure du projet

```
microservices-library-gateway
├── auteur-service (port 8081)
├── livre-service  (port 8082)
├── gateway-service (port 8080)
└── docker-compose.yml
```

Une **API Gateway** agit comme **un point d'entrée unique** dans un système de microservices. Elle intercepte les requêtes entrantes, applique des règles (filtrage, authentification, logging...) et redirige les appels vers le bon service interne.

---

## 1. 🚀 Créer le microservice `gateway-service`

Via Spring Initializr, sélectionnez :
- **Spring Reactive Web** (`spring-boot-starter-webflux`) → car Spring Cloud Gateway repose sur WebFlux (réactif).
- **Spring Cloud Gateway** → pour le routage intelligent.
- **Spring Boot Devtools** (optionnel) → pour le rechargement à chaud.
- **Spring Boot Admin Server** (optionnel) → pour superviser les services.

---

## 2. 🧹 Fichier `pom.xml` de `gateway-service`

Ce fichier Maven contient les dépendances nécessaires pour WebFlux, Gateway, Devtools et Admin Server. La section `dependencyManagement` importe la version officielle de Spring Cloud (`2024.0.1`).

---

## 3. ⚙️ `application.properties` de `gateway-service`

Ce fichier configure le port et définit les routes à exposer dans la gateway. Chaque `route` suit cette logique :

- `id` : identifiant unique de la route (utile pour le debug/logging).
- `uri` : adresse de destination (où la requête est redirigée).
- `predicates` : conditions d'activation (ex : path de l'URL).

```properties
server.port=8080
spring.application.name=gateway-service
spring.main.web-application-type=reactive

spring.cloud.gateway.routes[0].id=auteur-service
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/auteurs/**

spring.cloud.gateway.routes[1].id=livre-auteur-service
spring.cloud.gateway.routes[1].uri=http://localhost:8081
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/livres/**

spring.cloud.gateway.routes[2].id=client-livre-service
spring.cloud.gateway.routes[2].uri=http://localhost:8082
spring.cloud.gateway.routes[2].predicates[0]=Path=/api/clients/**

spring.cloud.gateway.routes[3].id=commande-livre-service
spring.cloud.gateway.routes[3].uri=http://localhost:8082
spring.cloud.gateway.routes[3].predicates[0]=Path=/api/commandes/**

spring.web.resources.add-mappings=false
spring.thymeleaf.check-template-location=false

logging.level.org.springframework.cloud.gateway=DEBUG
logging.level.org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping=TRACE
```

Chaque route permet de **rediriger une requête entrante vers le bon microservice**, sans que le client ait à connaître l’adresse réelle du service.

---

## 4. 🧠 Code principal `GatewayServiceApplication.java`

Voici le point d'entrée principal. L'annotation `@SpringBootApplication` lance l'application. Aucun code spécifique à Gateway n'est requis ici : tout est géré par configuration.

```java
@SpringBootApplication
public class GatewayServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(GatewayServiceApplication.class, args);
  }
}
```

---

## 5. 🧪 Exemple de test des routes

Lancez les services suivants :
- `auteur-service` → http://localhost:8081
- `livre-service`  → http://localhost:8082
- `gateway-service` → http://localhost:8080

### Exemples d'appels via Gateway

```http
GET  http://localhost:8080/api/auteurs
→ redirige vers http://localhost:8081/api/auteurs

POST http://localhost:8080/api/clients
→ redirige vers http://localhost:8082/api/clients

GET  http://localhost:8080/api/livres/3
→ redirige vers http://localhost:8081/api/livres/3
```

La Gateway fonctionne comme **un proxy intelligent**. Elle permet d'unifier l'accès aux APIs et de masquer la complexité du système.

---

## ✅ Résultat attendu

| Appel via Gateway            | Redirection réelle                      |
|-----------------------------|------------------------------------------|
| `/api/auteurs`             | `http://localhost:8081/api/auteurs`     |
| `/api/livres/5`            | `http://localhost:8081/api/livres/5`    |
| `/api/clients`             | `http://localhost:8082/api/clients`     |
| `/api/commandes/2`         | `http://localhost:8082/api/commandes/2` |

---

## 🧼 À venir : Eureka + Load Balancing

> Actuellement, on écrit l'URI des microservices en dur (`http://localhost:8081`).  
> Avec **Eureka**, on pourra simplement écrire : `lb://auteur-service`

Cela permettra à la Gateway de découvrir automatiquement les instances du microservice (si on en a plusieurs), et de faire de la **répartition de charge** automatiquement.

➡️ On verra ça dans le **prochain module** !

---

## 🔚 Conclusion

Vous avez maintenant une **API Gateway fonctionnelle** qui agit comme une **porte d’entrée unique** vers tous vos microservices.

✅ Elle centralise, filtre et redirige les appels.  
✅ Elle facilite le debug, l'évolution, la sécurité.

➡️ Prochaine étape : intégrer Eureka pour faire du **routage dynamique + load balancing** sans écrire d’IP ni de port à la main. 🚀

