const express = require('express');
const router = express.Router();
const TipoTripulanteController = require('../../controllers/TipoTripulanteController');
const schema = require('../validations/criarTipoTripulante');
const middleware = require('../middlewares/validarRequest');

/**
 * Schema Model TipoTripulante
 * @typedef TipoTripulante
 * @property {string} codigo.required - codigo do TipoTripulante - eg: PTESP
 * @property {string} descricao.required - descricao do TipoTripulante - eg: Motorista senior fluente em lingua espanhola
 * @function middleware @param schema a validar
 */


/**
 * @route POST /tiposTripulante
 * @group tiposTripulante - Operacoes nos Tipos de Tripulante
 * @param {TipoTripulante.model} tipoTripulante.body.required - definicao do TipoTripulante
 * @returns {object} 200 - TipoTripulante criado
 * @returns {Error}  default - Unexpected error
 * 
 */
router.post('/tiposTripulante', middleware(schema.schemaUnit), (req, res, next) => {

    TipoTripulanteController.criarTipoTripulante(req.body, res, next);
});

/**
* @route GET /tiposTripulante
* @group tiposTripulante - Operacoes nos Tipos de Tripulante
* @returns {object} 200 - Uma lista de Tipos de Tripulante
* @returns {Error}  default - Unexpected error
 */
router.get('/tiposTripulante', function (req, res) {

    TipoTripulanteController.listarTodosTiposTripulante(req, res);
});

/**
* @route GET /tiposTripulante/:codigo
* @group tiposTripulante - Operacoes nos Tipos de Tripulante
* @param {string} codigo.params.required - codigo do Tipo Tripulante a obter? eg: PTENG
* @returns {object} 200 - O Tipo de Tripulante com o codigo especificado
* @returns {Error}  default - Unexpected error
 */
router.get('/tiposTripulante/:codigo', function (req, res) {

    TipoTripulanteController.obterTipoTripulante(req.params.codigo, res);
});

/**
 * @route PUT /tiposTripulante/:codigo
 * @group tiposTripulante - Operacoes nos Tipos de Tripulante
 * @param {string} codigo.params.required - parametro: codigo do Tipo Tripulante a alterar? eg: PTENG
 * @returns {object} 200 - Tipo de Tripulante atualizado
 * @returns {Error}  default - Unexpected error
 *  
 */
router.put('/tiposTripulante/:codigo', middleware(schema.schemaUnitAlterar), function(req, res) {

    TipoTripulanteController.alterarTipoTripulante(req.params.codigo, req.body, res);
});

/**
* @route DELETE /tiposTripulante/:codigo
 * @group tiposTripulante - Operacoes nos Tipos de Tripulante
 * @param {string} codigo.params.required - parametro: codigo do Tipo Tripulante a apagar? eg: PTENG
 * @returns {object} 200 - Tipo de Tripulante apagado
 * @returns {Error}  default - Unexpected error
*/
router.delete('/tiposTripulante/:codigo', function (req, res) {
    TipoTripulanteController.apagarTipoTripulante(req.params.codigo, res);
});

module.exports = router;
