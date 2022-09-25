import { Component, OnInit } from '@angular/core';
import { Linha } from '../Linha/linha';
import { LinhaService } from '../Linha/linha.service';
import { Tipoviatura } from '../tipoviatura/tipoviatura';
import { TipoviaturaService } from '../tipoviatura/tipoviatura.service';
import { TipoTripulante } from '../TipoTripulante/tipoTripulante';
import { TipoTripulanteService } from '../TipoTripulante/tipoTripulante.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  //linhas: Linha[] = [];
  //tiposviatura: Tipoviatura[] = [];
  //tiposTripulante: TipoTripulante[] = [];

  constructor(/*private linhaService: LinhaService, private tpvService: TipoviaturaService, 
    private tipoTripulanteService: TipoTripulanteService*/) { }

  ngOnInit() {
    //this.getLinhas();
    //this.getTiposViatura();
    //this.getTiposTripulante();
  }

  //getLinhas(): void {
  //  this.linhaService.getLinhas()
  //    .subscribe(linhas => this.linhas = linhas.slice(1, 5));
  //}

  //getTiposViatura(): void {
  //  this.tpvService.getTiposviatura().subscribe(tiposviatura => this.tiposviatura = tiposviatura.slice(1, 3));
  //}

  //getTiposTripulante(): void {
  //  this.tipoTripulanteService.getTiposTripulante().subscribe(tiposTripulante => this.tiposTripulante = tiposTripulante.slice(1, 2));
  //}
}
