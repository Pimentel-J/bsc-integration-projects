import { Tipoviatura } from './tipoviatura';

export const TIPOSVIATURA: Tipoviatura[] = [
  {
    codigo: 'BUS_GAS01',
    nome: 'BUS a GAS',
    tipoCombustivel: 'Gas',
    autonomia: 30000,
    consumoMedio: 8.45,
    custoKm: 0.8,
    velocidadeMedia: 35,
    emissoesCO2: 700
  },
  { codigo: 'BUS_ELECT', nome: 'BUS ELECTRICO', tipoCombustivel: 'Electrico', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 35, emissoesCO2: 700 },
  { codigo: 'BUS_DIESEL', nome: 'BUS a GAS', tipoCombustivel: 'Diesel', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 40, emissoesCO2: 700 },
  { codigo: 'BUS_GAS02', nome: 'BUS a GAS', tipoCombustivel: 'Gas', autonomia: 30000, consumoMedio: 8.45,  custoKm: 0.8, velocidadeMedia: 45, emissoesCO2: 700 },

];