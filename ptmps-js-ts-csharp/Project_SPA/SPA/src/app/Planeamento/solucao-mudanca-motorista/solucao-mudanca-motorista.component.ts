import { Component, OnInit } from '@angular/core';
import { No } from 'src/app/No/no';
import { NoService } from 'src/app/No/no.service';
import { SolucoesMudancaMotorista } from './solucoes-mudanca-motorista';
import { PlaneamentoService } from '../planeamento.service';
import { TimeUtils } from 'src/app/utils/time';
import { Time } from '@angular/common';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-solucao-mudanca-motorista',
  templateUrl: './solucao-mudanca-motorista.component.html',
  styleUrls: ['./solucao-mudanca-motorista.component.css']
})
export class SolucaoMudancaMotoristaComponent implements OnInit {

  solucoesVazio = {
    noOrigem: null,
    noDestino: null,
    horaInicial: null,
    duracao: null,
    caminho: null
  }

  solucoes: SolucoesMudancaMotorista = this.solucoesVazio;

  horaInicialFormatada: string;
  duracaoFormatada: string;

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

  selectNos: No[];

  constructor(
    private planeamentoService: PlaneamentoService,
    private noService: NoService,
    private timeUtils: TimeUtils,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.noService.getNos()
      .subscribe(selectNos => {
        this.selectNos = [this.no].concat(selectNos)
      });
  }

  geraSolucoes() {
    if (this.solucoes.noOrigem == null || this.solucoes.noDestino == null || this.solucoes.horaInicial == null) {
      alert('Por favor, preencha todos os campos!');
    } else {
      this.spinner.show();
      this.planeamentoService.getSolucaoMudMot(this.solucoes)
        .subscribe(sols => { this.solucoes = sols; this.refreshDuracao(); this.spinner.hide() });
    }
  }

  refreshHoraInicial() {
    this.horaInicialFormatada = this.timeUtils.seconds2timeValue(this.solucoes.horaInicial);
  }
  refreshDuracao() {
    this.duracaoFormatada = this.timeUtils.seconds2timeValue(this.solucoes.duracao);
  }

}
