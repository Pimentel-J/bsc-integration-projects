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
import { ViagemCreateComponent} from './Viagem/viagem-create/viagem-create.component'
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

import { AuthGuard } from './guards/auth.guard'
import { DataAdminGuard } from './guards/dataadmin.guard'
import { UtilizadorGuard } from './guards/utilizador.guard'



const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },

  {
    path: 'tiposviatura', component: TpvListComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tiposviatura/:codigo', component: TpvDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tipoviatura', component: TpvDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tipoviatura/search', component: TpvSearchComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viaturas/:id', component: ViaturaDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viaturas', component: ViaturasComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viatura', component: ViaturaDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicosViatura', component: SvListComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicosViatura/:id', component: SvDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicoViatura', component: SvDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'linha/search', component: LinhaSearchComponent, canActivate:
      [AuthGuard, DataAdminGuard] },  
  {
    path: 'linhas/:codigo', component: LinhaDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'linhas', component: LinhasComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'linha', component: LinhaDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'blocos', component: BlocosComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'bloco', component: BlocoCreateComponent, canActivate:
      [AuthGuard, DataAdminGuard]  },
  {
    path: 'blocos/:codigo', component: BlocoDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'nos', component: NosComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'nos/:abreviatura', component: NoDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'no', component: NoDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'percursos', component: PercursosComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'percursos/:idPercurso', component: PercursoDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'percurso', component: PercursoCreateComponent, canActivate:
      [AuthGuard, DataAdminGuard]},
  {
    path: 'tiposTripulante', component: TiposTripulanteComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tiposTripulante/:codigo', component: TipoTripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tipoTripulante', component: TipoTripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tripulantes', component: TripulantesComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tripulantes/:numeroMecanografico', component: TripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'tripulante', component: TripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicosTripulante', component: ServicosTripulanteComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicosTripulante/:codigoServicoTripulante', component: ServicoTripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'servicoTripulante', component: ServicoTripulanteDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'importFile', component: ImportFileComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'algMudMot', component: SolucaoMudancaMotoristaComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'algGenetico', component: SolucaoGeneticoComponent, canActivate:
      [AuthGuard, DataAdminGuard]
  },
  {
    path: 'escalonamento', component: SolucaoEscalonamentoComponent, canActivate:
      [AuthGuard, DataAdminGuard]
  },
  {
    path: 'mapaRede', component: MapaRedeComponent, canActivate:
      [AuthGuard, UtilizadorGuard] },
  {
    path: 'viagens', component: ViagensComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viagens/:id', component: ViagemDetailComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viagem', component: ViagemCreateComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  {
    path: 'viagemadhoc', component: ViagemAdHocCreateComponent, canActivate:
      [AuthGuard, DataAdminGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
