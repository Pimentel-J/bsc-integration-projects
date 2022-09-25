import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { SegmentoDTO } from '../segmentoDTO';
import { No } from 'src/app/No/no';
import { NoService } from 'src/app/No/no.service';

@Component({
  selector: 'app-segmento-create',
  templateUrl: './segmento-create.component.html',
  styleUrls: ['./segmento-create.component.css']
})
export class SegmentoCreateComponent implements OnInit {

  @Input() segmentoDTO: SegmentoDTO;

  selectNos: No[];

  noVazio = {
    abreviatura: null,
    nome: null,
    latitude: null,
    longitude: null,
    estacaoRecolha: false,
    pontoRendicao: false,
    modeloMapa: null
  };

  no: No = this.noVazio;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private noService: NoService
  ) { }


  ngOnInit(): void {
      
      this.noService.getNos()
        .subscribe(selectNos => { this.selectNos = [this.no].concat(selectNos) });
    }
  }

