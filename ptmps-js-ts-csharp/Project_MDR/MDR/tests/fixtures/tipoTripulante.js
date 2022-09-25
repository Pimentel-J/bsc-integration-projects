// Fixtures for testing purposes only
const TipoTripulanteModel = require('../../src/models/tipoTripulante');
const TipoTripulanteDTO = require('../../src/dto/tipoTripulanteDTO');


const tipoTripulanteRequest = { codigo: "5", descricao: "teste5" };

const tiposTripulanteFromFile = [{ "$": { name: "5", description: "teste5" } },
{ "$": { name: "7", description: "teste7" } }];

const tiposTripulanteModel = [
    new TipoTripulanteModel("5", "teste5"),
    new TipoTripulanteModel("7", "teste7")
];

const tiposTripulanteDTO = [
    new TipoTripulanteDTO("5", "teste5"),
    new TipoTripulanteDTO("7", "teste7")
];

module.exports = { tipoTripulanteRequest, tiposTripulanteFromFile, tiposTripulanteModel, tiposTripulanteDTO };