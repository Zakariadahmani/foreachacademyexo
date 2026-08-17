package fr.quotehub.api.erreur;

public class CitationIntrouvableException extends RuntimeException {

    public CitationIntrouvableException(Long id) {
        super("Citation introuvable avec l'id : " + id);
    }
}