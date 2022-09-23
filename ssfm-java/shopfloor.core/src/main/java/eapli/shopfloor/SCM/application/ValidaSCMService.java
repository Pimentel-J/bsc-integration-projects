/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.application;

import eapli.shopfloor.SCM.repositories.IdParaIpRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.NumeroSerie;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import java.net.InetAddress;

/**
 *
 *.se.1181483
 */
public class ValidaSCMService {

    private final MaquinaRepository maqRepository = PersistenceContext.repositories().maquinas();
    private final IdParaIpRepository idParaIpRepository = PersistenceContext.repositories().idsParaIps();

    private IdParaIp associarIpAId(int numInterno, InetAddress ipAddr) {
        final IdParaIp newIdParaIp = new IdParaIp(NumeroSerie.valueOf(numInterno), ipAddr);

        return this.idParaIpRepository.save(newIdParaIp);
    }

    protected boolean validaComunicacao(InetAddress ip, int version, int code, int id) {
        boolean resposta, ipAssociado;
        resposta = containsMaquina(id);
        ipAssociado = ipAssociadoAMaquina(id, ip);
        if (resposta) {
            if (code == 0) {
                if (ipNaoAssociado(ip) && !contemId(id)) {// && id não associado
                    associarIpAId(id, ip);
                } else if (!ipAssociado) {
                    resposta = false;
                }
            }
            if (!ipAssociado && code != 0) {
                resposta = false;
            }
        }
        return resposta;
    }

    
    protected boolean containsMaquina(int idInterno){
        return maqRepository.containsMaquina(NumeroSerie.valueOf(idInterno))!=null;
    }

    protected Maquina devolveMaquina(String idMaquina) {
        return this.maqRepository.ofIdentity(CodigoAlfaCurto.valueOf(idMaquina)).get();
    }

    protected boolean contemId(int idMaquina){
        return this.idParaIpRepository.obterIpDeId(NumeroSerie.valueOf(idMaquina)) != null;
    }
 
    
    protected InetAddress whatIsIP(int numInterno){
        return idParaIpRepository.obterIpDeId(NumeroSerie.valueOf(numInterno)).enderecoIp();
    }

    protected boolean ipAssociadoAMaquina(int numInterno, InetAddress endereco) {
        if (idParaIpRepository.obterIdDeIp(endereco) != null) {
            Integer idInterno = idForIp(endereco).idInterno().numero();
            return idInterno - numInterno == 0;
        }
        return false;
    }

    protected IdParaIp idForIp(InetAddress enderecoIp) {
        return idParaIpRepository.obterIdDeIp(enderecoIp);
    }
    

    protected boolean ipNaoAssociado(InetAddress endereco) {
        IdParaIp idip = idForIp(endereco);
        if (idip == null) {
            return true;
        }
        return false;
    }

}
