package eapli.shopfloor.SPM.application;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.AtividadeRepository;
import eapli.shopfloor.gestaoproducao.repositories.ExecucaoOrdemProducaoRepository;
import eapli.shopfloor.gestaoproducao.repositories.MovimentoRepository;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import eapli.shopfloor.util.Tempo;

public class CalcularResultadosService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MovimentoRepository movimentoRepository = PersistenceContext.repositories().movimentos();
    private final AtividadeRepository atividadeRepository = PersistenceContext.repositories().atividades();
    private final ExecucaoOrdemProducaoRepository execucaoRepository = PersistenceContext.repositories().execucoesOrdensProducao();
    private final ProdutoRepository produtosRepository = PersistenceContext.repositories().produtos();
    private OrdemProducao ordemProducao;
	
    public CalcularResultadosService(OrdemProducao ordemProducao) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	this.ordemProducao = ordemProducao;
    }
    
    public List<Object[]> producoes() {
    	return execucaoRepository.producoesDeOrdemProducao(ordemProducao);
    }
    
    public Double producaoTotal() {
    	return execucaoRepository.producaoTotalDeOrdemProducao(ordemProducao);
    }
    
    public Double desvioProducao() {
    	return execucaoRepository.desvioProducaoDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> desvioConsumos() {
    	List<Object[]> consumosReais = movimentoRepository.consumoPorProdutoDeOrdemProducao(ordemProducao);
    	List<Object[]> consumosTeoricos = produtosRepository.consumosTeoricos(ordemProducao.produto(), ordemProducao.quantidade());
    	List<Object[]> desvioConsumos = consumosTeoricos;
    	for (Object[] desvios : desvioConsumos) {
			for (Object[] reais : consumosReais) {
				if (desvios[1].equals(reais[1])) {
					double desvio = (double)reais[0]-(float)desvios[0];
					desvios[0] = desvio;
				}
			}
		}
    	return desvioConsumos;
    }
    
    public List<Object[]> consumos() {
    	return movimentoRepository.consumosDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> estornos() {
    	return movimentoRepository.estornosDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> entregas() {
    	return movimentoRepository.entregasDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> entregasTotaisPorDeposito() {
    	return movimentoRepository.entregasTotaisDepositoDeOrdemProducao(ordemProducao);
    }
    
    public Calendar tempoInicio() {
    	return atividadeRepository.inicioDeOrdemProducao(ordemProducao);
    }
    
    public Calendar tempoFim() {
    	return atividadeRepository.fimDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> inicioFimMaquinas() {
    	return atividadeRepository.inicioFimMaquinasDeOrdemProducao(ordemProducao);
    }
    
    public List<Object[]> inicioFimParagemMaquinas() {
    	return atividadeRepository.inicioFimParagemMaquinasDeOrdemProducao(ordemProducao);
    }
    
    public long[] tempoBruto() {
    	return Tempo.calculaDiferencaHMS(Tempo.calculaDiferencaMilis(tempoInicio(), tempoFim()));
    }
    
    private Map<String,Long> tempoBrutoMaquinas() {
    	List<Object[]> inicioFim = inicioFimMaquinas();
    	Map<String,Long> temposParagem = new HashMap<>();
    	for (Object[] maq : inicioFim) {
    		long diferenca = Tempo.calculaDiferencaMilis((Calendar)maq[1], (Calendar)maq[2]);
			temposParagem.put((String)maq[0], diferenca);
		}
    	return temposParagem;
    }
    
    public Map<String,long[]> tempoBrutoTotalMaquinas() {
    	Map<String,Long> temposMili = tempoBrutoMaquinas();
    	Map<String,long[]> temposParagem = new HashMap<>();
    	for (Map.Entry<String,Long> maq : temposMili.entrySet()) {
    		temposParagem.put(maq.getKey(), Tempo.calculaDiferencaHMS(maq.getValue()));
		}
    	return temposParagem;
    }
    
    private Map<String,Long> tempoParagemMaquinas() {
    	List<Object[]> inicioFim = inicioFimParagemMaquinas();
    	Map<String,Long> temposParagem = new HashMap<>();
    	for (Object[] maq : inicioFim) {
    		long diferenca = Tempo.calculaDiferencaMilis((Calendar)maq[1], (Calendar)maq[2]);
    		if (temposParagem.containsKey(maq[0])) {
				temposParagem.put((String)maq[0], temposParagem.get(maq[0])+diferenca);
			} else {
				temposParagem.put((String)maq[0], diferenca);
			}
		}
    	return temposParagem;
    }
    
    public Map<String,long[]> tempoParagemTotalMaquinas() {
    	Map<String,Long> temposMili = tempoParagemMaquinas();
    	Map<String,long[]> temposParagem = new HashMap<>();
    	for (Map.Entry<String,Long> maq : temposMili.entrySet()) {
    		temposParagem.put(maq.getKey(), Tempo.calculaDiferencaHMS(maq.getValue()));
		}
    	return temposParagem;
    }
    
    private Map<String,Long> tempoEfetivoMaquinas() {
    	Map<String,Long> temposMili = tempoBrutoMaquinas();
    	Map<String,Long> temposParagemMili = tempoParagemMaquinas();
    	for (Map.Entry<String,Long> maq : temposParagemMili.entrySet()) {
    		if (temposMili.containsKey(maq.getKey())) {
    			temposMili.put(maq.getKey(), temposMili.get(maq.getKey()) - maq.getValue());
			}
		}
    	return temposMili;
    }
    
    public Map<String,long[]> tempoEfetivoTotalMaquinas() {
    	Map<String,Long> temposMili = tempoEfetivoMaquinas();
    	Map<String,long[]> tempos = new HashMap<>();
    	for (Map.Entry<String,Long> maq : temposMili.entrySet()) {
    		tempos.put(maq.getKey(), Tempo.calculaDiferencaHMS(maq.getValue()));
		}
    	return tempos;
    }
    
    public long[] tempoParagem() {
    	return Tempo.calculaDiferencaHMS(tempoParagemMili());
    }
    
    private long tempoParagemMili() {
    	long total = 0;
    	for (Long tempo : tempoParagemMaquinas().values()) {
			total += tempo;
		}
    	return total;
    }
    
    public long[] tempoEfetivo() {
    	long bruto = Tempo.calculaDiferencaMilis(tempoInicio(), tempoFim());
    	long paragem = tempoParagemMili();
    	return Tempo.calculaDiferencaHMS(bruto-paragem);
    }
    
    
    
}
