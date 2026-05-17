import { Routes } from '@angular/router';
import { ListaProdutosComponent } from './components/lista-produtos/lista-produtos';

export const routes: Routes = [
  { path: '', component: ListaProdutosComponent },
  { path: '**', redirectTo: '' }
];
