import { Routes } from '@angular/router';
import { ListaProdutosComponent } from './components/lista-produtos/lista-produtos';
import { FormProdutoComponent } from './components/form-produto/form-produto';
import { ListaTiposComponent } from './components/lista-tipos/lista-tipos';

export const routes: Routes = [
  { path: '', redirectTo: 'produtos', pathMatch: 'full' },
  { path: 'produtos', component: ListaProdutosComponent },
  { path: 'produtos/novo', component: FormProdutoComponent },
  { path: 'tipos', component: ListaTiposComponent },
  { path: '**', redirectTo: 'produtos' }
];
