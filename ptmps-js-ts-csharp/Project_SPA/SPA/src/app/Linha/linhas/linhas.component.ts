import { Component, OnInit } from '@angular/core';

import { Linha } from '../linha'
import { LinhaService } from '../linha.service'

@Component({
  selector: 'app-linhas',
  templateUrl: './linhas.component.html',
  styleUrls: ['./linhas.component.css']
})

export class LinhasComponent implements OnInit {

  linhas: Linha[] = [];

  constructor(private linhaService: LinhaService) { }

  ngOnInit() {
    this.getLinhas();
  }

  getLinhas(): void {
    this.linhaService.getLinhas()
      .subscribe(linhas => this.linhas = linhas);

  }

  delete(linha: Linha): void {
    if (confirm('Remover definitivamente a linha ' + linha.codigo + '?')) {
      this.linhas = this.linhas.filter(n => n !== linha);
      this.linhaService.deleteLinha(linha).subscribe();

    }
  }

  import():void{
    this.linhaService.import();
  }
}
