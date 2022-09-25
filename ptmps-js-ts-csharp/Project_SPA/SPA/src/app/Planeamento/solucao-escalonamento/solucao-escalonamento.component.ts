import { Component, OnInit } from '@angular/core';
import { SolucaoEscalonamento } from './solucao-escalonamento';
import { InputEscalonamento } from './input-escalonamento';
import { PlaneamentoService } from '../planeamento.service';
import { ServicoViatura } from '../../ServicoViatura/servicoviatura';
import { ServicoViaturaService } from '../../ServicoViatura/servicoviatura.service';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-solucao-escalonamento',
  templateUrl: './solucao-escalonamento.component.html',
  styleUrls: ['./solucao-escalonamento.component.css']
})
export class SolucaoEscalonamentoComponent implements OnInit {

  solucaoVazio = {
    escalonamento: [],
    alertas: null
  }

  solucao: SolucaoEscalonamento = this.solucaoVazio;

  inputVazio = {
    numeroGeracoes: 5,
    tamanhoPopulacao: 5,
    probCruzamento: 75,
    probMutacao: 25,
    taxaPreservacao: 30,
    numeroGeracoesEstagnacao: 0,
    valorAlvo: 0,
    tempoLimite: 0,
    escrita: false  // valor fixo
  }

  input: InputEscalonamento = this.inputVazio;

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

  constructor(
    private planeamentoService: PlaneamentoService,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
  }

  geraSolucoes() {
    if (
      this.input.numeroGeracoes == null ||
      this.input.tamanhoPopulacao == null ||
      this.input.probCruzamento == null ||
      this.input.probMutacao == null ||
      this.input.taxaPreservacao == null ||
      this.input.numeroGeracoesEstagnacao == null ||
      this.input.valorAlvo == null ||
      this.input.tempoLimite == null
    ) {
      alert('Por favor, preencha todos os campos!');
    } else {
      this.spinner.show();
      this.planeamentoService.getSolucaoEscalonamento(this.input)
        .subscribe(sols => { this.solucao = sols; this.spinner.hide() });
    }
  }

}
