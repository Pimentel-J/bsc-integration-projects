import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Bloco } from '../bloco';
import { BlocoService } from '../bloco.service';
import { filter } from 'rxjs/operators';
import { ServicoViatura } from 'src/app/ServicoViatura/servicoviatura';
import { ServicoViaturaService } from '../../ServicoViatura/servicoviatura.service';
import { ViagemDTO } from '../../Viagem/ViagemDTO';
import { ViagemService } from '../../Viagem/viagem.service';

@Component({
  selector: 'app-bloco-detail',
  templateUrl: './bloco-detail.component.html',
  styleUrls: ['./bloco-detail.component.css']
})

export class BlocoDetailComponent implements OnInit {

  blocoVazio: Bloco ={
    codigo: null,
    startTime: 0 ,
    endTime: 0 ,
    servicoMotoristaId: null,
    servicoViaturaId: null,
    viagens : []
  };

  bloco: Bloco = this.blocoVazio;

  servicoViaturaVazio: ServicoViatura = {
    id: null,
    viaturaId: null,
    viagens: []
  }

  criarBloco: boolean;
  selectServicoViatura: ServicoViatura[] = [];
  selectViagens: ViagemDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private blocoService: BlocoService,
    private location: Location,
    private servicoviaturaService: ServicoViaturaService,
    private viagemService: ViagemService
  ) { }

  ngOnInit(): void {

    
    const codigo = this.route.snapshot.paramMap.get('codigo');
    if (codigo) {
      this.getBloco(codigo);
      this.criarBloco = false;
    } else {
      this.criarBloco = true;
      //this.selectServicoViatura = this.bloco.servicoViaturaId;
    }

    this.servicoviaturaService.getServicoViaturas()
      .subscribe(selectServicoViatura => { this.selectServicoViatura = [this.servicoViaturaVazio].concat(selectServicoViatura); });
  
  }

  getBloco(codigo: string): void {
    //this.blocoService.getBlocoData(codigo);
    this.blocoService.getBloco(codigo)
      .subscribe(bloco => {
        this.bloco = bloco;
        console.log(this.bloco);
        for (var i = 0; i < this.bloco.viagens.length; i++) {
          this.viagemService.getViagem(this.bloco.viagens[i]).subscribe(viagem => { this.selectViagens.push(viagem) })
        }
      });
    
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (confirm('Guardar alterações ao bloco '+ this.bloco.codigo + '?')) {
      const codigo = this.route.snapshot.paramMap.get('codigo');
      console.log(this.bloco);
      this.blocoService.updateBloco(this.bloco, codigo)
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

  onChange(idServicoViatura: string) {
    this.viagemService.getViagensByServicoViatura(idServicoViatura)
      .subscribe(viagensServico => { this.selectViagens = viagensServico; });
  }

  addViagem() {
    var novaViagem = null;
    this.bloco.viagens.push(novaViagem);
  }

  trackByIndex(index: number, obj: any): any {
    return index;
  }

  create() {
    if (confirm('Criar bloco ' + this.bloco.codigo + '?')) {
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

}
