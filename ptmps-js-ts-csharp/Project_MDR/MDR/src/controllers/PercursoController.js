var PercursoService = require('../services/PercursoService');
var percursoMapper = require('../mappers/percursoMapper');
const PercursoSchema = require('../infrastructure/mongoDB/schemas/percursoMongoDB');

function listarPercursos(query, res) {

    PercursoService.listarPercursos(query, res);

}

function listarPercursosIda(query, res) {

    PercursoService.listarPercursosIda(query, res);

}

function listarTodosPercursos(res) {

    PercursoService.listarTodosPercursos(res);

}

function definirPercurso(body, res) {
    var percursoDTO = percursoMapper.json2dto(body);
    PercursoService.definirPercurso(percursoDTO, res);
    
}

function obterPercurso(idPercurso, res) {

    PercursoService.obterPercurso(idPercurso, res);

}

function obterPercursoByLine(idLinha, res) {

    PercursoService.obterPercursoByLine(idLinha, res);

}

async function importarPercursos(percursos) {
    try {
       await PercursoService.importarPercursos(percursos);
    } catch (e) {
        throw e;
    }
    
}

module.exports = { obterPercursoByLine, listarPercursos, listarPercursosIda, definirPercurso , listarTodosPercursos, obterPercurso, importarPercursos};