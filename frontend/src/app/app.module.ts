import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PoModule } from '@po-ui/ng-components';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { ClienteViewComponent } from './pages/cliente-view/cliente-view.component';
import { ClienteEditComponent } from './pages/cliente-edit/cliente-edit.component';
import { HomeComponent } from './pages/home/home.component';

import { PoTableModule } from '@po-ui/ng-components';
import { PoTabsModule } from '@po-ui/ng-components';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TelefoneViewComponent } from './components/telefone-view/telefone-view.component';
import { EnderecoViewComponent } from './components/endereco-view/endereco-view.component';
import { EnderecoEditComponent } from './pages/endereco-edit/endereco-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    ClienteViewComponent,
    ClienteEditComponent,
    HomeComponent,
    TelefoneViewComponent,
    EnderecoViewComponent,
    EnderecoEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    PoModule,
    HttpClientModule,
    PoTableModule,
    PoTabsModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
