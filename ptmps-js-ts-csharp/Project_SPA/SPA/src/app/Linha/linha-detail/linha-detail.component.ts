import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Tipoviatura} from '../../tipoviatura/tipoviatura';
import { Linha } from '../linha';
import { LinhaService } from '../linha.service';
import { TipoTripulante } from '../../TipoTripulante/tipoTripulante';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-linha-detail',
  templateUrl: './linha-detail.component.html',
  styleUrls: ['./linha-detail.component.css']
})
export class LinhaDetailComponent implements OnInit {

  linhaVazia = {
    codigo: null,
    nome: null,
    permissaoMotorista: null,
    permissaoViatura: null,
    noFinal: null,
    cor: null
  };

  linha: Linha = this.linhaVazia;

  permissoesViaturas : Tipoviatura[];
  permissoesMotoristas : TipoTripulante[];
  criarLinha: boolean;

  constructor(
    private route: ActivatedRoute,
    private linhaService: LinhaService,
    private location: Location
  ) { }

  ngOnInit(): void {

    const codigo = this.route.snapshot.paramMap.get('codigo');
    if (codigo) {
      this.getLinha(codigo);
      this.getTiposViaturas(codigo);
      this.getTiposMotoristas(codigo);
      this.criarLinha = false;
    } else {
      this.criarLinha = true;
    }
    
  }

  getLinha(codigo: string): void {
    this.linhaService.getLinhaData(codigo);
    this.linhaService.getLinha(codigo)
      .subscribe(linha => this.linha = linha);
    
  }

  getTiposViaturas(codigo : string): void {
    this.linhaService.getPermissoesViaturas(codigo)
      .subscribe(permissoesViaturas => this.permissoesViaturas = permissoesViaturas);

  }
  
  getTiposMotoristas(codigo : string): void {
    this.linhaService.getPermissoesMotoristas(codigo)
      .subscribe(permissoesMotoristas => this.permissoesMotoristas = permissoesMotoristas);

  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (confirm('Guardar alterações á linha '+ this.linha.codigo + '?')) {
      const codigo = this.route.snapshot.paramMap.get('codigo');
      console.log(this.linha);
      this.linhaService.updateLinha(this.linha, codigo)
        .pipe(
          filter(
            (linha: Linha) => linha != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
    
  }

  create() {
    if (confirm('Criar linha ' + this.linha.codigo + '?')) {
      this.linhaService.addLinha(this.linha)
        .pipe(
          filter(
            (linha: Linha) => linha != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
    
  }

}
