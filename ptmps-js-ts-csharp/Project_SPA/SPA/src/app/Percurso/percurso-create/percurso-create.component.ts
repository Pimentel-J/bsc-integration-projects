import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { PercursoDTO } from '../percursoDTO';
import { PercursoService } from '../percurso.service';

import { LinhaService } from 'src/app/Linha/linha.service';

import { filter } from 'rxjs/operators';
import { Linha } from 'src/app/Linha/linha';


@Component({
  selector: 'app-percurso-create',
  templateUrl: './percurso-create.component.html',
  styleUrls: ['./percurso-create.component.css']
})
export class PercursoCreateComponent implements OnInit {

  linhaVazia = {
    codigo: null,
    nome: null,
    permissaoViatura: null,
    permissaoMotorista: null,
    noFinal: null,
    cor: null
  };

  linha: Linha = this.linhaVazia;

  selectLinhas: Linha[];

  percursoDTO: PercursoDTO = {
    linha: null,
    idPercurso: null,
    ida: false,
    segmentos: []
  };

  constructor(
    private route: ActivatedRoute,
    private percursoService: PercursoService,
    private linhaService: LinhaService,
    private location: Location
  ) { }

  ngOnInit(): void {

      this.linhaService.getLinhas()
        .subscribe(selectLinhas => { this.selectLinhas = [this.linha].concat(selectLinhas) });
    
  }
  
  /* istanbul ignore next */
  addSegmento() {
    this.percursoDTO.segmentos.unshift({
      ordem: null,
      noOrigem: null,
      noDestino: null,
      duracao: null,
      distancia: null
    });
  }


  goBack(): void {
    this.location.back();
  }


  create() {
    if (confirm('Criar percurso ' + this.percursoDTO.idPercurso + '?')) {
      this.percursoService.addPercurso(this.percursoDTO)
        .pipe(
          filter(
            (percursoDTO: PercursoDTO) => percursoDTO != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }

  }

}
