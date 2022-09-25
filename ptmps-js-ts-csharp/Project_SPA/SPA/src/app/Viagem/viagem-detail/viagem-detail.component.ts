import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { TimeUtils } from '../../utils/time';

import { ViagemDTO } from '../viagemDTO';
import { ViagemService } from '../viagem.service';

//import { PassagemService } from 'src/app/Passagem/passagem.service';

import { filter } from 'rxjs/operators';
import { Passagem } from '../../Passagem/passagem';
import { PassagemService } from '../../Passagem/passagem.service'


@Component({
  selector: 'app-viagem-detail',
  templateUrl: './viagem-detail.component.html',
  styleUrls: ['./viagem-detail.component.css']
})
export class ViagemDetailComponent implements OnInit {

  viagem: ViagemDTO = {
    id: null,
    servicoViaturaId: null,
    percursoId: null,
    descritivo: null,
    horaInicio: null,
    horaFim: null,
    passagens: []
  };
  selectPassagens: Passagem[] = [];
  
  criarViagem: boolean;
  editable: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private viagemService: ViagemService,
    private passagemService: PassagemService,
    private location: Location,
    private timeUtils: TimeUtils
  ) { }

  ngOnInit(): void {

    const idViagem = this.route.snapshot.paramMap.get('id');
    if (idViagem) {
      console.log(idViagem);
      this.getViagem(idViagem);
      this.criarViagem = false;
     
      
    }
    }

  timeMask(prop: number) {
    return this.timeUtils.seconds2timeValue(prop);
  }
  
  getViagem(idViagem: string): void {
    
    this.viagemService.getViagem(idViagem)
      .subscribe(viagem => {
        this.viagem = viagem;
        console.log(this.viagem);
        this.viagem.passagens.forEach(p => {
          this.getPassagemFromId(p);
        }) });
    
  }

  getPassagemFromId(passagem: string): void {
    this.passagemService.getPassagem(passagem)
      .subscribe(pass => { this.selectPassagens.push(pass); console.log(this.selectPassagens) });
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
