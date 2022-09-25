import { SegmentoDTO } from '../Segmento/segmentoDTO';

export interface PercursoDTO {
  linha: string;
  idPercurso: string;
  ida: boolean;
  segmentos: SegmentoDTO[];
}
