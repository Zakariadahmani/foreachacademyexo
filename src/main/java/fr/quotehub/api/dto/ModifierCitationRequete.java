package fr.quotehub.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModifierCitationRequete(

        @NotBlank(message = "Le texte est obligatoire")
        @Size(max = 255, message = "Le texte ne peut pas dépasser 255 caractères")
        String texte,

        @NotBlank(message = "L'auteur est obligatoire")
        @Size(max = 255, message = "L'auteur ne peut pas dépasser 255 caractères")
        String auteur
) {
}