/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.application;

import eapli.framework.time.util.Calendars;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.domain.MensagemFactory;
import eapli.shopfloor.SCM.domain.TipoMensagem;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 *.se.1181483
 */
public class MensagemService {
    
    private final MensagemRepository msgRepository = PersistenceContext.repositories().mensagens();
    private static final Logger logger = LogManager.getLogger(MensagemService.class);
    
    public void guardar(Maquina maquina, String [] mensagemBruta){
        try{
            Calendar timestampEnvio = Calendar.getInstance(new Locale("pt"));
            Mensagem msg = new Mensagem();
            Enum tipo = TipoMensagem.valueOf(mensagemBruta[1]);
            
            if(tipo.toString().equalsIgnoreCase("C0") ||tipo.toString().equalsIgnoreCase("C9")
                    || tipo.toString().equalsIgnoreCase("P2")){
                
               msg = MensagemFactory.criaMensagemConsumoEntregaEstorno(maquina, mensagemBruta, timestampEnvio);
            }
            if(tipo.toString().equalsIgnoreCase("S0") ||tipo.toString().equalsIgnoreCase("S9")){
                
               msg = MensagemFactory.criaMensagemInicioFimAtividade(maquina, mensagemBruta, timestampEnvio);
            }
            if(tipo.toString().equalsIgnoreCase("P1")){
                
               msg = MensagemFactory.criaMensagemProducao(maquina, mensagemBruta, timestampEnvio);
            }
            if(tipo.toString().equalsIgnoreCase("S1")){
                
               msg = MensagemFactory.criaMensagemRetomaAtividade(maquina, mensagemBruta, timestampEnvio);
            }
            if(tipo.toString().equalsIgnoreCase("S8")){
                
               msg = MensagemFactory.criaMensagemParagem(maquina, mensagemBruta, timestampEnvio);
            }
            
            msgRepository.save(msg);
            
        }catch(final IllegalArgumentException iae){
            logger.error("Tipo de mensagem incorreto", iae);
            throw iae;
        }
        
        
    }

    protected String[] parseMensagem(String rawMessage) {
        return rawMessage.split(";");
    }
    
}
