import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Bloco } from '../bloco';
import { BlocoService } from '../bloco.service';
import { filter } from 'rxjs/operators';
import { ServicoViatura } from '../../ServicoViatura/servicoviatura';
import { ServicoViaturaService } from '../../ServicoViatura/servicoviatura.service';
import { ViagemService } from '../../Viagem/viagem.service';
import { PassagemService } from '../../Passagem/passagem.service';
import { ViagemDTO } from '../../Viagem/ViagemDTO';
import { Passagem } from '../../Passagem/passagem';
import { TimeUtils } from '../../utils/time';


@Component({
  selector: 'app-bloco-create',
  templateUrl: './bloco-create.component.html',
  styleUrls: ['./bloco-create.component.css']
})


export class BlocoCreateComponent implements OnInit {

  blocoVazio: Bloco = {
    codigo: null,
    startTime: 0,
    endTime: 0,
    servicoMotoristaId: null,
    servicoViaturaId: null,
    viagens: []
  };
  bloco: Bloco = this.blocoVazio;

  servicoViaturaVazio: ServicoViatura = {
    id: null,
    viaturaId: null,
    viagens: []
  }

  passagemVazia: Passagem = {
  id: null,
  viagemId: null,
  horaPassagem: null,
  abreviaturaNo: null
}

  selectServicoViatura: ServicoViatura[];
  selectedServicoViatura: string = null;
  selectViagens: ViagemDTO[] = [];
  selectPassagensIniciais: Passagem[] = [];
  selectPassagensFinal: Passagem[] = [];

  constructor(
    private route: ActivatedRoute,
    private blocoService: BlocoService,
    private location: Location,
    private servicoviaturaService: ServicoViaturaService,
    private viagemService: ViagemService,
    private timeUtils: TimeUtils,
    private passagemService: PassagemService
  ) { }

  ngOnInit(): void {

    this.servicoviaturaService.getServicoViaturas()
      .subscribe(selectServicoViatura => { this.selectServicoViatura = [this.servicoViaturaVazio].concat(selectServicoViatura); });
  }


  goBack(): void {
    this.location.back();
  }

  addViagem() {

    var novaViagem = null;
    this.bloco.viagens.push(novaViagem);

  }

  create() {
    if (confirm('Criar blocos para o serviço ' + this.bloco.servicoViaturaId + '?')) {
      this.blocoService.addBloco(this.bloco)
        .pipe(
          filter(
            (bloco: Bloco) => bloco != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }

  }

  trackByIndex(index: number, obj: any): any {
    return index;
  }

  timeMask(prop: number) {
    return this.timeUtils.seconds2timeValue(prop);
  }

  atualizarSelectViagens() {
    this.viagemService.getViagensByServicoViatura(this.selectedServicoViatura)
      .subscribe(selectViagens => {
        this.selectViagens = selectViagens;
      });
  }

  changeSV(servicoViaturaID: string) {
    this.selectedServicoViatura = servicoViaturaID;
    this.atualizarSelectViagens();
  }

  changeViagens() {
    var vazia = null;
    this.passagemService.getPassagensPontosTrocaDeViagem(this.bloco.viagens[0])
      .subscribe(ret => { this.selectPassagensIniciais = [this.passagemVazia].concat(ret); console.log(this.selectPassagensIniciais) });
    this.passagemService.getPassagensPontosTrocaDeViagem(this.bloco.viagens[this.bloco.viagens.length - 1])
      .subscribe(ret => { this.selectPassagensFinal = [this.passagemVazia].concat(ret); console.log(this.selectPassagensFinal) });
  }

}
