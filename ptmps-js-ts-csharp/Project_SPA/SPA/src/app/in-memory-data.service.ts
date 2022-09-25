import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';


@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const tiposviatura = [
      { id: 'BUS_GAS01', nome: 'BUS a GAS', tipoCombustivel: 'Gas', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 35, emissoesCO2: 700 },
      { id: 'BUS_ELECT', nome: 'BUS ELECTRICO', tipoCombustivel: 'Electrico', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 35, emissoesCO2: 700 },
      { id: 'BUS_DIESEL', nome: 'BUS a GAS', tipoCombustivel: 'Diesel', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 40, emissoesCO2: 700 },
      { id: 'BUS_GAS02', nome: 'BUS a GAS', tipoCombustivel: 'Gas', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 45, emissoesCO2: 700 },
    
    ];
    const linhas = [
      { id: 11, name: 'Dr Nice' },
      { id: 12, name: 'Narco' },
      { id: 13, name: 'Bombasto' },
      { id: 14, name: 'Celeritas' },
      { id: 15, name: 'Magneta' },
      { id: 16, name: 'RubberMan' },
      { id: 17, name: 'Dynama' },
      { id: 18, name: 'Dr IQ' },
      { id: 19, name: 'Magma' },
      { id: 20, name: 'Tornado' }
    ];
    const nos = [
      {
        id: 'CETE',
        nome: 'Cete',
        latitude: 41.183243425797,
        longitude: -8.35164059584564,
        estacaoRecolha: false,
        pontoRendicao: false
      },
      {
        id: 'CRIST',
        nome: 'Cristelo',
        latitude: 41.2207801252676,
        longitude: -8.34639896125324,
        estacaoRecolha: false,
        pontoRendicao: true
      },
      {
        id: 'DIGRJ',
        nome: 'Duas Igrejas',
        latitude: 41.2278665802794,
        longitude: -8.35481024956726,
        estacaoRecolha: false,
        pontoRendicao: false
      },
      {
        id: 'ESTLO',
        nome: 'Estação (Lordelo)',
        latitude: 41.2521157104055,
        longitude: -8.4227924957086,
        estacaoRecolha: true,
        pontoRendicao: false
      },
      {
        id: 'ESTPA',
        nome: 'Estação (Paredes)',
        latitude: 41.2082119860192,
        longitude: -8.33448520831829,
        estacaoRecolha: true,
        pontoRendicao: false
      },
      {
        id: 'GAND',
        nome: 'Gandra',
        latitude: 41.1956579348384,
        longitude: -8.43958765792976,
        estacaoRecolha: false,
        pontoRendicao: false
      }
    ];
    const tipostripulante = [
      { codigo: 'PTFRESP', descricao: 'Motorista senior fluente em lingua francesa e espanhola' },
      { codigo: 'PTENGALE', descricao: 'Motorista junior fluente em lingua inglesa e alemã' }
    ];

    return {tiposviatura,linhas,nos,tipostripulante};
  }

  // Overrides the genId method to ensure that a linha always has an id.
  // If the linhas array is empty,
  // the method below returns the initial number (11).
  // if the linhas array is not empty, the method below returns the highest
  // linha id + 1.

}
