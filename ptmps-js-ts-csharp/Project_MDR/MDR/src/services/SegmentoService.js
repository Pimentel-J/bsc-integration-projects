var segmentoMapper = require('../mappers/segmentoMapper');
var SegmentoRepo = require('../repositories/segmentoRepo');

function criarSegmento(segmentoDTO, res) {

    // Cria modelo Segmento
    var segmentoModel = segmentoMapper.dto2model(segmentoDTO);

    

    // Pede ao Repositorio para criar o Segmento
    SegmentoRepo.save(segmentoModel,  res);
    //return segmentoModel;
}

module.exports = { criarSegmento };

