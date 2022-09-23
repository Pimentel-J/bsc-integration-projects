package eapli.shopfloor.SPM.domain;

import eapli.framework.general.domain.model.Description;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.domain.TipoMensagem;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.NumeroSerie;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *João Pimentel 
 */
public class NotificacaoErroProcessamentoTest {

    TipoNotificacaoErroProcessamento tipoTest = TipoNotificacaoErroProcessamento.TIPO_DADOS_INVALIDO;
    Calendar dataTest = Calendar.getInstance();
    LinhaProducao linhaTest = new LinhaProducao(CodigoAlfaCurto.valueOf("L1"));
    Maquina maquinaTest = new Maquina(CodigoAlfaCurto.valueOf("M1"), NumeroSerie.valueOf("123"),
            Descricao.valueOf("M1"), dataTest, Description.valueOf("M1"), CodigoAlfaCurto.valueOf("M1"),
            linhaTest, 1);
    Mensagem mensagemTest = new Mensagem(TipoMensagem.C0, dataTest, dataTest, maquinaTest,
            CodigoAlfaCurto.valueOf("P1"), 50, CodigoAlfaCurto.valueOf("L1"), Descricao.valueOf("ERRO"),
            CodigoAlfaCurto.valueOf("OP1"), CodigoAlfaCurto.valueOf("D1"));
    NotificacaoErroProcessamento notificacaoTest = new NotificacaoErroProcessamento(tipoTest, mensagemTest,
            linhaTest, "test");

    @Test
    public void testArquivarNotificacaoErroProcess() {
        System.out.println("testArquivarNotificacaoErroProcess");
        notificacaoTest.arquivarNotificacaoErroProcess();
        assertEquals(notificacaoTest.estado(), EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }
}