var express = require('express');
var router = express.Router();
var PercursoController = require('../../controllers/PercursoController');
const Percurso = require('../../models/percurso');


/**
 * Schema Model Percurso
 * @typedef Percurso
 * @property {string} idLinha.required - Codigo da Linha - eg: AGUIA
 * @property {string} idPercurso.required - Identificador do Percurso - eg: AGUIA
 * @property {boolean} ida - Percurso de ida/volta - eg: Aguiar de Sousa
 * @property {SegmentoSchema} segmentos - Segmentos do Percurso
 */

router.post('/percursos', function (req, res, next) {

    /**
    * @route POST /percursos
    * @group percursos - Operacoes nos Percursos
    * @param {Percurso.model} percurso.body.required - definicao do Percurso
    * @returns {object} 200 - Percurso criado
    * @returns {Error}  default - Unexpected error
    */
    console.log(req.body);
    PercursoController.definirPercurso(req.body, res);
    
});



router.get('/percursos', function(req, res, next) {

    if (Object.keys(req.query).length === 0) {
        /* GET listar todos os nos */
        PercursoController.listarTodosPercursos(res);
    } else {
    /* GET listar nos com filtro e ordenacao */
        if (req.query.ida != null) {
            PercursoController.listarPercursosIda(req.query, res);
        }
        else{
            PercursoController.listarPercursos(req.query, res);
        }
    }
    

});


router.get('/percursos/:idPercurso', function (req, res, next) {

    /**
    * @route GET /percursos/:idPercurso
    * @group percursos - Operacoes nos Percursos
    * @param {string} idPercurso.params.required - parametro: abreviatura do Percurso a obter?
    * @returns {object} 200 - O Percurso com o idPercurso especificado
    * @returns {Error}  default - Unexpected error
    */

    PercursoController.obterPercurso(req.params.idPercurso, res);

});

router.get('/percursos/linha/:idLinha', function (req, res, next) {

    /**
    * @route GET /percursos/:idPercurso
    * @group percursos - Operacoes nos Percursos
    * @param {string} idPercurso.params.required - parametro: abreviatura do Percurso a obter?
    * @returns {object} 200 - O Percurso com o idPercurso especificado
    * @returns {Error}  default - Unexpected error
    */

    PercursoController.obterPercursoByLine(req.params.idLinha, res);

});

module.exports = router;
