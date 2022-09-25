import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Segmento } from '../segmento';
import { No } from 'src/app/No/no';
import { NoService } from 'src/app/No/no.service';

@Component({
  selector: 'app-segmento-detail',
  templateUrl: './segmento-detail.component.html',
  styleUrls: ['./segmento-detail.component.css']
})
export class SegmentoDetailComponent implements OnInit {
  
  // segmento: Segmento;

  @Input() segmento: Segmento;

  criarSegmento: boolean;
  editable: boolean = false;

  selectNos: No[];

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private noService: NoService
  ) { }

  // createSegmento(_id: string, ordem: number, duracao: number, distancia: number) {
  //   this.segmentoCreated.emit({
  //     _id: _id,
  //     ordem: ordem,
  //     noOrigem: null,
  //     noDestino: null,
  //     duracao: duracao,
  //     distancia: distancia
  //   })
  // }

  ngOnInit(): void {
    if (this.segmento._id != null) {
      this.criarSegmento = false;
    }
    else {
      this.segmento = {
        _id: null,
        ordem: null,
        noOrigem: null,
        noDestino: null,
        duracao: null,
        distancia: null
      };
      this.noService.getNos()
        .subscribe(selectNos => { this.selectNos = selectNos });
      this.criarSegmento = true;
    }
  }
}
  // getSegmento(_id: string): void {

  //   this.segmentoService.getSegmento(_id)
  //     .subscribe(segmento => this.segmento = segmento);

  // }

  // goBack(): void {
  //   this.location.back();
  // }

  // save(): void {
  //   if (confirm('Guardar alterações ao percurso ' + this.percurso.idPercurso + '?')) {
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

  // create() {
  //   if (confirm('Criar percurso ' + this.percurso.idPercurso + '?')) {
  //     this.percursoService.addPercurso(this.percurso)
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


