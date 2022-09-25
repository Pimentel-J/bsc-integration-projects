// Fixtures for testing purposes only
const LinhaModel = require('../../src/models/linha');
const LinhaDTO = require('../../src/dto/linhaDTO');

const linhaRequest = {
    codigo: "Linha1", nome: "teste",
    permissoesTipoViatura: ["BUS_ELECT"], permissoesTipoMotorista: ["PTENG"], cor: "green"
};

const linhaFromFile = {
    codigo: "Linha1", nome: "teste",
    permissaoViatura: "BUS_ELECT", permissaoMotorista: "PTENG", cor: "green"
};

const linhaModel = new LinhaModel("Linha1", "teste", ["BUS_ELECT"], ["PTENG"], "green");

const linhaDTO = new LinhaDTO("Linha1", "teste", ["PTENG"], ["BUS_ELECT"], "green");

const linhaDtoSemPermissoes = new LinhaDTO("Linha1", "teste", [], [], "green");

module.exports = { linhaRequest, linhaFromFile, linhaModel, linhaDTO, linhaDtoSemPermissoes };