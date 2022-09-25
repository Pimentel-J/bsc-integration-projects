import { SolucaoServicoTripulante } from './solucao-servico-tripulante';

export interface SolucaoEscalonamento {
  escalonamento: SolucaoServicoTripulante[];
  alertas: string[];
}
