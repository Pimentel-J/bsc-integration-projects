import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { No } from '../no';
import { NoService } from '../no.service';

import { filter } from 'rxjs/operators';
import { modelos } from 'src/assets/modelos.json';

@Component({
  selector: 'app-no-detail',
  templateUrl: './no-detail.component.html',
  styleUrls: ['./no-detail.component.css']
})
export class NoDetailComponent implements OnInit {

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

  criarNo: boolean;

  selectModelosMapa: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private noService: NoService,
    private location: Location
  ) { }

  ngOnInit(): void {

    const abreviatura = this.route.snapshot.paramMap.get('abreviatura');
    if (abreviatura) {
      this.getNo(abreviatura);
      this.criarNo = false;
    } else {
      this.criarNo = true;
    }
    this.getModelosMapa();
    
  }

  getNo(abreviatura: string): void {
    
    this.noService.getNo(abreviatura)
      .subscribe(no => this.no = no);
    
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (confirm('Guardar alterações ao nó '+ this.no.abreviatura + '?')) {
      const abreviatura = this.route.snapshot.paramMap.get('abreviatura');
      console.log(this.no);
      this.noService.updateNo(this.no, abreviatura)
        .pipe(
          filter(
            (no: No) => no != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
    
  }

  create() {
    if (confirm('Criar nó ' + this.no.abreviatura + '?')) {
      this.noService.addNo(this.no)
        .pipe(
          filter(
            (no: No) => no != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
    
  }

  getModelosMapa() {
    this.selectModelosMapa = [null].concat(modelos);
    //this.selectModelosMapa.unshift(null);
  }

}
