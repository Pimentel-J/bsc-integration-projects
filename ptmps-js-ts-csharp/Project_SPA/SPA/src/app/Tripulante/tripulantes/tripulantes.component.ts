import { Component, OnInit } from '@angular/core';

import { Tripulante } from '../tripulante'
import { TripulanteService } from '../tripulante.service'

@Component({
  selector: 'app-tripulantes',
  templateUrl: './tripulantes.component.html',
  styleUrls: ['./tripulantes.component.css']
})

export class TripulantesComponent implements OnInit {

  tripulantes: Tripulante[] = [];

  constructor(private tripulanteService: TripulanteService) { }

  ngOnInit() {
    this.getTripulantes();
  }

  getTripulantes(): void {
    this.tripulanteService.getTripulantes()
      .subscribe(tripulantes => this.tripulantes = tripulantes);

  }

  delete(tripulante: Tripulante): void {
    if (confirm('Remover definitivamente o Tripulante ' + tripulante.numeroMecanografico + '?')) {
      this.tripulantes = this.tripulantes.filter(trip => trip !== tripulante);
      this.tripulanteService.deleteTripulante(tripulante).subscribe();
    }
  }

}
