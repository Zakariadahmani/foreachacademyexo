package fr.quotehub.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.quotehub.api.entite.Citation;

public interface CitationRepository extends JpaRepository<Citation, Long> {

    @Query("SELECT c FROM Citation c JOIN FETCH c.categorie")
    List<Citation> findAllWithCategorie();

    boolean existsByTexte(String texte);
}