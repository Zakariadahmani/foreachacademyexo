package fr.quotehub.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.quotehub.api.entite.Categorie;
import fr.quotehub.api.entite.Citation;
import fr.quotehub.api.repository.CategorieRepository;
import fr.quotehub.api.repository.CitationRepository;

/**
 * Insère les données de départ du sujet au premier lancement,
 * seulement si la base est vide.
 */
@Component
public class DonneesDepart implements CommandLineRunner {

    private final CategorieRepository categorieRepository;
    private final CitationRepository citationRepository;

    public DonneesDepart(CategorieRepository categorieRepository,
                          CitationRepository citationRepository) {
        this.categorieRepository = categorieRepository;
        this.citationRepository = citationRepository;
    }

    @Override
    public void run(String... args) {
        // Si des citations existent déjà, on ne fait rien
        if (citationRepository.count() > 0) {
            return;
        }

        Categorie science = creerCategorie("Science");
        Categorie motivation = creerCategorie("Motivation");
        Categorie humour = creerCategorie("Humour");
        Categorie philosophie = creerCategorie("Philosophie");
        Categorie sport = creerCategorie("Sport");
        creerCategorie("Travail");

        creerCitation("La logique vous mènera d'un point A à un point B. L'imagination vous mènera partout.",
                "Albert Einstein", science);
        creerCitation("Cela semble toujours impossible jusqu'à ce que ce soit fait.",
                "Nelson Mandela", motivation);
        creerCitation("Soyez vous-même, les autres sont déjà pris.",
                "Oscar Wilde", humour);
        creerCitation("Choisissez un travail que vous aimez et vous n'aurez pas à travailler un seul jour de votre vie.",
                "Confucius", philosophie);
        creerCitation("J'ai échoué encore et encore dans ma vie. Et c'est pourquoi je réussis.",
                "Michael Jordan", sport);
    }

    private Categorie creerCategorie(String nom) {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        return categorieRepository.save(categorie);
    }

    private void creerCitation(String texte, String auteur, Categorie categorie) {
        Citation citation = new Citation();
        citation.setTexte(texte);
        citation.setAuteur(auteur);
        citation.setCategorie(categorie);
        citationRepository.save(citation);
    }
}
