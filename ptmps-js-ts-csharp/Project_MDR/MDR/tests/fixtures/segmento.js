// Fixtures for testing purposes only
const SegmentoModel = require('../../src/models/segmento');
const SegmentoDTO = require('../../src/dto/segmentoDTO');
const NoModel = require('../../src/models/no')

const segmentoRequest = { ordem: "1", noOrigem: "AGUIA", noDestino: "RECAR", duracao: 600, distancia: 500 };

const segmentosModel = [
    new SegmentoModel("1", "AGUIA", "RECAR", 600, 500),
    new SegmentoModel("2", "RECAR", "PARAD", 300, 250)
];

const segmentosDTO = [
    new SegmentoDTO("1", "AGUIA", "RECAR", 600, 500),
    new SegmentoDTO("2", "RECAR", "PARAD", 300, 250)
];

const no1 = new NoModel("GAND", "Gandra", 41, 8, false, false);
const no2 = new NoModel("PARED", "Paredes", 42, 9, false, false);

const segmentosTestValidar = [
    new SegmentoModel(1, no1, no2, 600, 500),
    new SegmentoModel(2, no1, no2, 300, 250),
    new SegmentoModel(3, no2, no1, 340, 240)
];

var segmentosDTOTestValidar = new SegmentoDTO(1, no1, no2, 600, 500);

module.exports = { segmentoRequest, segmentosModel, segmentosDTO, segmentosTestValidar, segmentosDTOTestValidar };
