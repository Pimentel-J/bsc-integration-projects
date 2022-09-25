import { Component, OnInit } from '@angular/core';

import { No } from '../no'
import { NoService } from '../no.service'

@Component({
  selector: 'app-nos',
  templateUrl: './nos.component.html',
  styleUrls: ['./nos.component.css']
})

export class NosComponent implements OnInit {

  nos: No[] = [];

  constructor(private noService: NoService) { }

  ngOnInit() {
    this.getNos();
  }

  getNos(): void {
    this.noService.getNos()
      .subscribe(nos => this.nos = nos);

  }

  //add(nome: string): void {
  //  nome = nome.trim();
  //  if (!nome) { return; }
  //  this.tipoviaturaService.addTipoviatura({ nome } as Tipoviatura)
  //    .subscribe(tipoviatura => {
  //      this.tiposviatura.push(tipoviatura);
  //    });
  //}

  delete(no: No): void {
    if (confirm('Remover definitivamente o nó ' + no.abreviatura + '?')) {
      this.nos = this.nos.filter(n => n !== no);
      this.noService.deleteNo(no).subscribe();
    }
    
  }

}
