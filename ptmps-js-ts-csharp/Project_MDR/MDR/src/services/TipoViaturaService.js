var tpvRepo = require('../repositories/tipoviaturaRepo');
var tpvMapper = require('../mappers/tipoviaturaMapper');


function criarTipoViatura(tpvDTO, res) {
    // criar modelo TipoViatura
    var tpvModel = tpvMapper.dto2model(tpvDTO);

    // pede ao repositorio para criar tipo viatura
    tpvRepo.save(tpvModel, res);

}

function listarTodosTipos(res) {
    tpvRepo.listarTodosTipos(res);

}

function getTipoViatura(codigo, res) {
    tpvRepo.getTipoViatura(codigo, res);
}

function altTipoViatura(codigo, dto, res) {
    var tpvModel = tpvMapper.dto2model(dto);
    tpvRepo.altTipoViatura(codigo, tpvModel, res);
}

function delTipoViatura(codigo, res) {
    tpvRepo.delTipoViatura(codigo, res);
}

/**
 * Cria models Tipo Viatura importados do ficheiro
 * @param {object} tpvsDTO 
 * @param {object} res 
 */
async function criarTiposViaturaFromFile(tpvsDTO, res) {

    try {
        var tpvsModel = tpvMapper.dtoArray2Model(tpvsDTO);
        await tpvRepo.saveMany(tpvsModel, res);
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