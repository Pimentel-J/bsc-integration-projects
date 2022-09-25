var SegmentoDTO = require('../dto/segmentoDTO');
var SegmentoModel = require('../models/segmento');
var SegmentoSchema = require('../infrastructure/mongoDB/schemas/segmentoMongoDB');

function json2dto(json) {
    var segmentoDTO = new SegmentoDTO(
        ordem = json.ordem,
        noOrigem = json.noOrigem,
        noDestino = json.noDestino,
        duracao = json.duracao,
        distancia = json.distancia
    );

    return segmentoDTO;
};

function dto2model(dto) {
    return new SegmentoModel(dto.ordem, dto.noOrigem, dto.noDestino, dto.duracao, dto.distancia);
}

function domain2data(segmentoModel, segmentoSchemaModel, noOrigem, noDestino) {
    segmentoSchemaModel.ordem = segmentoModel.ordem;
    segmentoSchemaModel.noOrigem = noOrigem;
    segmentoSchemaModel.noDestino = noDestino;
    segmentoSchemaModel.duracao = segmentoModel.duracao;
    segmentoSchemaModel.distancia = segmentoModel.distancia;
    return segmentoSchemaModel;
}

module.exports = { json2dto, dto2model, domain2data };