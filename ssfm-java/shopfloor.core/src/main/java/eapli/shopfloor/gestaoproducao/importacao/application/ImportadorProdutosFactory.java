/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

/**
 *
 *.se.1181483
 */

    /**
     * This factory only has one option, but is here for future further file formats easy implementation
     *
     *.se.1181483
     *
     */
    public final class ImportadorProdutosFactory {

        public ImportadorProdutos build(ImportFileFormat format) {
            switch (format) {
                case CSV:
                    return new ImportadorProdutosCSV();
            }
                    throw new IllegalStateException("Unknown format");
            }
        }
