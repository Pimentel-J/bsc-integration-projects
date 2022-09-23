package eapli.shopfloor.gestaoproducao.domain;

import java.util.Calendar;

import eapli.framework.domain.model.DomainFactory;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

public class AtividadeBuilder implements DomainFactory<Atividade>{

	private Maquina maquina;
    private Calendar timestampInicio;
    private Calendar timestampFim;
    private TipoAtividade tipo;
    private Descricao erro;
    
    public AtividadeBuilder withMaquina(Maquina maquina) {
    	this.maquina = maquina;
    	return this;
    }
    
    public AtividadeBuilder withTimestampInicio(Calendar timestampInicio) {
    	this.timestampInicio = timestampInicio;
    	return this;
    }
    
    public AtividadeBuilder withTimestampFim(Calendar timestampFim) {
    	this.timestampFim = timestampFim;
    	return this;
    }
    
    public AtividadeBuilder withTipo(TipoAtividade tipo) {
    	this.tipo = tipo;
    	return this;
    }
    
    public AtividadeBuilder withErro(Descricao erro) {
    	this.erro = erro;
    	return this;
    }
    
    public Maquina maquina() {
    	return this.maquina;
    }
    
    public TipoAtividade tipo() {
    	return this.tipo;
    }
	
	@Override
	public Atividade build() {
		if (this.tipo == TipoAtividade.PARAGEM) {
			return new Atividade(this.maquina, this.timestampInicio, this.timestampFim, this.tipo, this.erro);
		} else {
			return new Atividade(this.maquina, this.timestampInicio, this.timestampFim, this.tipo);
		}
		
	}

}
