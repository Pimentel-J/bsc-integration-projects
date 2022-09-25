import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';

import { NgxSpinnerModule } from "ngx-spinner";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MessagesComponent } from './messages/messages.component';

import { DashboardComponent } from './dashboard/dashboard.component';
import { LinhaDetailComponent } from './Linha/linha-detail/linha-detail.component';
import { LinhasComponent } from './Linha/linhas/linhas.component';
import { BlocoDetailComponent } from './Bloco/bloco-detail/bloco-detail.component';
import { BlocoCreateComponent } from './Bloco/bloco-create/bloco-create.component';
import { BlocosComponent } from './Bloco/blocos/blocos.component';
import { TpvDetailComponent } from './tipoviatura/tpv-detail/tpv-detail.component';
import { TpvListComponent } from './tipoviatura/tpv-list/tpv-list.component';
import { TpvSearchComponent } from './tipoviatura/tpv-search/tpv-search.component';
import { NosComponent } from './No/nos/nos.component';
import { NoDetailComponent } from './No/no-detail/no-detail.component';
import { PercursosComponent } from './Percurso/percursos/percursos.component';
import { PercursoDetailComponent } from './Percurso/percurso-detail/percurso-detail.component';
import { SegmentoDetailComponent } from './Segmento/segmento-detail/segmento-detail.component';
import { TiposTripulanteComponent } from './TipoTripulante/tiposTripulante/tiposTripulante.component';
import { TipoTripulanteDetailComponent } from './TipoTripulante/tipoTripulante-detail/tipoTripulante-detail.component';
import { TripulantesComponent } from './Tripulante/tripulantes/tripulantes.component';
import { TripulanteDetailComponent } from './Tripulante/tripulante-detail/tripulante-detail.component';
import { ServicosTripulanteComponent } from './ServicoTripulante/servicosTripulante/servicosTripulante.component';
import { ServicoTripulanteDetailComponent } from './ServicoTripulante/servicoTripulante-detail/servicoTripulante-detail.component';
import { PercursoCreateComponent } from './Percurso/percurso-create/percurso-create.component';
import { SegmentoCreateComponent } from './Segmento/segmento-create/segmento-create.component';
import { ImportFileComponent } from './import-file/import-file.component';
import { SolucaoMudancaMotoristaComponent } from './Planeamento/solucao-mudanca-motorista/solucao-mudanca-motorista.component';
import { MapaRedeComponent } from './mapa-rede/mapa-rede.component';
import { ViaturaDetailComponent } from './Viatura/viatura-detail/viatura-detail.component';
import { ViaturasComponent } from './Viatura/viaturas/viaturas.component';
import { SvListComponent } from './ServicoViatura/sv-list/sv-list.component';
import { SvDetailComponent } from './ServicoViatura/sv-detail/sv-detail.component';
import { ViagensComponent } from './Viagem/viagens/viagens.component';
import { ViagemDetailComponent } from './Viagem/viagem-detail/viagem-detail.component';
import { PassagemDetailComponent } from './Passagem/passagem-detail/passagem-detail.component';
import { ViagemCreateComponent } from './Viagem/viagem-create/viagem-create.component';
import { ViagemAdHocCreateComponent } from './Viagem/viagemadhoc-create/viagemadhoc-create.component';
import { PoliticaPrivacidadeComponent } from './RGPD/politica-privacidade/politica-privacidade.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SolucaoGeneticoComponent } from './Planeamento/solucao-genetico/solucao-genetico.component';
import { LoginComponent } from './login/login.component';
import { DataAdminGuard } from './guards/dataadmin.guard';
import { AuthGuard } from './guards/auth.guard';
import { UtilizadorGuard } from './guards/utilizador.guard';
import { AuthenticationService } from './authentication.service';
import { RegisterComponent } from './register/register.component';
import { SimNaoPipe } from './sim-nao.pipe';
import { SolucaoEscalonamentoComponent } from './Planeamento/solucao-escalonamento/solucao-escalonamento.component';



@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NgxSpinnerModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    //HttpClientInMemoryWebApiModule.forRoot(
    //  InMemoryDataService, { dataEncapsulation: false }
    //)
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [
    AppComponent,
    DashboardComponent,
    LinhasComponent,
    LinhaDetailComponent,
    BlocosComponent,
    BlocoDetailComponent,
    BlocoCreateComponent,
    MessagesComponent,
    TpvListComponent,
    TpvDetailComponent,
    NosComponent,
    NoDetailComponent,
    TpvSearchComponent,
    PercursosComponent,
    PercursoDetailComponent,
    SegmentoDetailComponent,
    TipoTripulanteDetailComponent,
    TiposTripulanteComponent,
    TripulanteDetailComponent,
    TripulantesComponent,
    ServicoTripulanteDetailComponent,
    ServicosTripulanteComponent,
    PercursoCreateComponent,
    SegmentoCreateComponent,
    ImportFileComponent,
    SolucaoMudancaMotoristaComponent,
    MapaRedeComponent,
    ViaturaDetailComponent,
    ViaturasComponent,
    SvListComponent,
    SvDetailComponent,
    ViagensComponent,
    ViagemDetailComponent,
    PassagemDetailComponent,
    ViagemCreateComponent,
    ViagemAdHocCreateComponent,
    PoliticaPrivacidadeComponent,
    SolucaoGeneticoComponent,
    LoginComponent,
    RegisterComponent,
    SimNaoPipe,
    SolucaoEscalonamentoComponent
  ],
  providers: [
    AuthGuard,
    DataAdminGuard,
    UtilizadorGuard,
    AuthenticationService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
