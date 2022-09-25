import { No } from '../No/no';

export interface Segmento {
  _id: string;
  ordem: number;
  noOrigem: No;
  noDestino: No;
  duracao: number;
  distancia: number;
}
