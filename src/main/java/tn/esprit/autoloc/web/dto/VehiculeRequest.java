package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.math.BigDecimal;

@Schema(description = "Données envoyées pour créer ou modifier un véhicule")
public record VehiculeRequest(

        @Schema(example = "200 TU 1234")
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,

        @Schema(example = "Peugeot")
        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
        String marque,

        @Schema(example = "208")
        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
        String modele,

        @Schema(example = "CITADINE")
        @NotNull(message = "La catégorie est obligatoire")
        CategorieVehicule categorie,

        @Schema(example = "85.00")
        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le tarif journalier doit être positif")
        @Digits(integer = 8, fraction = 2, message = "Le tarif doit avoir au maximum 2 décimales")
        BigDecimal tarifJournalier,

        @Schema(example = "DISPONIBLE")
        @NotNull(message = "Le statut est obligatoire")
        StatutVehicule statut,

        @Schema(example = "1", description = "Identifiant de l'agence propriétaire (optionnel)")
        @Positive(message = "L'identifiant de l'agence doit être positif")
        Long idAgence
) {
}