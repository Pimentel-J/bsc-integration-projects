package eapli.shopfloor.SPM.application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eapli.shopfloor.SCM.domain.EstadoMensagem;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.domain.TipoMensagem;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaoproducao.domain.Atividade;
import eapli.shopfloor.gestaoproducao.domain.AtividadeBuilder;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.ExecucaoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Movimento;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Producao;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.domain.QtdMovimento;
import eapli.shopfloor.gestaoproducao.domain.TipoAtividade;
import eapli.shopfloor.gestaoproducao.domain.TipoMovimento;

public class ThreadProcessamento implements Runnable{
	
	private final LinhaProducao linhaProducao;
	private  List<Mensagem> listaMensagens = new LinkedList<>();
	
	private final GuardaErrosService geraErrosService = new GuardaErrosService();
	private final ValidaProdutoService validaProdutoService = new ValidaProdutoService();
	private final ValidaDepositoService validaDepositoService = new ValidaDepositoService();
	private final ValidaOrdemProducaoService validaOrdemProducaoService = new ValidaOrdemProducaoService();
	private final ConclusaoOrdemProducaoService conclusaoOrdemProducaoService = new ConclusaoOrdemProducaoService();
	private OrdemProducao ordemProducao;
	private ExecucaoOrdemProducao execucao;
	private Map<CodigoAlfaCurto,Float> producoes = new HashMap<>();
	private List<AtividadeBuilder> atividades = new LinkedList<>();
	private final ListMensagensService listMensagensService = new ListMensagensService();
	private Set<OrdemProducao> ordensProducaoProcessadas;
	
	public ThreadProcessamento(List<Mensagem> listaMensagens, LinhaProducao linhaProducao, Set<OrdemProducao> ordensProducao) {
		this.listaMensagens = listaMensagens;
		this.linhaProducao = linhaProducao;
		this.ordensProducaoProcessadas = ordensProducao;
	}
	
	@Override
	public void run() {
		
		// validar ordem de producao
		
		if (validaOrdemProducao() == false) {
				System.out.println("[linhaProducao "+linhaProducao.identity()+"] - Paragem devido a Ordem de Produção não especificada no sistema. "
						+ "Consultar Notificação de Erros para mais detalhes.");
				geraNotificacaoErro(listaMensagens.get(0), TipoNotificacaoErroProcessamento.ELEMENTOS_NAO_ESPECIFICADOS, "Ordem de Produção não disponível no sistema");
				return;
		}
		
		
		System.out.println("[linhaProducao "+linhaProducao.identity()+"] - Validar e enriquecer mensagens...");
		
		// validar e enriquecer mensagens
		
		for (int i = 0; i < listaMensagens.size(); i++) {
			Mensagem mensagem = listaMensagens.get(i);
			if (validaTipoDados(mensagem) == true && validaElementosSistema(mensagem) == true) {
				if (mensagem.estado() == EstadoMensagem.POR_TRATAR) {
					enriquecerMensagem(mensagem);
					mensagem = this.listMensagensService.saveMensagem(mensagem);
					listaMensagens.set(i, mensagem);
				}
			}
		}
		
		System.out.println("[linhaProducao "+linhaProducao.identity()+"] - Atualizar Ordem de Producao ...");
		
		// Iniciar Ordem de Produção e Execucao (se necessário)
		iniciaExecucaoOrdemProducao();
		
		
		System.out.println("[linhaProducao "+linhaProducao.identity()+"] - Gerar Informacao...");
		
		// gerar informação
		
		for (Mensagem mensagem : listaMensagens) {
			if (mensagem.estado() == EstadoMensagem.ENRIQUECIDA) {
				geraInformacao(mensagem);
			}
		}
		
		System.out.println("[linhaProducao "+linhaProducao.identity()+"] - Atualizar Execucao da Ordem de Producao ...");
		
		// Atualizar Execução
		
		for (Map.Entry<CodigoAlfaCurto, Float> producao : producoes.entrySet()) {
			this.execucao.adicionarUmaProducao(new Producao(producao.getValue(),producao.getKey()));
		}
		
		// Atualizar Ordem de Produção (se necessário)
		
		if (conclusaoOrdemProducaoService.conclusaoOrdemProducao(linhaProducao, ordemProducao)) {
			this.ordemProducao.concluirOrdem();
			this.ordensProducaoProcessadas.add(ordemProducao);
		}
		
		validaOrdemProducaoService.saveOrdemProducao(ordemProducao);
		
	}
	
