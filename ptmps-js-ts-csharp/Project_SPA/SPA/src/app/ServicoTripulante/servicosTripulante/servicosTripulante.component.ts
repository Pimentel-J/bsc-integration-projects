import { Component, OnInit } from '@angular/core';

import { ServicoTripulante } from '../servicoTripulante'
import { ServicoTripulanteService } from '../servicoTripulante.service'

@Component({
  selector: 'app-servicosTripulante',
  templateUrl: './servicosTripulante.component.html',
  styleUrls: ['./servicosTripulante.component.css']
})

export class ServicosTripulanteComponent implements OnInit {

  servicosTripulante: ServicoTripulante[] = [];

  constructor(private servicoTripulanteService: ServicoTripulanteService) { }

  ngOnInit() {
    this.getServicosTripulante();
  }

  getServicosTripulante(): void {
    this.servicoTripulanteService.getServicosTripulante()
      .subscribe(servicosTripulante => this.servicosTripulante = servicosTripulante);

  }

  delete(servicoTripulante: ServicoTripulante): void {
    if (confirm('Remover definitivamente o Serviço de Tripulante ' + servicoTripulante.codigoServicoTripulante + '?')) {
      this.servicosTripulante = this.servicosTripulante.filter(st => st !== servicoTripulante);
      this.servicoTripulanteService.deleteServicoTripulante(servicoTripulante).subscribe();
    }
  }

}
