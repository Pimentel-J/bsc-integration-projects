package eapli.shopfloor.reporting.notificacoeserroprocessamento.dto;

import eapli.framework.representations.dto.DTO;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

import java.util.Calendar;

/**
 * DTO - Consultar Notificações de Erros de Processamentos
 *
 *João Pimentel 
 */
@DTO
public class NotificacoesErrosProcessamento {

    public TipoNotificacaoErroProcessamento tipo;
    public Calendar timestampGeracao;
    public Mensagem mensagem;
    public LinhaProducao linhaProducao;
    public Descricao descricao;

    public NotificacoesErrosProcessamento(TipoNotificacaoErroProcessamento tipo, Calendar timestampGeracao,
                                          Mensagem mensagem, LinhaProducao linhaProducao, Descricao descricao) {
        this.tipo = tipo;
        this.timestampGeracao = timestampGeracao;
        this.mensagem = mensagem;
        this.linhaProducao = linhaProducao;
        this.descricao = descricao;
    }

}