	private void iniciaExecucaoOrdemProducao() {
		if (this.ordemProducao.linhaProducao() == null) {
			this.ordemProducao.associarLinhaProducao(linhaProducao);
		}
		if (this.ordemProducao.estado() == EstadoOrdemProducao.PENDENTE) {
				this.ordemProducao.iniciarExecucaoOrdem();
			} 
		this.execucao = this.ordemProducao.execucao();
	}
	
	private boolean validaOrdemProducao() {
		
		CodigoAlfaCurto codigoOrdemProd = this.listMensagensService.ordemProducaoMensagem(linhaProducao, listaMensagens.get(0).timestampGeracao());
		if (codigoOrdemProd == null) {
			return false;
		} else {
			this.ordemProducao = validaOrdemProducaoService.existeOrdemProducao(codigoOrdemProd);
			if (this.ordemProducao != null) {
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	
	private boolean validaTipoDados(Mensagem mensagem) {
		
		if (mensagem.tipo() == TipoMensagem.C0 || mensagem.tipo() == TipoMensagem.C9 || mensagem.tipo() == TipoMensagem.P2) {
			if (mensagem.produto() == null) {
				geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.TIPO_DADOS_INVALIDO, "Produto - tipo de dados inválido (nulo)");
				return false;
			}
			if (mensagem.deposito() == null) {
				geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.TIPO_DADOS_INVALIDO, "Depósito - tipo de dados inválido (nulo)");
				return false;
			}
		}
		if (mensagem.tipo() == TipoMensagem.P1) {
			if (mensagem.lote() == null) {
				geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.TIPO_DADOS_INVALIDO, "Lote - tipo de dados inválido (nulo)");
				return false;
			}
		}
		if ((mensagem.tipo() == TipoMensagem.S0 || mensagem.tipo() == TipoMensagem.S9) && mensagem.maquina().sequenciaMaquina() == 1) {
			if (mensagem.ordemProducao() == null) {
				geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.TIPO_DADOS_INVALIDO, "Ordem Produção - tipo de dados inválido (nulo)");
				return false;
			}
		}
		
