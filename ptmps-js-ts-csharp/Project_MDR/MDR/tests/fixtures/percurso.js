// Fixtures for testing purposes only
const PercursoModel = require('../../src/models/percurso');
const PercursoDTO = require('../../src/dto/percursoDTO');
const Segmento = require('./segmento');

const percursoRequest = {
    linha: "Line1", idPercurso: "Path1", ida: true,
    segmentos: [{ ordem: "1", noOrigem: "AGUIA", noDestino: "RECAR", duracao: 600, distancia: 500 },
    { ordem: "2", noOrigem: "RECAR", noDestino: "PARAD", duracao: 300, distancia: 250 }]
};

let percursoSchemaModel = { idLinha: "Line1", idPercurso: "1", ida: true };

const percursosModel = [
    new PercursoModel("Line1", "Path1", true, Segmento.segmentosModel),
    new PercursoModel("Line2", "Path2", true, Segmento.segmentosModel)
];

const percursosModel1Segmento = [
    new PercursoModel("Line1", "Path1", true, [Segmento.segmentosModel[0]]),
    new PercursoModel("Line2", "Path2", true, [Segmento.segmentosModel[1]])
];

const percursosDTO = [
    new PercursoDTO("Line1", "Path1", true, Segmento.segmentosDTO),
    new PercursoDTO("Line2", "Path2", true, Segmento.segmentosDTO)
];

module.exports = { percursoRequest, percursosModel, percursosDTO, percursoSchemaModel, percursosModel1Segmento };
