// Fixtures for testing purposes only
const TipoViaturaModel = require('../../src/models/tipoViatura');
const TipoViaturaDTO = require('../../src/dto/tipoViaturaDTO');

const tipoViaturaRequest = {
    codigo: "BUS_GAS01",
    nome: "BUS_GAS01",
    tipoCombustivel: "GPL",
    autonomia: 30000,
    consumoMedio: 8.45,
    custoKm: 0.8,
    velocidadeMedia: 35,
    emissoesCO2: 700
};

const tiposViaturaFromFile = [{
    "$": {
        Name: "BUS_GAS01",
        EnergySource: "20",
        Autonomy: 30000,
        Consumption: 8.45,
        Cost: 0.8,
        AverageSpeed: 35,
        Emissions: 700
    }
},
{
    "$": {
        Name: "BUS_GPL7",
        EnergySource: "20",
        Autonomy: 200000,
        Consumption: 40.50,
        Cost: 18.50,
        AverageSpeed: 50,
        Emissions: 700
    }
}];

const tiposViaturaModel = [
    new TipoViaturaModel("BUS_GAS01", "BUS_GAS01", "GPL", 30000, 8.45, 0.8, 35, 700),
    new TipoViaturaModel("BUS_GPL7", "BUS_GPL7", "GPL", 200000.0, 40.50, 18.50, 50, 700)
];

const tiposViaturaDTO = [
    new TipoViaturaDTO("BUS_GAS01", "BUS_GAS01", "GPL", 30000, 8.45, 0.8, 35, 700),
    new TipoViaturaDTO("BUS_GPL7", "BUS_GPL7", "GPL", 200000.0, 40.50, 18.50, 50, 700)
];

module.exports = { tipoViaturaRequest, tiposViaturaFromFile, tiposViaturaModel, tiposViaturaDTO };