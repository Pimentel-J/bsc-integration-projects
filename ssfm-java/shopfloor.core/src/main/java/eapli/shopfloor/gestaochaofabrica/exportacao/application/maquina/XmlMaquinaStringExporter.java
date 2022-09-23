package eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlMaquinaStringExporter implements ElementStringExporter<Maquina> {

    private final String HEADER = "<Maquinas>%n";
    private final String ENDING = "</Maquinas>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(Maquina e) {
        Collection<String> maquinas = new ArrayList<>();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(e.dataInstalacaoMaquina().getTime());

        maquinas.add("<maquina>%n");
        maquinas.add(String.format("<idMaquina>%s</idMaquina>%n", e.identity().codigo()));
        maquinas.add(String.format("<numSerie>%s</numSerie>%n", e.numSerie().numero()));
        maquinas.add(String.format("<descricao>%s</descricao>%n", e.descricaoMaquina()));
        maquinas.add(String.format("<dataInstalacao>%s</dataInstalacao>%n", date));
        maquinas.add(String.format("<marca>%s</marca>%n", e.marcaMaquina()));
        maquinas.add(String.format("<modelo>%s</modelo>%n", e.modeloMaquina()));
        maquinas.add(String.format("<idLinhaProducao>%s</idLinhaProducao>%n", e.linhaProducaoMaquina().identity()));
        maquinas.add(String.format("<sequencia>%s</sequencia>%n", e.sequenciaMaquina()));
        maquinas.add(String.format("<idProtocolo>%s</idProtocolo>%n", e.idProtocolo().numero()));
        maquinas.add("</maquina>%n");

        return maquinas;
    }

    @Override
    public String ending() { return ENDING; }
}
