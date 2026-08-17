package fr.quotehub.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.quotehub.api.dto.CitationReponse;
import fr.quotehub.api.dto.CreerCitationRequete;
import fr.quotehub.api.dto.ModifierCitationRequete;
import fr.quotehub.api.entite.Categorie;
import fr.quotehub.api.entite.Citation;
import fr.quotehub.api.erreur.CategorieIntrouvableException;
import fr.quotehub.api.erreur.CitationIntrouvableException;
import fr.quotehub.api.erreur.TexteDejaUtiliseException;
import fr.quotehub.api.repository.CategorieRepository;
import fr.quotehub.api.repository.CitationRepository;

@Service
public class CitationService {

    private final CitationRepository citationRepository;
    private final CategorieRepository categorieRepository;

    public CitationService(CitationRepository citationRepository,
                            CategorieRepository categorieRepository) {
        this.citationRepository = citationRepository;
        this.categorieRepository = categorieRepository;
    }

    // ── Lister ───────────────────────────────────────────
    public List<CitationReponse> lister() {
        return citationRepository.findAllWithCategorie()
                .stream()
                .map(this::versReponse)
                .toList();
    }

    // ── Lire ─────────────────────────────────────────────
    public CitationReponse lire(Long id) {
        return versReponse(trouverParId(id));
    }

    // ── Créer ────────────────────────────────────────────
    public CitationReponse creer(CreerCitationRequete requete) {
        if (citationRepository.existsByTexte(requete.texte())) {
            throw new TexteDejaUtiliseException(requete.texte());
        }

        Categorie categorie = categorieRepository.findById(requete.categorieId())
                .orElseThrow(() -> new CategorieIntrouvableException(requete.categorieId()));

        Citation citation = new Citation();
        citation.setTexte(requete.texte());
        citation.setAuteur(requete.auteur());
        citation.setCategorie(categorie);

        return versReponse(citationRepository.save(citation));
    }

    // ── Modifier ─────────────────────────────────────────
    public CitationReponse modifier(Long id, ModifierCitationRequete requete) {
        Citation citation = trouverParId(id);
        citation.setTexte(requete.texte());
        citation.setAuteur(requete.auteur());
        return versReponse(citationRepository.save(citation));
    }

    // ── Supprimer ────────────────────────────────────────
    public void supprimer(Long id) {
        citationRepository.delete(trouverParId(id));
    }

    // ── Utilitaires privés ───────────────────────────────
    private Citation trouverParId(Long id) {
        return citationRepository.findById(id)
                .orElseThrow(() -> new CitationIntrouvableException(id));
    }

    private CitationReponse versReponse(Citation citation) {
        return new CitationReponse(
                citation.getId(),
                citation.getTexte(),
                citation.getAuteur(),
                citation.getCategorie().getNom()
        );
    }
}