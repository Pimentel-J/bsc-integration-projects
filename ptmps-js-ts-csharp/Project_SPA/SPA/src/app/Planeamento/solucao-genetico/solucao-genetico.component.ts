import { Component, OnInit } from '@angular/core';
import { SolucaoGenetico } from './solucao-genetico';
import { InputGenetico } from './input-genetico';
import { PlaneamentoService } from '../planeamento.service';
import { ServicoViatura } from '../../ServicoViatura/servicoviatura';
import { ServicoViaturaService } from '../../ServicoViatura/servicoviatura.service';
import { NgxSpinnerService } from "ngx-spinner";
import { ServicoTripulante } from '../../ServicoTripulante/servicoTripulante';
import { ServicoTripulanteService } from '../../ServicoTripulante/servicoTripulante.service';

@Component({
  selector: 'app-solucao-genetico',
  templateUrl: './solucao-genetico.component.html',
  styleUrls: ['./solucao-genetico.component.css']
})
export class SolucaoGeneticoComponent implements OnInit {

  solucaoVazio = {
    escalonamento: [],
    custo: null
  }

  solucao: SolucaoGenetico = this.solucaoVazio;

  inputVazio = {
    servicoViaturaID: null,
    escalonamentoMotoristas: null,
    numeroGeracoes: 100,
    tamanhoPopulacao: 5,
    probCruzamento: 75,
    probMutacao: 25,
    taxaPreservacao: 30,
    numeroGeracoesEstagnacao: 0,
    valorAlvo: 0,
    tempoLimite: 0,
    escrita: false  // valor fixo
  }

  input: InputGenetico = this.inputVazio;

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

  selectServicoViatura: ServicoViatura[];
  selectServicoTripulante: ServicoTripulante[];

  servicoViaturaVazio: ServicoViatura = {
    id: null,
    viaturaId: null,
    viagens: []
  }

  servicoTripulanteVazio: ServicoTripulante = {
    codigoServicoTripulante: null,
  }

  selectedServicosTripulante: string[] = [];
  selectedNrBlocos: string[] = [];

  constructor(
    private planeamentoService: PlaneamentoService,
    private servicoviaturaService: ServicoViaturaService,
    private servicoTripulanteService: ServicoTripulanteService,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.servicoviaturaService.getServicoViaturas()
      .subscribe(selectServicoViatura => { this.selectServicoViatura = [this.servicoViaturaVazio].concat(selectServicoViatura); });
    this.servicoTripulanteService.getServicosTripulante()
      .subscribe(selectServicoTripulante => { this.selectServicoTripulante = [this.servicoTripulanteVazio].concat(selectServicoTripulante); });
  }

  addST() {

    var novo = null;
    this.selectedServicosTripulante.push(novo);
    this.selectedNrBlocos.push(novo);

  }

  trackByIndex(index: number, obj: any): any {
    return index;
  }

  servicosTripulanteToString() {
    console.log(this.selectedNrBlocos);
    console.log(this.selectedServicosTripulante);
    var returnString = "[";
    for (var i = 0; i < this.selectedServicosTripulante.length; i++) {
      returnString = returnString + '("' + this.selectedServicosTripulante[i] + '",' + this.selectedNrBlocos[i] + '),';
    }
    returnString = returnString.slice(0, -1);
    returnString = returnString + "]";
    this.input.escalonamentoMotoristas = returnString;
  }


  geraSolucoes() {
    if (
      this.input.servicoViaturaID == null ||
      this.selectedServicosTripulante.length == 0 ||
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
      this.servicosTripulanteToString();
      console.log(this.input);
      this.spinner.show();
      this.planeamentoService.getSolucaoGenetico(this.input)
        .subscribe(sols => { this.solucao = sols; this.spinner.hide() });
    }
  }

}
