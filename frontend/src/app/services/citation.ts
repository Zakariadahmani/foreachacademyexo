import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Categorie, Citation } from '../modeles/citation.model';

@Injectable({
  providedIn: 'root',
})
export class CitationService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getCitations(): Observable<Citation[]> {
    return this.http.get<Citation[]>(this.apiUrl + '/quotes');
  }

  getCitation(id: number): Observable<Citation> {
    return this.http.get<Citation>(this.apiUrl + '/quotes/' + id);
  }

  ajouterCitation(citation: { texte: string; auteur: string; categorieId: number }): Observable<Citation> {
    return this.http.post<Citation>(this.apiUrl + '/quotes', citation);
  }

  supprimerCitation(id: number): Observable<void> {
    return this.http.delete<void>(this.apiUrl + '/quotes/' + id);
  }

  getCategories(): Observable<Categorie[]> {
    return this.http.get<Categorie[]>(this.apiUrl + '/categories');
  }
}
