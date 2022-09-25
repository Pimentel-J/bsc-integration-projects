import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Tipoviatura } from '../tipoviatura'
import { TipoviaturaService} from '../tipoviatura.service'
import { filter } from 'rxjs/operators';

interface Tipo {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-tpv-detail',
  templateUrl: './tpv-detail.component.html',
  styleUrls: ['./tpv-detail.component.css']
})
  
export class TpvDetailComponent implements OnInit {
  tipoViaturaVazio = {
    codigo: null,
    nome: null,
    tipoCombustivel: null,
    autonomia: null,
    consumoMedio: 0,
    custoKm: null,
    velocidadeMedia: null,
    emissoesCO2: null
  };

  tipoviatura: Tipoviatura = this.tipoViaturaVazio;
  criar: boolean;
  tipos: Tipo[] = [
    {value: 'Gasoleo', viewValue: 'Gasoleo'},
    {value: 'Gasolina', viewValue: 'Gasolina'},
    { value: 'Eletrico', viewValue: 'Eletrico' },
    { value: 'Hidrogenio', viewValue: 'Hidrogenio' },
    {value: 'GPL', viewValue: 'GPL'}
  ];

  constructor(
    private route: ActivatedRoute,
    private tipoviaturaService: TipoviaturaService,
    private location: Location,
    
  ) { }

  ngOnInit(): void {
    //this.getTipoviatura();
    const codigo = this.route.snapshot.paramMap.get('codigo');
    // console.log(codigo);
    if (codigo) {
      this.getTipoviatura(codigo);
      this.criar = false;
    } else {
      this.criar = true;
    }

  }


  /* getTipoviatura(): void {
 
     const codigo = this.route.snapshot.paramMap.get('codigo');
     console.log('Este é o ' + codigo);
     this.tipoviaturaService.getTipoviatura(codigo)
       .subscribe(tipoviatura => this.tipoviatura = tipoviatura);
     console.log(this.tipoviatura);
    
     };*/
  
  getTipoviatura(codigo: string): void {

    this.tipoviaturaService.getTipoviatura(codigo)
      .subscribe(tipoviatura => this.tipoviatura = tipoviatura);
    console.log(this.tipoviatura);
   
  };

  goBack(): void {
    this.location.back();
  }

  save(): void {
 
    if (confirm('Guardar alterações  ' + this.tipoviatura.codigo + '?')) {
      const codigo = this.route.snapshot.paramMap.get('codigo');
      console.log(this.tipoviatura);
      this.tipoviaturaService.updateTipoviatura(this.tipoviatura, codigo)
        .pipe(
          filter(
            (tipoviatura: Tipoviatura) => tipoviatura != null
          )
        )
        .subscribe(
          () => this.goBack());
    }

  }

  create(): void {
      if (confirm('Criar Tipo Viatura ' + this.tipoviatura.codigo + '?')) {
        this.tipoviaturaService.addTipoviatura(this.tipoviatura)
          .pipe(
            filter(
              (tipoviatura: Tipoviatura) => tipoviatura != null
            )
          )
          .subscribe(
            () => this.goBack()
          );
      }
      
    }
 
  }
