package fr.quotehub.api.erreur;

public class TexteDejaUtiliseException extends RuntimeException {

    public TexteDejaUtiliseException(String texte) {
        super("Une citation avec ce texte existe déjà : " + texte);
    }
}