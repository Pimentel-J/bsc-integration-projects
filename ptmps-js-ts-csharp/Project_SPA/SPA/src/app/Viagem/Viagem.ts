import { Percurso } from '../Percurso/percurso'
import { ServicoViatura } from '../ServicoViatura/servicoviatura'
import { Bloco } from '../Bloco/bloco'
import { Passagem } from '../Passagem/passagem'

export interface Viagem {

  id: string;
  percursoId: string;
  descritivo: string;
  horaInicio: number;
  horaFim: number;
  passagens: Passagem[]

}