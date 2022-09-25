import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

import { TpvListComponent } from './tipoviatura/tpv-list/tpv-list.component';
import { TpvDetailComponent } from './tipoviatura/tpv-detail/tpv-detail.component';

import { SvListComponent } from './ServicoViatura/sv-list/sv-list.component';
import { SvDetailComponent } from './ServicoViatura/sv-detail/sv-detail.component';

import { ViaturasComponent } from './Viatura/viaturas/viaturas.component'
import { ViaturaDetailComponent } from './Viatura/viatura-detail/viatura-detail.component'

import { LinhasComponent } from './Linha/linhas/linhas.component'
import { LinhaDetailComponent } from './Linha/linha-detail/linha-detail.component'
import { LinhaSearchComponent } from './Linha/linha-search/linha-search.component'

import { BlocosComponent } from './Bloco/blocos/blocos.component'
import { BlocoDetailComponent } from './Bloco/bloco-detail/bloco-detail.component'
import { BlocoCreateComponent } from './Bloco/bloco-create/bloco-create.component'

import { NosComponent } from './No/nos/nos.component'
import { NoDetailComponent } from './No/no-detail/no-detail.component'
import { TpvSearchComponent } from './tipoviatura/tpv-search/tpv-search.component';

import { PercursosComponent } from './Percurso/percursos/percursos.component'
import { PercursoDetailComponent } from './Percurso/percurso-detail/percurso-detail.component'
import { PercursoCreateComponent } from './Percurso/percurso-create/percurso-create.component'

import { ViagensComponent } from './Viagem/viagens/viagens.component'
import { ViagemDetailComponent } from './Viagem/viagem-detail/viagem-detail.component'
import { ViagemCreateComponent } from './Viagem/viagem-create/viagem-create.component'
import { ViagemAdHocCreateComponent } from './Viagem/viagemadhoc-create/viagemadhoc-create.component'

import { TipoTripulanteDetailComponent } from './TipoTripulante/tipoTripulante-detail/tipoTripulante-detail.component'
import { TiposTripulanteComponent } from './TipoTripulante/tiposTripulante/tiposTripulante.component'

import { TripulanteDetailComponent } from './Tripulante/tripulante-detail/tripulante-detail.component'
import { TripulantesComponent } from './Tripulante/tripulantes/tripulantes.component'

import { ServicoTripulanteDetailComponent } from './ServicoTripulante/servicoTripulante-detail/servicoTripulante-detail.component'
import { ServicosTripulanteComponent } from './ServicoTripulante/servicosTripulante/servicosTripulante.component'

import { ImportFileComponent } from './import-file/import-file.component'

import { SolucaoMudancaMotoristaComponent } from './Planeamento/solucao-mudanca-motorista/solucao-mudanca-motorista.component'
import { SolucaoGeneticoComponent } from './Planeamento/solucao-genetico/solucao-genetico.component'
import { SolucaoEscalonamentoComponent } from './Planeamento/solucao-escalonamento/solucao-escalonamento.component'

import { MapaRedeComponent } from './mapa-rede/mapa-rede.component'

import { LoginComponent } from './login/login.component'
import { RegisterComponent } from './register/register.component'

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },

  { path: 'tiposviatura', component: TpvListComponent },
  { path: 'tiposviatura/:codigo', component: TpvDetailComponent },
  { path: 'tipoviatura', component: TpvDetailComponent },
  { path: 'tipoviatura/search', component: TpvSearchComponent },
  { path: 'viaturas/:id', component: ViaturaDetailComponent },
  { path: 'viaturas', component: ViaturasComponent },
  { path: 'viatura', component: ViaturaDetailComponent },
  { path: 'servicosViatura', component: SvListComponent },
  { path: 'servicosViatura/:id', component: SvDetailComponent },
  { path: 'servicoViatura', component: SvDetailComponent },
  { path: 'linha/search', component: LinhaSearchComponent },
  { path: 'linhas/:codigo', component: LinhaDetailComponent },
  { path: 'linhas', component: LinhasComponent },
  { path: 'linha', component: LinhaDetailComponent },
  { path: 'blocos', component: BlocosComponent },
  { path: 'bloco', component: BlocoCreateComponent },
  { path: 'blocos/:codigo', component: BlocoDetailComponent },
  { path: 'nos', component: NosComponent },
  { path: 'nos/:abreviatura', component: NoDetailComponent },
  { path: 'no', component: NoDetailComponent },
  { path: 'percursos', component: PercursosComponent },
  { path: 'percursos/:idPercurso', component: PercursoDetailComponent },
  { path: 'percurso', component: PercursoCreateComponent },
  { path: 'tiposTripulante', component: TiposTripulanteComponent },
  { path: 'tiposTripulante/:codigo', component: TipoTripulanteDetailComponent },
  { path: 'tipoTripulante', component: TipoTripulanteDetailComponent },
  { path: 'tripulantes', component: TripulantesComponent },
  { path: 'tripulantes/:numeroMecanografico', component: TripulanteDetailComponent },
  { path: 'tripulante', component: TripulanteDetailComponent },
  { path: 'servicosTripulante', component: ServicosTripulanteComponent },
  { path: 'servicosTripulante/:codigoServicoTripulante', component: ServicoTripulanteDetailComponent },
  { path: 'servicoTripulante', component: ServicoTripulanteDetailComponent },
  { path: 'importFile', component: ImportFileComponent },
  { path: 'algMudMot', component: SolucaoMudancaMotoristaComponent },
  { path: 'algGenetico', component: SolucaoGeneticoComponent },
  { path: 'escalonamento', component: SolucaoEscalonamentoComponent },
  { path: 'mapaRede', component: MapaRedeComponent },
  { path: 'viagens', component: ViagensComponent },
  { path: 'viagens/:id', component: ViagemDetailComponent },
  { path: 'viagem', component: ViagemCreateComponent },
  { path: 'viagemadhoc', component: ViagemAdHocCreateComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
