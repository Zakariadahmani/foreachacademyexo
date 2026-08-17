package fr.quotehub.api.dto;

public record CitationReponse(
        Long id,
        String texte,
        String auteur,
        String nomCategorie
) {
}