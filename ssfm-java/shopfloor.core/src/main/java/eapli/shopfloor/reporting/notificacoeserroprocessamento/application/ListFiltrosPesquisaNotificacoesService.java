package eapli.shopfloor.reporting.notificacoeserroprocessamento.application;

import java.util.Arrays;

/**
 *João Pimentel 
 */
public class ListFiltrosPesquisaNotificacoesService {

    public Iterable<Filtros> allFiltrosPesquisaNotificacoes() {
        return Arrays.asList(Filtros.values());
    }

    public enum Filtros {GERAL, TIPO_ERRO, DATA_ERRO, LINHA_PRODUCAO, MAQUINA}
}
