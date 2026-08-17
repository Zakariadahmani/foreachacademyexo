import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Categorie } from '../../modeles/citation.model';
import { CitationService } from '../../services/citation';

@Component({
  selector: 'app-ajouter-citation',
  imports: [FormsModule],
  templateUrl: './ajouter-citation.html',
  styleUrl: './ajouter-citation.css'
})
export class AjouterCitation implements OnInit {
  categories: Categorie[] = [];

  texte = '';
  auteur = '';
  categorieId: number | null = null;

  erreur = '';
  envoiEnCours = false;

  constructor(
    private citationService: CitationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.citationService.getCategories().subscribe({
      next: (categories) => (this.categories = categories),
      error: () => {
        this.erreur = 'Impossible de contacter le serveur. Vérifiez que le backend est bien lancé.';
      },
    });
  }

  envoyer() {
    if (this.texte.trim() === '' || this.auteur.trim() === '' || this.categorieId === null) {
      this.erreur = 'Le formulaire est invalide : tous les champs sont obligatoires.';
      return;
    }

    this.erreur = '';
    this.envoiEnCours = true;

    this.citationService
      .ajouterCitation({
        texte: this.texte.trim(),
        auteur: this.auteur.trim(),
        categorieId: Number(this.categorieId),
      })
      .subscribe({
        next: () => {
          this.router.navigate(['/quotes']);
        },
        error: (err) => {
          this.envoiEnCours = false;
          if (err.error && err.error.message) {
            this.erreur = err.error.message;
          } else {
            this.erreur = 'Impossible de contacter le serveur. Vérifiez que le backend est bien lancé.';
          }
        },
      });
  }
}
