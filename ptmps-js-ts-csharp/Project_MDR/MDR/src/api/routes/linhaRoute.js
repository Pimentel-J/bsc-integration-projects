var express = require('express');
var router = express.Router();
var LinhaController = require('../../controllers/LinhaController');
const PERMISSOES_VIATURAS =1;
const PERMISSOES_MOTORISTAS =2;
const FUNCAO_CRIAR =1;
const FUNCAO_ALTERAR=2;

/* POST criar linha */
router.post('/linhas', function (req, res, next) {

    /**
    * @route POST /linha
    * @group linha - Operacoes Linhas
    * @param {Linha.model} no.body.required - definicao da Linha
    * @returns {object} 200 - Linha criada
    * @returns {Error}  default - Unexpected error
    */

    LinhaController.criarLinha(0,req.body, res, FUNCAO_CRIAR);

});

/* PUT alterar linha */
router.put('/linhas/:codigo', function (req, res, next) {

    /**
    * @route PUT /linha/:codigo
    * @group linha - Operacoes Linhas
    * @param {string} codigo.params.required - parametro: codigo da Linha a atualizar
    * @param {Linha.model} no.body.required - definicao da Linha atualizada
    * @returns {object} 200 - Linha atualizada
    * @returns {Error}  default - Unexpected error
    */

    LinhaController.criarLinha(req.params.codigo, req.body, res, FUNCAO_ALTERAR);

});

/* DELETE remover linha */
router.delete('/linhas/:codigo', function (req, res, next) {

    /**
    * @route PUT /linha/:codigo
    * @group linha - Operacoes Linhas
    * @param {string} codigo.params.required - parametro: codigo da Linha a atualizar
    * @param {Linha.model} no.body.required - definicao da Linha atualizada
    * @returns {object} 200 - Linha atualizada
    * @returns {Error}  default - Unexpected error
    */

    LinhaController.removerLinha(req.params.codigo, res);

});

/**
 * get linha com requesitos
 */
router.get('/linhas', function (req, res, next) {

    /**
    * @route GET /linha
    * @group linha - Operacoes Linhas
    * @param {string} codigo.query.optional - filtro: codigo comeca por ?
    * @param {string} nome.query.optional - filtro: nome comeca por ?
    * @param {string} sort.query.optional - ordenacao: asc / desc
    * @returns {object} 200 - Uma lista de Linhas
    * @returns {Error}  default - Unexpected error
    */

  //  if (Object.keys(req.query).length === 0) {
        /* GET listar todos as linha */
        LinhaController.listarTodosLinhas(res);
  /*  } else {
    
        LinhaController.listarLinhasWithC(req.query, res);
    }*/

});

/**
 * devolve a lista das permissoes de viaturas da linha
 */
router.get('/linhas/pv/:codigo', function (req, res, next) {
    LinhaController.obterPermissoes(PERMISSOES_VIATURAS,req.params.codigo, res);
});

/**
 * devolve a lista das permissoes de motoristas da linha
 */
router.get('/linhas/pm/:codigo', function (req, res, next) {
    LinhaController.obterPermissoes(PERMISSOES_MOTORISTAS, req.params.codigo, res);
});

/**
 * get da linha 
 */
router.get('/linhas/:codigo', function (req, res, next) {

    /**
    * @route GET /linha/:codigo
    * @group linha - Operacoes Linhas
    * @param {string} codigo.params.required - parametro: codigo da Linha a obter?
    * @returns {object} 200 - O Linha com o codigo especificado
    * @returns {Error}  default - Unexpected error
    */

    LinhaController.obterLinha(req.params.codigo, res);

});


module.exports = router;
