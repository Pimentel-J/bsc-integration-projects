const express = require('express');
const router = express.Router();
const TipoViaturaController = require('../../controllers/TipoViaturaController');

/**
 * Schema Model TipoViatura
 * @typedef TipoViatura
 * @property {string} codigo.required - codigo do Tipo Viatura - eg: BUS_GAS
 * @property {string} nome.required - descrição - eg. Bus a Gas
 * @property {string} tipoCombustivel.required - tipo de combustivel - eg: Gas
 * @property {number} autonomia.required - autonomia - eg: 30000.0
 * @property {number} consumoMedio.required - consumo medio - eg: 10.5364
 * @property {number} custoKm.required - custo por km - eg: 7
 * @property {number} velocidadeMedia.required - velocidade Media - eg: 30
 * @property {number} emissoesCO2.required - emissoes de CO2 - eg: 100
 * 
 */

/* POST criar Tipos Viatura */
router.post('/tiposViatura', function(req, res, next) {

    /**
     * @route POST /tipoviatura
     * @group tiposViatura - Operacoes em Tipo Viaturas
     * @param {TipoViatura.model} tipoviatura.body.required - definicao tipo viatura
     * @returns {object} 200 - Tipo Viatura criado
     * @returns {object} default - Unexpected error
     */
    TipoViaturaController.criarTipoViatura(req.body, res);

});

router.get('/tiposViatura', function(req, res) {

    /**
     * @route GET /tiposViatura
     * @group tiposViatura - Operacoes nos Tipos Viatura
     * @returns {object} 200 - Uma lista de Tipos Viatura
     * @returns {Error}  default - Unexpected error
     */

    TipoViaturaController.listarTodosTipos(res);

});

router.get('/tiposViatura/:codigo', function(req, res, next) {

    /**
     * Schema Model TipoViatura
     * @route GET /tiposViatura
     * @group tiposViatura - Operacoes nos Tipos Viatura
     * @param {string} codigo.query.required - filtro tipo viatura
     * @property {string} codigo.required - codigo do Tipo Viatura - eg: BUS_GAS
     * @returns {object} 200 - Uma lista de Tipos Viatura
     * @returns {Error}  default - Unexpected error
     *  
     */
    TipoViaturaController.getTipoViatura(req.params.codigo, res);

});

router.put('/tiposViatura/:codigo', function(req, res, next) {

    /**
     * Schema Model TipoViatura
     * @route PUT /tiposViatura/:codigo
     * @group tiposViatura - Operacoes nos Tipos Viatura
     * @param {string} codigo.params.required - parametro codigo do tipo viatura
     * @param {tipoviatura.model} tipoviatura.body.required - codigo do Tipo Viatura - eg: BUS_GAS
     * @returns {object} 200 - Uma lista de Tipos Viatura
     * @returns {Error}  default - Unexpected error
     *  
     */
    TipoViaturaController.altTipoViatura(req.params.codigo, req.body, res);
});

router.delete('/tiposViatura/:codigo', function(req, res, next) {

    /**
     * Schema Model TipoViatura
     * @route DELETE /tiposViatura/:codigo
     * @group tiposViatura - Operacoes nos Tipos Viatura
     * @param {string} codigo.params.required - parametro codigo do tipo viatura
     * @param {tipoviatura.model} tipoviatura.body.required - codigo do Tipo Viatura - eg: BUS_GAS
     * @returns {object} 200 - Uma lista de Tipos Viatura
     * @returns {Error}  default - Unexpected error
     *  
     */
    TipoViaturaController.delTipoViatura(req.params.codigo, res);

});


module.exports = router;