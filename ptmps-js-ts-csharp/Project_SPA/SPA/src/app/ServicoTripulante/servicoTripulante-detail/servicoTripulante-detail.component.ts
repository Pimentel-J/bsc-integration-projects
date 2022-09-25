import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ServicoTripulante } from '../servicoTripulante';
import { ServicoTripulanteService } from '../servicoTripulante.service';

import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-servicoTripulante-detail',
  templateUrl: './servicoTripulante-detail.component.html',
  styleUrls: ['./servicoTripulante-detail.component.css']
})

export class ServicoTripulanteDetailComponent implements OnInit {

  servicoTripulanteVazio = {
    codigoServicoTripulante: null
  };
  servicoTripulante: ServicoTripulante = this.servicoTripulanteVazio;
  criarServicoTripulante: boolean;

  constructor(
    private route: ActivatedRoute,
    private servicoTripulanteService: ServicoTripulanteService,
    private location: Location
  ) { }

  ngOnInit(): void {
    // this.getServicoTripulante(codigoServicoTripulante);
    const codigoServicoTripulante = this.route.snapshot.paramMap.get('codigoServicoTripulante');
    // console.log(codigoServicoTripulante);
    if (codigoServicoTripulante) {
      this.getServicoTripulante(codigoServicoTripulante);
      this.criarServicoTripulante = false;
    } else {
      this.criarServicoTripulante = true;
    }
  }

  /**
   * Obter o Serviço de Tripulante
   * @param codigoServicoTripulante 
   */
  getServicoTripulante(codigoServicoTripulante: string): void {
    // const codigoServicoTripulante = this.route.snapshot.paramMap.get('codigoServicoTripulante');
    this.servicoTripulanteService.getServicoTripulante(codigoServicoTripulante)
      .subscribe(servicoTripulante => this.servicoTripulante = servicoTripulante);
    // console.log(this.servicoTripulante);
  }

  goBack(): void {
    this.location.back();
  }

  /**
   * Criar o Serviço de Tripulante
   */
  create() {
    if (confirm('Criar Serviço de Tripulante ' + this.servicoTripulante.codigoServicoTripulante + '?')) {
      this.servicoTripulanteService.addServicoTripulante(this.servicoTripulante)
        .pipe(
          filter(
            (servicoTripulante: ServicoTripulante) => servicoTripulante != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
  }

}
