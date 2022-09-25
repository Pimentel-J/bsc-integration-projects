import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Percurso } from './percurso';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataPercursosService implements InMemoryDbService {
  createDb() {
    const percursos = [
      {
        "linha": "Linha_S01112s1sss1",
        "idPercurso": "Percurso_4",
        "ida": true,
        "segmentos": [{
          "ordem": 1,
          "noOrigem": "NO_1",
          "noDestino": "Gandra",
          "duracao": 11,
          "distancia": 22
        }, {
          "ordem": 2,
          "noOrigem": "Gandra",
          "noDestino": "AGUIA",
          "duracao": 12,
          "distancia": 40
          }],
        },
        {
          "linha": "11",
          "idPercurso": "Percurso_1",
        "ida": true,
        "segmentos": [{
          "ordem": 2,
          "noOrigem": "NO_1",
          "noDestino": "NO_2",
          "duracao": 15,
          "distancia": 20
        }, {
          "ordem": 1,
          "noOrigem": "NO_2",
          "noDestino": "NO_1",
          "duracao": 12,
          "distancia": 40
        }]
      }
    ];
    return { percursos };
  }

}
