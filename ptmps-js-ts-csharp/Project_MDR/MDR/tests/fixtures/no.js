// Fixtures for testing purposes only
const NoModel = require('../../src/models/no');
const NoDTO = require('../../src/dto/noDTO');


const noRequest = {
    abreviatura: "GAND", nome: "Gandra", latitude: 41,
    longitude: 8, estacaoRecolha: false, pontoRendicao: false
};

const nosFromFile = [{
    "$": {
        ShortName: "GAND", Name: "Gandra", Latitude: 41,
        Longitude: 8, IsDepot: "false", IsReliefPoint: "false"
    }
},
{
    "$": {
        ShortName: "PARED", Name: "Paredes", Latitude: 42,
        Longitude: 9, IsDepot: "false", IsReliefPoint: "false"
    }
}
];

const nosModel = [
    new NoModel("GAND", "Gandra", 41, 8, false, false),
    new NoModel("PARED", "Paredes", 42, 9, false, false)
];

const nosDTO = [
    new NoDTO("GAND", "Gandra", 41, 8, false, false),
    new NoDTO("PARED", "Paredes", 42, 9, false, false)
];

const nosModel2 = [
    new NoModel("GAND", "Gandra", 41, 8, "false", "false"),
    new NoModel("PARED", "Paredes", 42, 9, "false", "false")
];

const nosDTO2 = [
    new NoDTO("GAND", "Gandra", 41, 8, "false", "false"),
    new NoDTO("PARED", "Paredes", 42, 9, "false", "false")
];

module.exports = { noRequest, nosFromFile, nosModel, nosDTO, nosModel2, nosDTO2 };