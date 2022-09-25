import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Percurso } from '../percurso';
import { PercursoService } from '../percurso.service';

import { LinhaService } from 'src/app/Linha/linha.service';

import { filter } from 'rxjs/operators';
import { Linha } from 'src/app/Linha/linha';


@Component({
  selector: 'app-percurso-detail',
  templateUrl: './percurso-detail.component.html',
  styleUrls: ['./percurso-detail.component.css']
})
export class PercursoDetailComponent implements OnInit {

  percurso: Percurso = {
    idLinha: null,
    idPercurso: null,
    ida: false,
    segmentos: []
  };
  selectLinhas: Linha[];
  
  criarPercurso: boolean;
  editable: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private percursoService: PercursoService,
    private linhaService: LinhaService,
    private location: Location
  ) { }

  ngOnInit(): void {

    const idPercurso = this.route.snapshot.paramMap.get('idPercurso');
    if (idPercurso) {
      console.log(idPercurso);
      this.getPercurso(idPercurso);
      this.criarPercurso = false;
    } else {
      this.percurso = {
        idLinha: null,
        idPercurso: null,
        ida: false,
        segmentos: []
      };
      this.linhaService.getLinhas()
        .subscribe(selectLinhas => { this.selectLinhas = selectLinhas; console.log(selectLinhas); });
      this.criarPercurso = true;
    }
  }

  /* istanbul ignore next */
  addSegmento() {
    this.percurso.segmentos.unshift({
      _id: null,
      ordem: null,
      noOrigem: null,
      noDestino: null,
      duracao: null,
      distancia: null
    });
  }

  getPercurso(idPercurso: string): void {
    
    this.percursoService.getPercurso(idPercurso)
      .subscribe(percurso => this.percurso = percurso);
    
  }

  /* istanbul ignore next */
  goBack(): void {
    this.location.back();
  }

  // save(): void {
  //   if (confirm('Guardar alterações ao percurso '+ this.percurso.idPercurso + '?')) {
  //     const idPercurso = this.route.snapshot.paramMap.get('idPercurso');
  //     console.log(this.percurso);
  //     this.percursoService.updatePercurso(this.percurso, idPercurso)
  //       .pipe(
  //         filter(
  //           (percurso: Percurso) => percurso != null
  //         )
  //       )
  //       .subscribe(
  //         () => this.goBack()
  //       );
  //   }
    
  // }

}
