import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Tripulante } from '../tripulante';
import { TripulanteService } from '../tripulante.service';
import { TipoTripulante } from '../../TipoTripulante/tipoTripulante';
import { TipoTripulanteService } from '../../TipoTripulante/tipoTripulante.service';

import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-tripulante-detail',
  templateUrl: './tripulante-detail.component.html',
  styleUrls: ['./tripulante-detail.component.css']
})

export class TripulanteDetailComponent implements OnInit {

  tripulanteVazio = {
    id: null,
    numeroMecanografico: null,
    nome: null,
    dataNascimento: null,
    numeroCartaoCidadao: null,
    nif: null,
    numeroCartaConducao: null,
    dataEmissaoLicencaConducao: null,
    dataValidadeLicencaConducao: null,
    tipoTripulante: null,
    dataEntradaEmpresa: null,
    dataSaidaEmpresa: null
  };

  tripulante: Tripulante = this.tripulanteVazio;
  criarTripulante: boolean;
  tiposTripulante: TipoTripulante[];

  constructor(
    private route: ActivatedRoute,
    private tripulanteService: TripulanteService,
    private tipoTripulanteService: TipoTripulanteService,
    private location: Location
  ) { }

  ngOnInit(): void {
    // this.getTripulante(numeroMecanografico);
    const numeroMecanografico = this.route.snapshot.paramMap.get('numeroMecanografico');
    // console.log(numeroMecanografico);
    if (numeroMecanografico) {
      this.getTripulante(numeroMecanografico);
      this.criarTripulante = false;
    } else {
      this.criarTripulante = true;
    }

    this.tipoTripulanteService.getTiposTripulante()
      .subscribe(tiposTripulante => { this.tiposTripulante = tiposTripulante; console.log(this.tiposTripulante); });

  }

  /**
   * Obter o Tripulante
   * @param numeroMecanografico 
   */
  getTripulante(numeroMecanografico: string): void {
    // const numeroMecanografico = this.route.snapshot.paramMap.get('numeroMecanografico');
    this.tripulanteService.getTripulante(numeroMecanografico)
      .subscribe(tripulante => this.tripulante = tripulante);
    // console.log(this.tripulante);
  }

  goBack(): void {
    this.location.back();
  }

  /**
   * Criar o Tripulante
   */
  create() {
    if (confirm('Criar Tripulante ' + this.tripulante.numeroMecanografico + '?')) {
      this.tripulanteService.addTripulante(this.tripulante)
        .pipe(
          filter(
            (tripulante: Tripulante) => tripulante != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
  }

  /**
   * Atualizar dados do Tripulante
   */
  save(): void {
    if (confirm('Guardar alterações do Tripulante ' + this.tripulante.numeroMecanografico + '?')) {
      const numeroMecanografico = this.route.snapshot.paramMap.get('numeroMecanografico');
      // console.log(this.tripulante);
      this.tripulanteService.updateTripulante(this.tripulante, numeroMecanografico)
        .pipe(
          filter(
            (tripulante: Tripulante) => tripulante != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
  }
}
