var noMapper = require('../mappers/noMapper');
var NoRepo = require('../repositories/noRepo');

function criarNo(noDTO, res) {

    // Cria modelo N�
    var noModel = noMapper.dto2model(noDTO);

    // Pede ao Repositorio para criar o N�
    NoRepo.save(noModel, res);
    
}

function listarNos(query, res) {

    NoRepo.listarNos(query, res);

}

function obterNo(abreviatura, res) {

    NoRepo.obterNo(abreviatura, res);

}

function removerNo(abreviatura, res) {

    NoRepo.removerNo(abreviatura, res);

}

function alterarNo(abreviatura, noDTO, res) {

    // Cria modelo N�
    var noModel = noMapper.dto2model(noDTO);

    NoRepo.alterarNo(abreviatura, noModel, res);

}

function listarTodosNos(res) {

    NoRepo.listarTodosNos(res);

}

async function criarNosFromFile(nosDTO, res) {

    try {
        // Cria modelo No
        var nosModel = noMapper.dtoArray2Model(nosDTO);

        // Pede ao Repositório para criar o TipoTripulante

        await NoRepo.saveMany(nosModel); //.catch(e => { console.log('chegou aqui'); throw e; });

    } catch (e) {
        throw e;
    }


}


module.exports = { listarNos, criarNo, listarTodosNos, obterNo, alterarNo, removerNo, criarNosFromFile};

