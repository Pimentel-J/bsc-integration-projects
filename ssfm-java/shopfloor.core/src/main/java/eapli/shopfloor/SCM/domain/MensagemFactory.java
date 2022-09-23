/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.domain;

import eapli.framework.time.util.Calendars;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import java.util.Calendar;

/**
 *
 *User
 */
public class MensagemFactory {

    private Maquina maquina; // obrigatorio
    private TipoMensagem tipoMensagem; //obrigatorio
    private Calendar timestampGeracao; //obrigatorio
    private Calendar timestampEnvio; //obrigatorio
    private CodigoAlfaCurto produtoMsg; //opcional
    private int quantidadeMsg; //opcional
    private CodigoAlfaCurto loteMsg; //opcional
    private Descricao erroMsg; //opcional
    private CodigoAlfaCurto ordemProdMsg; //opcional
    private CodigoAlfaCurto depositoMsg; //opcional

    public MensagemFactory(Maquina maquina, TipoMensagem tipoMensagem,
            Calendar timestampGeracao, Calendar timestampEnvio) {
        if (Calendars.tomorrow().compareTo(timestampGeracao) < 1) {
            throw new IllegalArgumentException("A data de geração da mensagem é inválida: tem data futura.");
        }
        if (Calendars.tomorrow().compareTo(timestampEnvio) < 1) {
            throw new IllegalArgumentException("A data de envio da mensagem é inválida: tem data futura.");
        }
        this.maquina = maquina;
        this.tipoMensagem = tipoMensagem;
        this.timestampGeracao = timestampGeracao;
        this.timestampEnvio = timestampEnvio;
    }

    public static Mensagem criaMensagemConsumoEntregaEstorno(Maquina maquina,
            String[] mensagemBruta, Calendar timestampEnvio) {
        MensagemFactory newBuilder = new MensagemFactory(maquina, TipoMensagem.valueOf(mensagemBruta[1]),
                Calendars.parse(mensagemBruta[2], "YYYYMMDDhhmmss"),
                timestampEnvio);
        newBuilder.adicionaProdutoAMensagem(CodigoAlfaCurto.valueOf(mensagemBruta[3]));
        newBuilder.adicionaQuantidadeAMensagem(Integer.parseInt(mensagemBruta[4]));
        newBuilder.adicionaDepositoAMensagem(CodigoAlfaCurto.valueOf(mensagemBruta[5]));
        return newBuilder.build();
    }

    public static Mensagem criaMensagemProducao(Maquina maquina,
            String[] mensagemBruta, Calendar timestampEnvio) {
        MensagemFactory newBuilder = new MensagemFactory(maquina, TipoMensagem.valueOf(mensagemBruta[1]),
                Calendars.parse(mensagemBruta[2], "YYYYMMDDhhmmss"),
                timestampEnvio);
        newBuilder.adicionaProdutoAMensagem(CodigoAlfaCurto.valueOf(mensagemBruta[3]));
        newBuilder.adicionaQuantidadeAMensagem(Integer.parseInt(mensagemBruta[4]));
        newBuilder.adicionaLoteAMensagem(CodigoAlfaCurto.valueOf(mensagemBruta[5]));
        return newBuilder.build();
    }

    public static Mensagem criaMensagemInicioFimAtividade(Maquina maquina,
            String[] mensagemBruta, Calendar timestampEnvio) {
        MensagemFactory newBuilder = new MensagemFactory(maquina, TipoMensagem.valueOf(mensagemBruta[1]),
                Calendars.parse(mensagemBruta[2], "YYYYMMDDhhmmss"),
                timestampEnvio);
        if (mensagemBruta.length > 3) {
            newBuilder.adicionaOrdemProdAMensagem(CodigoAlfaCurto.valueOf(mensagemBruta[3]));
        }

        return newBuilder.build();
    }

    public static Mensagem criaMensagemParagem(Maquina maquina,
            String[] mensagemBruta, Calendar timestampEnvio) {
        MensagemFactory newBuilder = new MensagemFactory(maquina, TipoMensagem.valueOf(mensagemBruta[1]),
                Calendars.parse(mensagemBruta[2], "YYYYMMDDhhmmss"),
                timestampEnvio);
        newBuilder.adicionaErroAMensagem(Descricao.valueOf(mensagemBruta[3]));
        return newBuilder.build();
    }

    public static Mensagem criaMensagemRetomaAtividade(Maquina maquina,
            String[] mensagemBruta, Calendar timestampEnvio) {
        MensagemFactory newBuilder = new MensagemFactory(maquina, TipoMensagem.valueOf(mensagemBruta[1]),
                Calendars.parse(mensagemBruta[2], "YYYYMMDDhhmmss"),
                timestampEnvio);
        return newBuilder.build();
    }

    protected MensagemFactory adicionaProdutoAMensagem(CodigoAlfaCurto codProduto) {
        produtoMsg = codProduto;
        return this;
    }

    protected MensagemFactory adicionaQuantidadeAMensagem(int quant) {
        if (quant < 0) {
            throw new IllegalArgumentException("Quantidade inválida: tem que ser maior ou igual a zero");
        }
        quantidadeMsg = quant;
        return this;
    }

    protected MensagemFactory adicionaLoteAMensagem(CodigoAlfaCurto lote) {
        loteMsg = lote;
        return this;
    }

    protected MensagemFactory adicionaErroAMensagem(Descricao erro) {
        erroMsg = erro;
        return this;
    }

    protected MensagemFactory adicionaOrdemProdAMensagem(CodigoAlfaCurto ordem) {
        ordemProdMsg = ordem;
        return this;
    }

    protected MensagemFactory adicionaDepositoAMensagem(CodigoAlfaCurto deposito) {
        depositoMsg = deposito;
        return this;
    }

    protected Maquina maquina() {
        return maquina;
    }

    protected Enum tipoMensagem() {
        return tipoMensagem;
    }

    protected Calendar timestampGeracao() {
        return timestampGeracao;
    }

    protected Calendar timestampEnvio() {
        return timestampEnvio;
    }

    protected CodigoAlfaCurto produtoMsg() {
        return produtoMsg;
    }

    protected int quantidadeMsg() {
        return quantidadeMsg;
    }

    protected CodigoAlfaCurto loteMsg() {
        return loteMsg;
    }

    protected Descricao erroMsg() {
        return erroMsg;
    }

    protected CodigoAlfaCurto ordemProdMsg() {
        return ordemProdMsg;
    }

    protected CodigoAlfaCurto depositoMsg() {
        return depositoMsg;
    }

    protected Mensagem build() {
        return new Mensagem(this.tipoMensagem, this.timestampGeracao, this.timestampEnvio, this.maquina, this.produtoMsg,
                this.quantidadeMsg, this.loteMsg, this.erroMsg, this.ordemProdMsg, this.depositoMsg);
    }
}
