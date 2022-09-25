const tipoTripulanteMapper = require('../mappers/tipoTripulanteMapper');
var tipoviaturaMapper = require('../mappers/tipoviaturaMapper')
var TipoViaturaService = require('../services/TipoViaturaService');

function criarTipoViatura(body, res) {
    // Validacaoo dos campos

    // Obter DTO atraves do Mapper
    var tpvDTO = tipoviaturaMapper.json2dto(body);

    // Chamar Servico Criar No e enviar-lhe o DTO
    TipoViaturaService.criarTipoViatura(tpvDTO, res);

}

function listarTodosTipos(res) {
    TipoViaturaService.listarTodosTipos(res);

}

function getTipoViatura(codigo, res) {
    TipoViaturaService.getTipoViatura(codigo, res);
}

function altTipoViatura(codigo, body, res) {
    // console.log(body);
    var tpvDTO = tipoviaturaMapper.json2dto(body);
    TipoViaturaService.altTipoViatura(codigo, tpvDTO, res);
}

function delTipoViatura(codigo, res) {
    TipoViaturaService.delTipoViatura(codigo, res);
}

async function criarTiposViaturaFromFile(req, res) {

    try {
        var tpsvDTO = tipoviaturaMapper.objectArray2dto(req);
        await TipoViaturaService.criarTiposViaturaFromFile(tpsvDTO, res);
    } catch (e) {
        throw e;
    }
    

}

module.exports = {
    criarTipoViatura,
    listarTodosTipos,
    getTipoViatura,
    altTipoViatura,
    delTipoViatura,
    criarTiposViaturaFromFile
};