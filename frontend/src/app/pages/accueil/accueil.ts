import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Citation } from '../../modeles/citation.model';
import { CitationService } from '../../services/citation';

@Component({
  selector: 'app-accueil',
  imports: [RouterLink],
  templateUrl: './accueil.html',
  styleUrl: './accueil.css'
})
export class Accueil implements OnInit {
  citations: Citation[] = [];
  citationAffichee: Citation | null = null;
  chargement = true;
  erreur = '';

  constructor(private citationService: CitationService) {}

  ngOnInit() {
    this.citationService.getCitations().subscribe({
      next: (citations) => {
        this.citations = citations;
        this.chargement = false;
        this.nouvelleCitation();
      },
      error: () => {
        this.erreur = 'Impossible de contacter le serveur. Vérifiez que le backend est bien lancé.';
        this.chargement = false;
      },
    });
  }

  nouvelleCitation() {
    if (this.citations.length === 0) {
      return;
    }
    const indexAleatoire = Math.floor(Math.random() * this.citations.length);
    this.citationAffichee = this.citations[indexAleatoire];
  }
}
