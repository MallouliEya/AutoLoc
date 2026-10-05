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
