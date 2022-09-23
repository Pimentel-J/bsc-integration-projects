package eapli.shopfloor.SPM.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SPM.application.ListMensagensService;
import eapli.shopfloor.SPM.application.ThreadProcessamento;
import eapli.shopfloor.gestaochaofabrica.application.ListLinhasProducaoService;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

public class ProcessadorMensagensService {
	
	private Calendar inicioIntervaloProcessamento;
	private Calendar finalIntervaloProcessamento;
	private final List<LinhaProducao> listaLinhasProdSelecionadas = new ArrayList<>();
	private final Set<Thread> threads = new HashSet<>();
	private  List<Mensagem> listaMensagens = new LinkedList<>();
	private final ListMensagensService listMensagensService = new ListMensagensService();
	private final ListLinhasProducaoService listLinhasProducaoService = new ListLinhasProducaoService();
	private Set<OrdemProducao> ordensProducaoProcessadas = new HashSet<>();
	
	public void definirInicioIntervaloProc(Calendar dataHora) {
		this.inicioIntervaloProcessamento = dataHora;
	}
	
	public void definirFinalIntervaloProc(Calendar dataHora) {
		this.finalIntervaloProcessamento = dataHora;
	}
	
	public void selecionarLinhaProducao(LinhaProducao linha) {
		this.listaLinhasProdSelecionadas.add(linha);
	}
	
	public Set<OrdemProducao> executarProcessamento() {
		
		if (listaLinhasProdSelecionadas.isEmpty()) {
			listaLinhasProdSelecionadas.addAll((List<LinhaProducao>) listLinhasProducaoService.linhasProducaoComProcessamentoAtivo());
		}
		
		for (LinhaProducao linhaProducao : listaLinhasProdSelecionadas) {
			// Obter mensagens de cada linha de produção
			listaMensagens = (List<Mensagem>) listMensagensService.mensagensDeLinhaProducao(linhaProducao, inicioIntervaloProcessamento, finalIntervaloProcessamento);
			// Criar a Thread para processar linha de produção
			if (listaMensagens.isEmpty() == false) {
				ThreadProcessamento threadLinha = new ThreadProcessamento(listaMensagens, linhaProducao, ordensProducaoProcessadas);
				Thread thread = new Thread(threadLinha);
				thread.start();
				threads.add(thread);
			} else {
				System.out.println("[Linha Producao "+linhaProducao.identity()+"] Sem mensagens para processar");
			}
		}
		for (Thread thread : threads) {
			// Apanha as threads que finalizam
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return ordensProducaoProcessadas;
		
	}
	
}
