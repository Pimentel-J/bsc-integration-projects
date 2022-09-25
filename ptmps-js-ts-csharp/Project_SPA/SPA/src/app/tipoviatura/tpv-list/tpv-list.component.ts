import { Component, OnInit } from '@angular/core';

import { Tipoviatura } from '../tipoviatura'
import { TipoviaturaService } from '../tipoviatura.service'

@Component({
  selector: 'app-tpv-list',
  templateUrl: './tpv-list.component.html',
  styleUrls: ['./tpv-list.component.css']
})

export class TpvListComponent implements OnInit {

  tiposviatura: Tipoviatura[] = [];


  constructor(private tipoviaturaService: TipoviaturaService) { }

  ngOnInit() {
    this.getTiposviatura();
  }

  getTiposviatura(): void {
    this.tipoviaturaService.getTiposviatura()
      .subscribe(tiposviatura => this.tiposviatura = tiposviatura);

  }

  /* add(codigo:string): void {
     codigo = codigo.trim();
     if (!codigo) { return; }
     this.tipoviaturaService.addTipoviatura({ codigo } as Tipoviatura)
       .subscribe(tipoviatura => {
         this.tiposviatura.push(tipoviatura); 
       });
   }*/

  delete(tipoviatura: Tipoviatura): void {
    if (confirm('Remover Tipo Viatura ' + tipoviatura.codigo + ' ?')) {
      this.tiposviatura = this.tiposviatura.filter(h => h !== tipoviatura);
      this.tipoviaturaService.deleteTipoviatura(tipoviatura).subscribe();
    }
  }

}
