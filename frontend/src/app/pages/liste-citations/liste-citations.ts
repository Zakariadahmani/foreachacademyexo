import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Categorie, Citation } from '../../modeles/citation.model';
import { CitationService } from '../../services/citation';

@Component({
  selector: 'app-liste-citations',
  imports: [FormsModule, RouterLink],
  templateUrl: './liste-citations.html',
  styleUrl: './liste-citations.css'
})
export class ListeCitations implements OnInit {
  citations: Citation[] = [];
  categories: Categorie[] = [];
  recherche = '';
  categorieChoisie = '';
  chargement = true;
  erreur = '';

  constructor(private citationService: CitationService) {}

  ngOnInit() {
    this.citationService.getCitations().subscribe({
      next: (citations) => {
        this.citations = citations;
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Impossible de contacter le serveur. Vérifiez que le backend est bien lancé.';
        this.chargement = false;
      },
    });

    this.citationService.getCategories().subscribe({
      next: (categories) => (this.categories = categories),
    });
  }

  citationsFiltrees(): Citation[] {
    const motCherche = this.recherche.toLowerCase();

    return this.citations.filter((citation) => {
      const correspondRecherche =
        citation.texte.toLowerCase().includes(motCherche) ||
        citation.auteur.toLowerCase().includes(motCherche);

      const correspondCategorie =
        this.categorieChoisie === '' || citation.nomCategorie === this.categorieChoisie;

      return correspondRecherche && correspondCategorie;
    });
  }
}
