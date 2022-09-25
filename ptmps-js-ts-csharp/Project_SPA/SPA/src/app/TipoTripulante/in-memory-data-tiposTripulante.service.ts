import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { TipoTripulante } from './tipoTripulante';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataTiposTripulanteService implements InMemoryDbService {
  createDb() {
    const tiposTripulante = [
      {
        codigo: 'PTFRESP',
        descricao: 'Motorista senior fluente em lingua francesa e espanhola'
      },
      {
        codigo: 'PTENGALE',
        descricao: 'Motorista junior fluente em lingua inglesa e alemã'
      }
    ];
    return { tiposTripulante };
  }

  //// Overrides the genId method to ensure that a linha always has an id.
  //// If the linhas array is empty,
  //// the method below returns the initial number (11).
  //// if the linhas array is not empty, the method below returns the highest
  //// linha id + 1.
  //genId(linhas: linha[]): number {
  //  return linhas.length > 0 ? Math.max(...linhas.map(linha => linha.id)) + 1 : 11;
  //}
}
