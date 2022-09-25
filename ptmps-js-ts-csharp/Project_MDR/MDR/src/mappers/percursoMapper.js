var PercursoDTO = require('../dto/percursoDTO');
var PercursoModel = require('../models/percurso');
var PercursoSchema = require('../infrastructure/mongoDB/schemas/percursoMongoDB')

function json2dto(json) {
    var percursoDTO = new PercursoDTO(
        idLinha = json.linha,
        idPercurso = json.idPercurso,
        ida = json.ida,
        segmentos = json.segmentos
    );

    return percursoDTO;
};

function dto2model(dto) {
    var percursoModel = new PercursoModel(
        idLinha = dto.idLinha,
        idPercurso = dto.idPercurso,
        ida = dto.ida,
        segmentos = dto.segmentos
    );

    return percursoModel;
}

function domain2data(percursoModel, percursoSchemaModel, linha) {
    percursoSchemaModel.idLinha = linha;
    percursoSchemaModel.idPercurso = percursoModel.idPercurso;
    percursoSchemaModel.ida = percursoModel.ida;
    return percursoSchemaModel;
}

module.exports = { json2dto, dto2model, domain2data };