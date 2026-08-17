import { Routes } from '@angular/router';

import { Accueil } from './pages/accueil/accueil';
import { ListeCitations } from './pages/liste-citations/liste-citations';
import { DetailCitation } from './pages/detail-citation/detail-citation';
import { AjouterCitation } from './pages/ajouter-citation/ajouter-citation';
import { PageIntrouvable } from './pages/page-introuvable/page-introuvable';

export const routes: Routes = [
  { path: '', component: Accueil },
  { path: 'quotes', component: ListeCitations },
  { path: 'quotes/:id', component: DetailCitation },
  { path: 'add', component: AjouterCitation },
  { path: '**', component: PageIntrouvable },
];
