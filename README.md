# README - Projet Microservices Spring Boot (Jour 1 & 2)

Bienvenue test etetsts t dans ce projet d'introduction aux **microservices avec Java Spring Boot**, pensé pour les deux premiers jours de la formation "4AMS : API & Microservices".

---

## ✨ Objectifs

- Comprendre les bases des microservices
- Créer une architecture simple avec 2 microservices : `auteur-service` et `livre-service`
- Interagir avec des API REST (CRUD)
- Utiliser Docker pour la base de données
- Tester avec Postman

---

## 🚀 1. Démarrage avec Spring Initializr

Allez sur : https://start.spring.io/

**Pour chaque microservice (auteur et livre) :**

- Project : Maven
- Language : Java
- Spring Boot : 3.4.4
- Group : `com.example`
- Artifact : `auteur-service` ou `livre-service`
- Dependencies :
  - Spring Web
  - Spring Data JPA
  - PostgreSQL Driver
  - Lombok
  - Spring Boot DevTools

---

## 📄 2. Script SQL

### Pour `auteur-service`
```sql
CREATE TABLE auteur (
   id_auteur SERIAL PRIMARY KEY,
   nom VARCHAR(50)
);

CREATE TABLE livre (
   id_livre SERIAL PRIMARY KEY,
   titre VARCHAR(50),
   id_auteur INTEGER NOT NULL,
   FOREIGN KEY(id_auteur) REFERENCES auteur(id_auteur)
);
```

### Pour `livre-service`
```sql
CREATE TABLE client(
   id_etudiant SERIAL PRIMARY KEY,
   nom VARCHAR(50),
   email VARCHAR(50)
);

CREATE TABLE commande(
   id_formation SERIAL PRIMARY KEY,
   produit NUMERIC(15,2),
   montant VARCHAR(50),
   id_etudiant INTEGER NOT NULL,
   FOREIGN KEY(id_etudiant) REFERENCES client(id_etudiant)
);
```

---

## 📝 3. application.properties (exemple auteur-service)
```properties
server.port=8081
spring.application.name=auteur-service

spring.datasource.url=jdbc:postgresql://localhost:5433/auteurdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Changer les ports/BDD pour `livre-service` : port 8082, port Docker 5434, BDD `livredb`.

---

## 🧰 4. Docker Compose

Créer un fichier `docker-compose.yml` à la racine :

```yaml
version: '3.8'

services:
  auteur-db:
    image: postgres:15
    container_name: auteur-db
    environment:
      POSTGRES_DB: auteurdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5433:5432"
    volumes:
      - auteur-data:/var/lib/postgresql/data
    networks:
      - library-network

  livre-db:
    image: postgres:15
    container_name: livre-db
    environment:
      POSTGRES_DB: livredb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5434:5432"
    volumes:
      - livre-data:/var/lib/postgresql/data
    networks:
      - library-network

volumes:
  auteur-data:
  livre-data:

networks:
  library-network:
    driver: bridge
```

**Lancement des containers :**
```bash
docker compose up -d
```

---

## 🚪 5. Architecture des dossiers

```
microservices-library/
├── auteur-service
│   ├── entity (Auteur, Livre)
│   ├── repository
│   ├── service + impl
│   ├── controller
│   └── application.properties
├── livre-service
│   ├── entity (Client, Commande)
│   ├── repository
│   ├── service + impl
│   ├── controller
│   └── application.properties
├── docker-compose.yml
```

---

## 🔍 6. Exemple de requêtes Postman : `auteur-service`

### POST /auteurs
```json
{
  "nom": "Victor Hugo"
}
```

### POST /livres
```json
{
  "titre": "Les Misérables",
  "auteur": {
    "id": 1
  }
}
```

### GET /auteurs /livres | PUT /{id} | DELETE /{id} ➔ Standard REST

---

## ✅ Prêt pour le Jour 3 :

- Intégration d'une **Gateway Spring Cloud**
- Ajout de **Eureka** pour le découverte de services
- Et peut-être un peu de **sécurité** si on a le temps 🤓

---

Bon courage à tous ! 🚀

---

# micro-services-library-gateway
# micro-services-library-gateway