		return true;
		
	}
	
	private boolean validaElementosSistema(Mensagem mensagem) {
		if (mensagem.tipo() == TipoMensagem.C0 || mensagem.tipo() == TipoMensagem.C9 || mensagem.tipo() == TipoMensagem.P2) {
			if (validaProdutoSistema(mensagem) == false || validaDepositoSistema(mensagem) == false) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
	
	private boolean validaProdutoSistema(Mensagem mensagem) {
		if (validaProdutoService.existeProduto(mensagem.produto()) == null) {
			geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.ELEMENTOS_NAO_ESPECIFICADOS, "Produto não registado no Sistema");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validaDepositoSistema(Mensagem mensagem) {
		if (validaDepositoService.existeDeposito(mensagem.deposito()) == null) {
			geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.ELEMENTOS_NAO_ESPECIFICADOS, "Depósito não registado no Sistema");
			return false;
		} else {
			return true;
		}
	}
	
	private void enriquecerMensagem(Mensagem mensagem) {
		if (mensagem.ordemProducao() == null) {
			mensagem.inserirOrdemProducao(this.ordemProducao.identity());
		}
		if (mensagem.linhaProducao() == null) {
			mensagem.inserirLinhaProducao(this.linhaProducao.identity());
		}
		mensagem.marcarComoEnriquecida();
	}
	
	
	private boolean geraInformacao(Mensagem mensagem) {
		
		Produto produto = validaProdutoService.existeProduto(mensagem.produto());
		Deposito deposito = validaDepositoService.existeDeposito(mensagem.deposito());
		
		TipoMovimento tipoMovimento;
		
		try {
			
			if (mensagem.tipo() == TipoMensagem.C0) {	
				
				//Consumo
				tipoMovimento = TipoMovimento.CONSUMO;
				criaMovimento(tipoMovimento, mensagem, deposito, produto, execucao);
				
			} else if (mensagem.tipo() == TipoMensagem.C9) {	
				
				//Entrega Producao
				tipoMovimento = TipoMovimento.ENTREGA_PRODUCAO;
				criaMovimento(tipoMovimento, mensagem, deposito, produto, execucao);
				
			} else if (mensagem.tipo() == TipoMensagem.P2) {	
				
				//Estorno
				tipoMovimento = TipoMovimento.ESTORNO;
				criaMovimento(tipoMovimento, mensagem, deposito, produto, execucao);
				
			} else if (mensagem.tipo() == TipoMensagem.P1) {	
				
				//Producao
				criarProducaoTemp(mensagem.quantidade(), mensagem.lote());
				
			} else if (mensagem.tipo() == TipoMensagem.S0) {	
				
				//Inicio de Atividade
				novaAtividadeProducao(mensagem);
				
			} else if (mensagem.tipo() == TipoMensagem.S8) {	
				
				//Paragem de Atividade
				novaAtividadeParagem(mensagem);
				
			} else if (mensagem.tipo() == TipoMensagem.S9) {	
				
				//Fim de Atividade
				fimAtividadeProducao(mensagem);
				
			} else if (mensagem.tipo() == TipoMensagem.S1) {	
				
				//Retoma de Atividade
				fimAtividadeParagem(mensagem);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			geraNotificacaoErro(mensagem, TipoNotificacaoErroProcessamento.FALHA_GERACAO_INFORMACAO, "Erro na geração de informação no sistema");
			return false;
		}
		
		mensagem.marcarComoProcessada();
		mensagem = this.listMensagensService.saveMensagem(mensagem);
		return true;
	}
	
	private void novaAtividadeProducao(Mensagem mensagem) {
		novaAtividade(mensagem, TipoAtividade.PRODUCAO);
	}
	
	private void novaAtividadeParagem(Mensagem mensagem) {
		novaAtividade(mensagem, TipoAtividade.PARAGEM);
	}
	
	private void novaAtividade(Mensagem mensagem, TipoAtividade tipo) {
		AtividadeBuilder ativBuilder = new AtividadeBuilder();
		ativBuilder = ativBuilder.withMaquina(mensagem.maquina());
		ativBuilder = ativBuilder.withTimestampInicio(mensagem.timestampGeracao());
		ativBuilder = ativBuilder.withTipo(tipo);
		if (tipo == TipoAtividade.PARAGEM) {
			ativBuilder = ativBuilder.withErro(mensagem.erro());
		}
		atividades.add(ativBuilder);
	}
	
	private void fimAtividadeProducao(Mensagem mensagem) {
		fimAtividade(mensagem, TipoAtividade.PRODUCAO);
	}
	
	private void fimAtividadeParagem(Mensagem mensagem) {
		fimAtividade(mensagem, TipoAtividade.PARAGEM);
	}
	
	private void fimAtividade(Mensagem mensagem, TipoAtividade tipo) {
		for (int i = atividades.size()-1; i >= 0 ; i--) {
			AtividadeBuilder ativBuilder = atividades.get(i);
			if (ativBuilder.maquina().identity().equals(mensagem.maquina().identity()) && ativBuilder.tipo().toString().equals(tipo.toString())) {
				ativBuilder = ativBuilder.withTimestampFim(mensagem.timestampGeracao());
				Atividade atividade = ativBuilder.build();
				this.execucao.adicionarUmaAtividade(atividade);
				atividades.remove(i);
				break;
			}
		}
	}
	
	private boolean criaMovimento(TipoMovimento tipo, Mensagem mensagem, Deposito deposito, Produto produto, ExecucaoOrdemProducao execucao) {
		Movimento movimento = new Movimento(tipo, mensagem.maquina(), QtdMovimento.valueOf(mensagem.quantidade()), deposito, produto);
//		movimento = guardaInformacaoService.saveMovimento(movimento);
		execucao.adicionarUmMovimento(movimento);
//		geraInformacaoService.saveExecucao(execucao);
		return true;
	}
	
	private boolean geraNotificacaoErro(Mensagem mensagem, TipoNotificacaoErroProcessamento tipoErro, String descricao) {
		NotificacaoErroProcessamento notificacaoErro = new NotificacaoErroProcessamento(tipoErro, mensagem, linhaProducao, descricao);
		System.out.println("-- Notificacao de Erro gerada");
		notificacaoErro = geraErrosService.saveNotificacao(notificacaoErro);
		return true;
	}
	
	private void criarProducaoTemp(float quantidade, CodigoAlfaCurto lote) {
		if (producoes.containsKey(lote)) {
			producoes.put(lote, Float.sum(producoes.get(lote), quantidade));
		} else {
			producoes.put(lote, quantidade);
		}
	}

}
