export interface InputEscalonamento {
  numeroGeracoes: number;
  tamanhoPopulacao: number;
  probCruzamento: number;
  probMutacao: number;
  taxaPreservacao: number;
  numeroGeracoesEstagnacao: number;
  valorAlvo: number;
  tempoLimite: number;
  escrita: boolean;
}
