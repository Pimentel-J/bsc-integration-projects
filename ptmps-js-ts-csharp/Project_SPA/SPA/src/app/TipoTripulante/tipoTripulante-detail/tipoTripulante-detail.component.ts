import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { TipoTripulante } from '../tipoTripulante';
import { TipoTripulanteService } from '../tipoTripulante.service';

import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-tipoTripulante-detail',
  templateUrl: './tipoTripulante-detail.component.html',
  styleUrls: ['./tipoTripulante-detail.component.css']
})

export class TipoTripulanteDetailComponent implements OnInit {

  tipoTripulanteVazio = {
    codigo: null,
    descricao: null
  };
  tipoTripulante: TipoTripulante = this.tipoTripulanteVazio;
  criarTipoTripulante: boolean;

  constructor(
    private route: ActivatedRoute,
    private tipoTripulanteService: TipoTripulanteService,
    private location: Location
  ) { }

  ngOnInit(): void {
    // this.getTipoTripulante(codigo);
    const codigo = this.route.snapshot.paramMap.get('codigo');
    // console.log(codigo);
    if (codigo) {
      this.getTipoTripulante(codigo);
      this.criarTipoTripulante = false;
    } else {
      this.criarTipoTripulante = true;
    }
  }

  /**
   * Obter o Tipo de Tripulante
   * @param codigo 
   */
  getTipoTripulante(codigo: string): void {
    // const codigo = this.route.snapshot.paramMap.get('codigo');
    this.tipoTripulanteService.getTipoTripulante(codigo)
      .subscribe(tipoTripulante => this.tipoTripulante = tipoTripulante);
    // console.log(this.tipoTripulante);
  }

  goBack(): void {
    this.location.back();
  }

  /**
   * Criar o Tipo de Tripulante
   */
  create() {
    if (confirm('Criar Tipo de Tripulante ' + this.tipoTripulante.codigo + '?')) {
      this.tipoTripulanteService.addTipoTripulante(this.tipoTripulante)
        .pipe(
          filter(
            (tipoTripulante: TipoTripulante) => tipoTripulante != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
  }

  /**
   * Atualizar dados do Tipo de Tripulante
   */
  save(): void {
    if (confirm('Guardar alterações ao Tipo de Tripulante ' + this.tipoTripulante.codigo + '?')) {
      const codigo = this.route.snapshot.paramMap.get('codigo');
      // console.log(this.tipoTripulante);
      this.tipoTripulanteService.updateTipoTripulante(this.tipoTripulante, codigo)
        .pipe(
          filter(
            (tipoTripulante: TipoTripulante) => tipoTripulante != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
  }
}
