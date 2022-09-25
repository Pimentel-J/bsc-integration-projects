import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { CreatingViagemDTO } from '../creatingViagemDTO';
import { ViagemService } from '../viagem.service';

import { PercursoService } from '../../Percurso/percurso.service';

import { filter } from 'rxjs/operators';
import { Percurso } from '../../Percurso/percurso';
import { TimeUtils } from 'src/app/utils/time';


@Component({
  selector: 'app-viagemadhoc-create',
  templateUrl: './viagemadhoc-create.component.html',
  styleUrls: ['./viagemadhoc-create.component.css']
})
export class ViagemAdHocCreateComponent implements OnInit {

  percursoVazioIda = {
    idLinha: null,
    idPercurso: null,
    ida: false,
    segmentos: []
  };

  percursoVazioVolta = {
    idLinha: null,
    idPercurso: null,
    ida: false,
    segmentos: []
  };

  
  percursoIda: Percurso = this.percursoVazioIda;
  percursoVolta: Percurso = this.percursoVazioVolta;

  selectPercursosIda: Percurso[] = [];

  selectPercursosVolta: Percurso[] = [];

  creatingViagemDTO: CreatingViagemDTO = {
    HoraInicio: null,
    PercursoId: null,
    PercursoIdaId: null,
    PercursoVoltaId: null,
    Frequencia: null,
    NViagens: null
  };

  horaInicialFormatada: string;
  selectLinha: string;

  constructor(
    private route: ActivatedRoute,
    private percursoService: PercursoService,
    private viagemService: ViagemService,
    private location: Location,
    private timeUtils: TimeUtils
  ) { }

  ngOnInit(): void {

    this.creatingViagemDTO.Frequencia = 0, this.creatingViagemDTO.PercursoIdaId = null,
      this.creatingViagemDTO.PercursoVoltaId = null, this.creatingViagemDTO.NViagens = 0;
    this.percursoService.getPercursos()
      .subscribe(selectPercursosIda => {
        this.selectPercursosIda = [this.percursoVazioIda].concat(selectPercursosIda);
        console.log(selectPercursosIda)
      });
    
  }
  
  // atualizarPercursosVolta(): void{
  //   this.percursoService.getPercursosIdaVolta("false", this.selectLinha)
  //     .subscribe(selectPercursosVolta => { this.selectPercursosVolta = [this.percursoVazioVolta].concat(selectPercursosVolta) });
  // }

  goBack(): void {
    this.location.back();
  }

  refreshHoraInicial() {
    this.horaInicialFormatada = this.timeUtils.seconds2timeValue(this.creatingViagemDTO.HoraInicio);
  }

  // onChange(percurso: string) {
  //   this.selectLinha = this.selectPercursosIda.find(x => x.idPercurso == percurso).idLinha.codigo;
  //   this.atualizarPercursosVolta();
  // }

  create() {
    if (confirm('Criar Viagem do Percurso ' + this.creatingViagemDTO.PercursoId + ' a comecar às ' +
      this.creatingViagemDTO.HoraInicio + '?')) {
      this.viagemService.addViagem(this.creatingViagemDTO)
        .pipe(
          filter(
            creatingViagens => creatingViagens != null
          )
        )
        .subscribe((viagem) => {
          console.log(viagem);
          this.goBack();
        }
           
        );
    }

  }

}
