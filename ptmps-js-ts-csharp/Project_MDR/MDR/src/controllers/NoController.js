var noMapper = require('../mappers/noMapper')
var NoService = require('../services/NoService');

function listarNos(query, res) {

    NoService.listarNos(query, res);

}

function obterNo(abreviatura, res) {

    NoService.obterNo(abreviatura, res);

}

function removerNo(abreviatura, res) {

    NoService.removerNo(abreviatura, res);

}

function listarTodosNos(res) {

    NoService.listarTodosNos(res);

}

function alterarNo(abreviatura, body, res) {

    if (body == undefined) {
        res.status(400).json({ message: 'Please send a JSON body in the request!' });
    } else {
        var noDTO = noMapper.json2dto(body);

        NoService.alterarNo(abreviatura, noDTO, res);

    }

}

function criarNo(body, res) {

    // Obter DTO atrav�s do Mapper
    var noDTO = noMapper.json2dto(body);

    // Chamar Servi�o Criar N� e enviar-lhe o DTO
    NoService.criarNo(noDTO, res);
    
}

async function criarNosFromFile(req) {

    try {
        // Obter DTO através do Mapper
        var nosDTO = noMapper.objectArray2dto(req);

        // Pede a Serviço para CriarTipoTripulante e envia o DTO
        await NoService.criarNosFromFile(nosDTO);

    } catch (e) {
        throw e;
    }

}


module.exports = { criarNo, listarNos, listarTodosNos, obterNo, alterarNo, removerNo, criarNosFromFile};