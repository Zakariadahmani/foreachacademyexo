package fr.quotehub.api.controleur;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.quotehub.api.dto.CitationReponse;
import fr.quotehub.api.dto.CreerCitationRequete;
import fr.quotehub.api.dto.ModifierCitationRequete;
import fr.quotehub.api.service.CitationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/quotes")
@CrossOrigin(origins = "http://localhost:4200") // autorise le front Angular
public class CitationController {

    private final CitationService citationService;

    public CitationController(CitationService citationService) {
        this.citationService = citationService;
    }

    // ── GET /quotes ──────────────────────────────────────
    @GetMapping
    public List<CitationReponse> lister() {
        return citationService.lister();
    }

    // ── GET /quotes/{id} ─────────────────────────────────
    @GetMapping("/{id}")
    public CitationReponse lire(@PathVariable Long id) {
        return citationService.lire(id);
    }

    // ── POST /quotes ─────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CitationReponse creer(@Valid @RequestBody CreerCitationRequete requete) {
        return citationService.creer(requete);
    }

    // ── PUT /quotes/{id} ─────────────────────────────────
    @PutMapping("/{id}")
    public CitationReponse modifier(@PathVariable Long id,
                                     @Valid @RequestBody ModifierCitationRequete requete) {
        return citationService.modifier(id, requete);
    }

    // ── DELETE /quotes/{id} ──────────────────────────────
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        citationService.supprimer(id);
    }
}