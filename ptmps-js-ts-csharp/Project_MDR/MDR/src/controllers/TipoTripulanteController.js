const TipoTripulanteMapper = require('../mappers/tipoTripulanteMapper');
const TipoTripulanteService = require('../services/TipoTripulanteService');

/**
 * Cria DTO Tipo de Tripulante
 * @param {object} req 
 * @param {object} res 
 */
function criarTipoTripulante(req, res, next) {

    // Obter DTO através do Mapper
    let tipoTripulanteDTO = TipoTripulanteMapper.json2dto(req);

    // Pede a Serviço para CriarTipoTripulante e envia o DTO
    TipoTripulanteService.criarTipoTripulante(tipoTripulanteDTO, res);
}

/**
 * Listar Tipos de Tripulante
 * @param {object} req 
 * @param {object} res 
 */
function listarTodosTiposTripulante(req, res) {

    TipoTripulanteService.listarTodosTiposTripulante(req, res);
}

/**
 * Obter um Tipo de Tripulante
 * @param {object} req 
 * @param {object} res 
 */
function obterTipoTripulante(req, res) {

    TipoTripulanteService.obterTipoTripulante(req, res);
}

/**
 * Cria DTO Tipo de Tripulante importados do ficheiro
 * @param {object} req 
 */
async function criarTiposTripulanteFromFile(req) {

    try {
        // Obter DTO através do Mapper
        let tiposTripulanteDTO = TipoTripulanteMapper.objectArray2dto(req);

        // Pede a Serviço para CriarTipoTripulante e envia o DTO
        await TipoTripulanteService.criarTiposTripulanteFromFile(tiposTripulanteDTO);

    } catch (e) {
        throw e;
    }

}

/**
 * Atualizar um Tipo de Tripulante
 * @param {string} codigo 
 * @param {object} req 
 * @param {object} res 
 */
function alterarTipoTripulante(codigo, req, res) {
    // console.log(body);
    let tipoTripulanteDTO = TipoTripulanteMapper.json2dto(req);
    TipoTripulanteService.alterarTipoTripulante(codigo, tipoTripulanteDTO, res);
}

/**
 * Apaga um Tipo de Tripulante
 * @param {string} codigo 
 * @param {object} res 
 */
function apagarTipoTripulante(codigo, res) {
    TipoTripulanteService.apagarTipoTripulante(codigo, res);
}

module.exports = {
    criarTipoTripulante,
    criarTiposTripulanteFromFile,
    listarTodosTiposTripulante,
    obterTipoTripulante,
    alterarTipoTripulante,
    apagarTipoTripulante
};