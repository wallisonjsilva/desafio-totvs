import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteViewComponent } from './pages/cliente-view/cliente-view.component';
import { HomeComponent } from './pages/home/home.component';
import { ClienteEditComponent } from './pages/cliente-edit/cliente-edit.component';
import { EnderecoEditComponent } from './pages/endereco-edit/endereco-edit.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'cliente-view', component: ClienteViewComponent },
  { path: 'cliente-edit', component: ClienteEditComponent },
  { path: 'cliente-edit/:id', component: ClienteEditComponent },
  { path: 'endereco-edit/:clienteId', component: EnderecoEditComponent },
  { path: 'endereco-edit/:clienteId/:enderecoId', component: EnderecoEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
