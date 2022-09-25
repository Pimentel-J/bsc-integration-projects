const TipoTripulanteMapper = require('../mappers/tipoTripulanteMapper');
const TipoTripulanteRepo = require('../repositories/tipoTripulanteRepo');

/**
 * Cria model tipo de tripulante
 * @param {object} tipoTripulanteDTO 
 * @param {object} res 
 */
function criarTipoTripulante(tipoTripulanteDTO, res) {

    // Cria modelo Tipo de Tripulante
    let tipoTripulanteModel = TipoTripulanteMapper.dto2model(tipoTripulanteDTO);

    // Pede ao Repositório para criar o TipoTripulante
    TipoTripulanteRepo.save(tipoTripulanteModel, res);
}

/**
 * Listar Tipos de Tripulante
 * @param {object} req 
 * @param {object} res 
 */
function listarTodosTiposTripulante(req, res) {
    
    TipoTripulanteRepo.listarTodosTiposTripulante(res);
}

/**
 * Obter um Tipo de Tripulante
 * @param {object} req 
 * @param {object} res 
 */
function obterTipoTripulante(req, res) {
    
    TipoTripulanteRepo.obterTipoTripulante(req, res);
}

/**
 * Cria models Tipo de Tripulante importados do ficheiro
 * @param {object} tipoTripulanteDTO 
 */
async function criarTiposTripulanteFromFile(tiposTripulanteDTO) {

    try {
        // Cria modelo Tipo de tripulante
        let tiposTripulanteModel = TipoTripulanteMapper.dtoArray2Model(tiposTripulanteDTO);

        // Pede ao Repositório para criar o TipoTripulante

        await TipoTripulanteRepo.saveMany(tiposTripulanteModel); //.catch(e => { console.log('chegou aqui'); throw e; });

    } catch (e) {
        throw e;
    }

}

/**
 * Atualizar um Tipo de Tripulante
 * @param {string} codigo 
 * @param {object} tipoTripulanteDTO 
 * @param {object} res 
 */
function alterarTipoTripulante(codigo, tipoTripulanteDTO, res) {
    let tipoTripulanteModel = TipoTripulanteMapper.dto2model(tipoTripulanteDTO);
    TipoTripulanteRepo.alterarTipoTripulante(codigo, tipoTripulanteModel, res);
}

/**
 * Apaga um Tipo de Tripulante
 * @param {string} codigo 
 * @param {object} res 
 */
function apagarTipoTripulante(codigo, res) {
    TipoTripulanteRepo.apagarTipoTripulante(codigo, res);
}

module.exports = { 
    criarTipoTripulante, 
    criarTiposTripulanteFromFile, 
    listarTodosTiposTripulante, 
    obterTipoTripulante, 
    alterarTipoTripulante,
    apagarTipoTripulante
};