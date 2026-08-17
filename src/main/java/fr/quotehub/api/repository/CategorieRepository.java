package fr.quotehub.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.quotehub.api.entite.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}