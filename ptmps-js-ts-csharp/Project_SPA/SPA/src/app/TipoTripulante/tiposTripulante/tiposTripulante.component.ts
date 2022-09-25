import { Component, OnInit } from '@angular/core';

import { TipoTripulante } from '../tipoTripulante'
import { TipoTripulanteService } from '../tipoTripulante.service'

@Component({
  selector: 'app-tiposTripulante',
  templateUrl: './tiposTripulante.component.html',
  styleUrls: ['./tiposTripulante.component.css']
})

export class TiposTripulanteComponent implements OnInit {

  tiposTripulante: TipoTripulante[] = [];

  constructor(private tipoTripulanteService: TipoTripulanteService) { }

  ngOnInit() {
    this.getTiposTripulante();
  }

  getTiposTripulante(): void {
    this.tipoTripulanteService.getTiposTripulante()
      .subscribe(tiposTripulante => this.tiposTripulante = tiposTripulante);

  }

  delete(tipoTripulante: TipoTripulante): void {
    if (confirm('Remover definitivamente o Tipo de Tripulante ' + tipoTripulante.codigo + '?')) {
      this.tiposTripulante = this.tiposTripulante.filter(tt => tt !== tipoTripulante);
      this.tipoTripulanteService.deleteTipoTripulante(tipoTripulante).subscribe();
    }
  }
  
  //add(codigo: string): void {
  //  codigo = codigo.trim();
  //  if (!codigo) { return; }
  //  this.tipoTripulanteService.addTipoTripulante({ codigo } as TipoTripulante)
  //    .subscribe(tipoTripulante => {
  //      this.tiposTripulante.push(tipoTripulante);
  //    });
  //}
}
