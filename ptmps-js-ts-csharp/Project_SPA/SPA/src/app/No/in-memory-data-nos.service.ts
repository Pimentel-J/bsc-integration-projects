import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { No } from './no';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataNosService implements InMemoryDbService {
  createDb() {
    const nos = [
      {
        abreviatura: 'CETE',
        nome: 'Cete',
        latitude: 41.183243425797,
        longitude: -8.35164059584564,
        estacaoRecolha: false,
        pontoRendicao: false
      },
      {
        abreviatura: 'CRIST',
        nome: 'Cristelo',
        latitude: 41.2207801252676,
        longitude: -8.34639896125324,
        estacaoRecolha: false,
        pontoRendicao: true
      },
      {
        abreviatura: 'DIGRJ',
        nome: 'Duas Igrejas',
        latitude: 41.2278665802794,
        longitude: -8.35481024956726,
        estacaoRecolha: false,
        pontoRendicao: false
      },
      {
        abreviatura: 'ESTLO',
        nome: 'Estação (Lordelo)',
        latitude: 41.2521157104055,
        longitude: -8.4227924957086,
        estacaoRecolha: true,
        pontoRendicao: false
      },
      {
        abreviatura: 'ESTPA',
        nome: 'Estação (Paredes)',
        latitude: 41.2082119860192,
        longitude: -8.33448520831829,
        estacaoRecolha: true,
        pontoRendicao: false
      },
      {
        abreviatura: 'GAND',
        nome: 'Gandra',
        latitude: 41.1956579348384,
        longitude: -8.43958765792976,
        estacaoRecolha: false,
        pontoRendicao: false
      }
    ];
    return { nos };
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
