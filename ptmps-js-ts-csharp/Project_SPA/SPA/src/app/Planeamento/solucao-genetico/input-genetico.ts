export interface InputGenetico {
  servicoViaturaID: string;
  escalonamentoMotoristas: string;
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
