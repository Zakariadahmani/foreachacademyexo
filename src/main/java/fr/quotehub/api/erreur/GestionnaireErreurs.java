package fr.quotehub.api.erreur;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionnaireErreurs {

    // ── 404 : Citation introuvable ────────────────────────
    @ExceptionHandler(CitationIntrouvableException.class)
    public ResponseEntity<Map<String, Object>> gererCitationIntrouvable(CitationIntrouvableException ex) {
        return construireReponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── 404 : Catégorie introuvable ───────────────────────
    @ExceptionHandler(CategorieIntrouvableException.class)
    public ResponseEntity<Map<String, Object>> gererCategorieIntrouvable(CategorieIntrouvableException ex) {
        return construireReponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── 409 : Texte déjà utilisé ───────────────────────────
    @ExceptionHandler(TexteDejaUtiliseException.class)
    public ResponseEntity<Map<String, Object>> gererTexteDejaUtilise(TexteDejaUtiliseException ex) {
        return construireReponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── 400 : Données invalides (@Valid dans le contrôleur) ─
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> gererValidationEchouee(MethodArgumentNotValidException ex) {
        List<String> erreurs = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(erreur -> erreur.getField() + " : " + erreur.getDefaultMessage())
                .toList();

        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("horodatage", Instant.now());
        corps.put("statut", HttpStatus.BAD_REQUEST.value());
        corps.put("erreurs", erreurs);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corps);
    }

    // ── Utilitaire privé ───────────────────────────────────
    private ResponseEntity<Map<String, Object>> construireReponse(HttpStatus statut, String message) {
        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("horodatage", Instant.now());
        corps.put("statut", statut.value());
        corps.put("message", message);

        return ResponseEntity.status(statut).body(corps);
    }
}