/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.shopfloor.gestaoproducao.exportacao.application;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.util.TemplateMethod;

public class ExportadorErrosProdutosService {

    private static final Logger logger = LogManager.getLogger(ExportadorErrosProdutosService.class);
    private final ExportadorErrosProdutosFactory factory = new ExportadorErrosProdutosFactory();

    /**
     * Exports errors that arise from previous importation. This a "template method" working in conjunction with a
     * Strategy. If the {@link ExportadorErrosProdutos} interface has just the
     * export method we would be repeating the logic of traversing the dish list
     * in every implementation!
     *
     * @param dishes
     * @param filename
     * @param exporter
     * @throws IOException
     */
    @TemplateMethod
    public void export(Iterable<String> linhasExport, String nomeFicheiro, ExportFileFormat format)
            throws IOException {
        final ExportadorErrosProdutos exporter = factory.build(format);
        try {
            exporter.begin(nomeFicheiro);

            boolean hasPrevious = false;
            for (final String e : linhasExport) {
                if (hasPrevious) {
                    exporter.elementSeparator();
                }

                exporter.element(e);
                hasPrevious = true;
            }

            exporter.end();
        } catch (final IOException e) {
            logger.error("Problema a exportar erros dos produtos", e);
            throw e;
        } finally {
            exporter.cleanup();
        }
    }
}
