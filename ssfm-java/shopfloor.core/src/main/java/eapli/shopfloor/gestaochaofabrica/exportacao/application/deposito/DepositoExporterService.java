package eapli.shopfloor.gestaochaofabrica.exportacao.application.deposito;

import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Serviço com um template do método para exportar o objeto Depósito
 *
 *João Pimentel 
 */
public class DepositoExporterService {

    private final DepositoRepository repository = PersistenceContext.repositories().depositos();

    public Collection<String> exportString() {
        Collection<String> report = new ArrayList<>();
        XmlDepositoStringExporter exporter = new XmlDepositoStringExporter();
        Iterable<Deposito> depositos = repository.findAll();

        if (!depositos.iterator().hasNext()) {
            System.out.println("Não existem depósitos");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final Deposito deposito : depositos) {
            report.addAll(exporter.element(deposito));
        }

        report.add(exporter.ending());

        return report;
    }
}
