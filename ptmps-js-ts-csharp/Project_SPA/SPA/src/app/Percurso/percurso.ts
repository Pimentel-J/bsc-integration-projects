import { Linha } from '../Linha/linha';
import { Segmento } from '../Segmento/segmento';

export interface Percurso {
  idLinha: Linha;
  idPercurso: string;
  ida: boolean;
  segmentos: Segmento[];
}
