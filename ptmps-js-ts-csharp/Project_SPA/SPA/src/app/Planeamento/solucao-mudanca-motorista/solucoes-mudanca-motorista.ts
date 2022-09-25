import { SolMudancaMotorista } from './solucao-mudanca-motorista';

export interface SolucoesMudancaMotorista {
  noOrigem: string;
  noDestino: string;
  duracao: number;
  horaInicial: number;
  caminho: SolMudancaMotorista;
}
