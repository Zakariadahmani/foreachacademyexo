package fr.quotehub.api.controleur;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.quotehub.api.entite.Categorie;
import fr.quotehub.api.repository.CategorieRepository;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:4200") // autorise le front Angular
public class CategorieController {

    private final CategorieRepository categorieRepository;

    public CategorieController(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    // ── GET /categories ──────────────────────────────────
    @GetMapping
    public List<Categorie> lister() {
        return categorieRepository.findAll();
    }
}
