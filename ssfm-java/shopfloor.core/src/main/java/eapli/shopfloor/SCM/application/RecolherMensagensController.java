/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

import java.net.InetAddress;
import java.util.NoSuchElementException;

/**
 *
 *.se.1181483
 */
@UseCaseController
public class RecolherMensagensController {

    private final ValidaSCMService valService = new ValidaSCMService();
    private final MensagemService msgService = new MensagemService();


    public void guardarMensagem(String[] mensagem) {
        try {
            
            if (mensagem != null) {
            Maquina maquina = valService.devolveMaquina(mensagem[0]);
            
                msgService.guardar(maquina, mensagem);
            }
            
        } catch (NoSuchElementException nse) {
            System.out.println("ERRO - Mensagem não guardada");
        }
    }
    
    public boolean recolherMensagem(InetAddress ip, int version, int code, int id, int dataLength, String rawMessage){
        
        boolean resposta = this.valService.validaComunicacao(ip, version, code, id);
        
        if (resposta && dataLength > 0) {
            String[] mensagem = msgService.parseMensagem(rawMessage);
            guardarMensagem(mensagem);
        }
        
        return resposta;
    }

    
}
