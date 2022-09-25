var express = require('express');
var router = express.Router();
var NoController = require('../../controllers/NoController');

/**
 * Schema Model No
 * @typedef No
 * @property {string} abreviatura.required - abreviatura do No - eg: AGUIA
 * @property {string} nome.required - nome do No - eg: Aguiar de Sousa
 * @property {number} latitude.required - latitude do No - eg: 41.1293363229325
 * @property {number} longitude.required - longitude do No - eg: -8.4464785432391
 * @property {boolean} estacaoRecolha.required - se estacao recolha - eg: false
 * @property {boolean} pontoRendicao.required - se ponto de rendicao - eg: false
 */


/* POST criar no */
router.post('/nos', function (req, res, next) {

    /**
    * @route POST /nos
    * @group nos - Operacoes nos Nos
    * @param {No.model} no.body.required - definicao do No
    * @returns {object} 200 - No criado
    * @returns {Error}  default - Unexpected error
    */

    NoController.criarNo(req.body, res);
    
});

/* PUT alterar no */
router.put('/nos/:abreviatura', function (req, res, next) {

    /**
    * @route PUT /nos/:abreviatura
    * @group nos - Operacoes nos Nos
    * @param {string} abreviatura.params.required - parametro: abreviatura do No a atualizar
    * @param {No.model} no.body.required - definicao do No atualizado
    * @returns {object} 200 - No atualizado
    * @returns {Error}  default - Unexpected error
    */

    NoController.alterarNo(req.params.abreviatura, req.body, res);

});

/* DELETE remover no */
router.delete('/nos/:abreviatura', function (req, res, next) {

    /**
    * @route PUT /nos/:abreviatura
    * @group nos - Operacoes nos Nos
    * @param {string} abreviatura.params.required - parametro: abreviatura do No a atualizar
    * @param {No.model} no.body.required - definicao do No atualizado
    * @returns {object} 200 - No atualizado
    * @returns {Error}  default - Unexpected error
    */

    NoController.removerNo(req.params.abreviatura, res);

});

router.get('/nos', function(req, res, next) {

    /**
    * @route GET /nos
    * @group nos - Operacoes nos Nos
    * @param {string} abreviatura.query.optional - filtro: abreviatura comeca por ?
    * @param {string} nome.query.optional - filtro: nome comeca por ?
    * @param {string} sort.query.optional - ordenacao: asc / desc
    * @returns {object} 200 - Uma lista de Nos
    * @returns {Error}  default - Unexpected error
    */

    if (Object.keys(req.query).length === 0) {
        /* GET listar todos os nos */
        NoController.listarTodosNos(res);
    } else {
        /* GET listar nos com filtro e ordenacao */
        NoController.listarNos(req.query, res);
    }

});

router.get('/nos/:abreviatura', function (req, res, next) {

    /**
    * @route GET /nos/:abreviatura
    * @group nos - Operacoes nos Nos
    * @param {string} abreviatura.params.required - parametro: abreviatura do No a obter?
    * @returns {object} 200 - O No com a abreviatura especificada
    * @returns {Error}  default - Unexpected error
    */

    NoController.obterNo(req.params.abreviatura, res);

});


module.exports = router;
