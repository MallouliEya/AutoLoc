# autoloc-api — Atelier 1 (Séance 2)

Projet Spring Boot / Maven de l'étude de cas **AutoLoc** (ASI 26-27).

## Contenu
- `pom.xml` : Spring Web, Spring Data JPA, MySQL Driver, Lombok, Validation, DevTools (Java 17)
- `application.properties` : connexion MySQL `autoloc_db` (créée automatiquement), `ddl-auto=update`, logs SQL
- Package `domain` : 9 entités JPA **sans associations** (ajoutées en Atelier 2)
  - Vehicule, Agence, Client, Employe, Equipement, Reservation, Contrat, Paiement, Maintenance
  - Enums : CategorieVehicule, StatutVehicule, RoleEmploye, StatutReservation, ModePaiement
- Packages préparés : `repository`, `service`, `web.controller`, `web.dto`

## Lancer
1. Démarrer MySQL.
2. Définir le mot de passe (optionnel si vide) : variable d'environnement `DB_PASSWORD`
   (IntelliJ : Run → Edit Configurations → Environment variables → `DB_PASSWORD=...`).
3. Activer l'annotation processing (Settings → Compiler → Annotation Processors).
4. Lancer `AutolocApiApplication` puis vérifier les 9 tables dans `autoloc_db`.

## API REST + documentation Swagger (SpringDoc)

- Dépendance : `springdoc-openapi-starter-webmvc-ui` (2.8.9)
- Swagger UI : http://localhost:8081/swagger-ui.html
- Spécification OpenAPI : http://localhost:8081/v3/api-docs

### Endpoints CRUD (Agence, Vehicule, Client)

| Méthode | URL | Rôle | Codes |
|---|---|---|---|
| GET | `/agences` · `/vehicules` · `/clients` | lister | 200 |
| GET | `/{ressource}/{id}` | détail | 200 / 404 |
| POST | `/{ressource}` | créer | 201 |
| PUT | `/{ressource}/{id}` | modifier | 200 / 404 |
| DELETE | `/{ressource}/{id}` | supprimer | 204 / 404 |

Les endpoints sont documentés avec `@Tag`, `@Operation`, `@ApiResponse` et `@Parameter`.
Les associations sont exclues du JSON avec `@JsonIgnore` pour éviter les boucles de sérialisation.

### Tests Postman

Importer `postman/AutoLoc.postman_collection.json` dans Postman (variable `baseUrl` = `http://localhost:8081`).
