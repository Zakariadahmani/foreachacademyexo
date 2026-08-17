package fr.quotehub.api.erreur;

public class CategorieIntrouvableException extends RuntimeException {

    public CategorieIntrouvableException(Long id) {
        super("Catégorie introuvable avec l'id : " + id);
    }
}