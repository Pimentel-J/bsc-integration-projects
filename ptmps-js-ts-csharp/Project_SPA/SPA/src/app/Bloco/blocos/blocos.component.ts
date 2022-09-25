import { Component, OnInit } from '@angular/core';

import { Bloco } from '../bloco'
import { BlocoService } from '../bloco.service'

@Component({
  selector: 'app-blocos',
  templateUrl: './blocos.component.html',
  styleUrls: ['./blocos.component.css']
})

export class BlocosComponent implements OnInit {

  blocos: Bloco[] = [];

  constructor(private blocoService: BlocoService) { }

  ngOnInit() {
    this.getBlocos();
  }

  getBlocos(): void {
    this.blocoService.getBlocos()
      .subscribe(blocos => this.blocos = blocos);

  }

  delete(bloco: Bloco): void {
    if (confirm('Remover definitivamente a bloco ' + bloco.codigo + '?')) {
      this.blocos = this.blocos.filter(n => n !== bloco);
      this.blocoService.deleteBloco(bloco).subscribe();

    }
  }

  import():void{
    this.blocoService.import();
  }
}
