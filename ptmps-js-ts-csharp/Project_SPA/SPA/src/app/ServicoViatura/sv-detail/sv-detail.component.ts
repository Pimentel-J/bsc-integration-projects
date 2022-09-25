import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { filter } from 'rxjs/operators';

import { ServicoViatura } from '../../ServicoViatura/servicoviatura';
import { ServicoViaturaService } from '../servicoviatura.service';

import { ViagemDTO } from '../../Viagem/viagemDTO';
import { ViagemService } from '../../Viagem/viagem.service';

import { Viatura } from '../../Viatura/viatura';
import { ViaturaService } from '../../Viatura/viatura.service';

import { TimeUtils } from '../../utils/time';



@Component({
  selector: 'app-sv-detail',
  templateUrl: './sv-detail.component.html',
  styleUrls: ['./sv-detail.component.css']
})
export class SvDetailComponent implements OnInit {

  servicoViaturaVazio = {
    id: null,
    viaturaId: null,
    viagens: []
  };

  servicoViatura: ServicoViatura = this.servicoViaturaVazio;

  viagemVazia: ViagemDTO = {
    id: null,
    percursoId: null,
    servicoViaturaId: null,
    descritivo: null,
    horaInicio: null,
    horaFim: null,
    passagens: []
  }

  selectViagens: ViagemDTO[] = [];
  selectViaturas: Viatura[] = [];

  criarServicoViatura: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private servicoviaturaService: ServicoViaturaService,
    private viagemService: ViagemService,
    private viaturaService: ViaturaService,
    private location: Location,
    private timeUtils : TimeUtils


  ) { }

  ngOnInit(): void {
    const idServViat = this.route.snapshot.paramMap.get('id');
    console.log(idServViat);

    if (idServViat) {

      this.getServicoViatura(idServViat);
      this.criarServicoViatura = false;

    } else {

      this.criarServicoViatura = true;

      this.viagemService.getViagensSemServico()
        .subscribe(selectViagens => {
          this.selectViagens = selectViagens;
        });

      this.viaturaService.getViaturas()
        .subscribe(selectViaturas => { this.selectViaturas = selectViaturas; console.log(selectViaturas); });

    }

  }

  getServicoViatura(idServViat: string): void {

    this.servicoviaturaService.getServicoViatura(idServViat)
      .subscribe(servicoViatura => {
        this.servicoViatura = servicoViatura;
        console.log(this.servicoViatura);
        // Se a vista é de Alteração (e não de Criação), as possibilidades de escolha das viagens são as próprias viagens do Serviço de Viatura selecionado
        for (var i = 0; i < this.servicoViatura.viagens.length; i++) {
          this.viagemService.getViagem(this.servicoViatura.viagens[i]).subscribe(viagem => {
            this.selectViagens.push(viagem);
            this.selectViagens.sort((a, b) => a.horaInicio - b.horaInicio);
          })
        }
       
        console.log(this.selectViagens);
        // Idem para a viatura
        this.viaturaService.getViatura(this.servicoViatura.viaturaId).subscribe(viatura => { this.selectViaturas.unshift(viatura) })
      });

  }

  sortBy(prop: number) {
      return this.selectViagens.sort((a, b) => a.horaInicio-b.horaInicio);
  }

  
  timeMask(prop: number) {
    return this.timeUtils.seconds2timeValue(prop);
  }
  
  addViagem() {

    var novaViagem = null;
    this.servicoViatura.viagens.push(novaViagem);

  }

  /* istanbul ignore next */
  trackByIndex(index: number, obj: any): any {
    return index;
  }

  goBack(): void {
    this.location.back();
  }
  /* istanbul ignore next */
  save(): void {
    //if (confirm('Guardar alterações do serviço de viatura ' + this.servicoViatura.id + '?')) {
    //  const codigo = this.route.snapshot.paramMap.get('id');
    //  this.servicoviaturaService..updateViatura(this.viatura, codigo)
    //     .pipe(
    //       filter(
    //         (viatura: Viatura) => viatura != null
    //       )
    //     )
    //     .subscribe(
    //       () => this.goBack()
    //     );
    // }

  }

  create() {

    if (!this.camposValidos()) {
      return;
    }

    if (confirm('Criar Servico de Viatura ' + this.servicoViatura.id + '?')) {

      this.servicoviaturaService.addServicoViatura(this.servicoViatura)
        .pipe(
          filter(
            (servicoviatura: ServicoViatura) => servicoviatura != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }

  }
  /* istanbul ignore next */
  camposValidos(): boolean {

    if (this.servicoViatura.viagens.length == 0) {
      alert('Deve selecionar pelo menos uma viagem!');
      return false;
    }
    for (var i = 0; i < this.servicoViatura.viagens.length; i++) {
      if (this.servicoViatura.viagens[i] == null) {
        alert('Todas as viagens devem ser preenchidas');
        return false;
      }
    }
    if (this.servicoViatura.id == null) {
      alert('Deve introduzir um ID (código) para o Serviço de Viatura');
      return false;
    }
    /*if (this.servicoViatura.viaturaId == null) {
      alert('Deve introduzir uma matrícula para o Serviço de Viatura');
      return false;
    }*/
    return true;

  }



}
