import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { Citation } from '../../modeles/citation.model';
import { CitationService } from '../../services/citation';

@Component({
  selector: 'app-detail-citation',
  imports: [RouterLink],
  templateUrl: './detail-citation.html',
  styleUrl: './detail-citation.css'
})
export class DetailCitation implements OnInit {
  citation: Citation | null = null;
  chargement = true;
  erreur = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private citationService: CitationService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.citationService.getCitation(id).subscribe({
      next: (citation) => {
        this.citation = citation;
        this.chargement = false;
      },
      error: (err) => {
        if (err.status === 404) {
          this.erreur = 'Cette citation n\'existe pas.';
        } else {
          this.erreur = 'Impossible de contacter le serveur. Vérifiez que le backend est bien lancé.';
        }
        this.chargement = false;
      },
    });
  }

  supprimer() {
    if (!this.citation) {
      return;
    }
    const confirmation = confirm('Voulez-vous vraiment supprimer cette citation ?');
    if (!confirmation) {
      return;
    }

    this.citationService.supprimerCitation(this.citation.id).subscribe({
      next: () => this.router.navigate(['/quotes']),
      error: () => (this.erreur = 'La suppression a échoué.'),
    });
  }
}
