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

import eapli.framework.util.Strategy;

/**
 * The Strategy for exporting dishes to external formats.
 *
 *Paulo Gandra de Sousa 28/04/2020
 *
 */
@Strategy
public interface ExportadorErrosProdutos {

    /**
     * Initiate the export process. The implementation should open the underlying resource (e.g., file) and create the
     * "document start"/"header" for the respective format.
     *
     * @param filename
     */
    void begin(String filename) throws IOException;

    /**
     * Export one single element.
     *
     * @param e
     */
    void element(String e);

    /**
     * Indicates that a new element will be created.
     */
    void elementSeparator();

    /**
     * Indicates that there are no more elements to export. The implementation should create any "document closing"
     * element it might need and close the underlying resource.
     */
    void end();

    /**
     * Gives the exporter implementation a change to cleanup in case some exception has occurred.
     */
    void cleanup();
}
