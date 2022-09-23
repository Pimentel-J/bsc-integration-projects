package eapli.shopfloor.SPM.application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Produto;

public class ResultadosProcessamentoPrinter {

	
	private final String padraoTempo = "yyyy/MM/dd HH:mm:ss";
	private final SimpleDateFormat formatoTempo = new SimpleDateFormat(padraoTempo);
	
	public void print(Set<OrdemProducao> ordensProducao) {
		
		for (OrdemProducao ordemProducao : ordensProducao) {
			
			CalcularResultadosService calculaResultadosService = new CalcularResultadosService(ordemProducao);
			
			Produto produto = ordemProducao.produto();
			
			System.out.println("\n\n== RESULTADOS DE PROCESSAMENTO == Ordem Producao: "+ordemProducao.identity()+"\n");
		
			imprimirHeading1("PRODUCAO");
			imprimirHeading2("Total");
			System.out.println("\tTotal: "+calculaResultadosService.producaoTotal()+" "
					+produto.unidade()+" do produto "+produto.identity()+"\n");
			System.out.println("\tQtd.\t\tLote");
			for (Object[] producao : calculaResultadosService.producoes()) {
				System.out.println("\t"+producao[0]+"\t\t"+producao[1]);
			}
			
			imprimirHeading1("CONSUMO");
			imprimirHeading2("Total");
			System.out.println("\tQtd.\t\tUnid.\t\tMateria-prima\t\tDeposito");
			for (Object[] consumo : calculaResultadosService.consumos()) {
				System.out.println("\t"+consumo[0]+"\t\t"+consumo[1]+"\t\t"+consumo[2]+"\t\t"+consumo[3]);
			}
			
			imprimirHeading1("ESTORNO");
			imprimirHeading2("Total");
			System.out.println("\tQtd.\t\tUnid.\t\tMateria-prima\t\tDeposito");
			for (Object[] estorno : calculaResultadosService.estornos()) {
				System.out.println("\t"+estorno[0]+"\t\t"+estorno[1]+"\t\t"+estorno[2]+"\t\t"+estorno[3]);
			}
			
			imprimirHeading1("ENTREGA PRODUCAO");
			imprimirHeading2("Por deposito");
			System.out.println("\tQtd.\t\tLote    \t\tDeposito");
			for (Object[] entrega : calculaResultadosService.entregas()) {
				System.out.println("\t"+entrega[0]+"\t\t"+entrega[1]+"\t\t"+entrega[2]);
			}
			
			imprimirHeading2("Totais Entrega por Deposito");
			System.out.println("\tQtd.\t\tDeposito");
			for (Object[] entrega : calculaResultadosService.entregasTotaisPorDeposito()) {
				System.out.println("\t"+entrega[0]+"\t\t"+entrega[1]);
			}
			
			imprimirHeading1("DESVIOS");
			imprimirHeading2("Desvios producao");
			System.out.println("\t"+calculaResultadosService.desvioProducao()+" "+produto.unidade()+" de "+produto.identity()+" em relacao à ordem de producao "+ordemProducao.identity());
			
			imprimirHeading2("Desvios consumo");
			System.out.println("\tQtd.\t\tMateria-prima");
			for (Object[] desvio : calculaResultadosService.desvioConsumos()) {
				System.out.println("\t"+(double)desvio[0]+"\t\t"+desvio[1]);
			}
			
			imprimirHeading1("TEMPOS");
			imprimirHeading2("Tempos Brutos");
			System.out.println("\tInicio/Fim da Ordem de Producao: \t"
			+formatoTempo.format(calculaResultadosService.tempoInicio().getTime())+" até "
			+formatoTempo.format(calculaResultadosService.tempoFim().getTime())+"\n");
			for (Object[] maq : calculaResultadosService.inicioFimMaquinas()) {
				System.out.println("\t\tInicio/Fim Operacao "+maq[0]+":   \t\t"
						+formatoTempo.format(((Calendar)maq[1]).getTime())+" até "
						+formatoTempo.format(((Calendar)maq[2]).getTime()));
			}
			
			imprimirHeading2("Total Tempo Bruto");
			System.out.print("\tTempo Bruto Ordem de Producao: \t\t");
			printTempos(calculaResultadosService.tempoBruto());
			System.out.print("\n\n");
			Map<String,long[]> tempos = calculaResultadosService.tempoBrutoTotalMaquinas();
			for (Map.Entry<String,long[]> maq : tempos.entrySet()) {
				System.out.print("\t\tTempo Bruto "+maq.getKey()+":   \t\t\t");
				printTempos(maq.getValue());
				System.out.print("\n");
			}
			
			imprimirHeading2("Tempos Paragem");
			for (Object[] maq : calculaResultadosService.inicioFimParagemMaquinas()) {
				System.out.println("\t\tInicio/Fim Paragem "+maq[0]+":   \t\t"
						+formatoTempo.format(((Calendar)maq[1]).getTime())+" até "
						+formatoTempo.format(((Calendar)maq[2]).getTime())+"\t"
						+maq[3]);
			}
			
			imprimirHeading2("Total Tempo Paragem");
			System.out.print("\tTempo Paragem Ordem de Producao: \t");
			printTempos(calculaResultadosService.tempoParagem());
			System.out.print("\n\n");
			Map<String,long[]> temposParagem = calculaResultadosService.tempoParagemTotalMaquinas();
			for (Map.Entry<String,long[]> maq : temposParagem.entrySet()) {
				System.out.print("\t\tTempo Paragem "+maq.getKey()+":   \t\t\t");
				printTempos(maq.getValue());
				System.out.print("\n");
			}
			
			imprimirHeading2("Total Tempo Efetivo");
			System.out.print("\tTempo Efetivo Ordem de Producao: \t");
			printTempos(calculaResultadosService.tempoEfetivo());
			System.out.print("\n\n");
			Map<String,long[]> temposEfetivos = calculaResultadosService.tempoEfetivoTotalMaquinas();
			for (Map.Entry<String,long[]> maq : temposEfetivos.entrySet()) {
				System.out.print("\t\tTempo Bruto "+maq.getKey()+":   \t\t\t");
				printTempos(maq.getValue());
				System.out.print("\n");
			}
		}
		
		
	}
	
	private void imprimirHeading1(String titulo) {
		System.out.println("\n\n# "+titulo.toUpperCase()+" ========");
	}
	
	private void imprimirHeading2(String titulo) {
		System.out.println("\n\n## "+titulo+"\n");
	}
	
	private void printTempos(long[] tempos) {
		if (tempos[0] != 0) {
			System.out.print(tempos[0]+" horas, ");
		}
		if (tempos[1] != 0) {
			System.out.print(tempos[1]+" minutos e ");
		}
		System.out.print(tempos[2]+" segundos");
	}
	
}
