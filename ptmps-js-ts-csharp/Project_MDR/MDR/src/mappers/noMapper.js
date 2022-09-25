var NoDTO = require('../dto/noDTO');
var NoModel = require('../models/no');

function json2dto(json) {

    var noDTO = new NoDTO(
        abreviatura = json.abreviatura,
        nome = json.nome,
        latitude = json.latitude,
        longitude = json.longitude,
        estacaoRecolha = json.estacaoRecolha,
        pontoRendicao = json.pontoRendicao,
        modeloMapa = json.modeloMapa
    );

    return noDTO;
};

function domain2data(noModel, noSchemaModel) {

    //var noSchema = NoSchema;
    //var NoSchemaModel = mongoose.model('No', noSchema);
    //var noSchemaModel = NoSchemaModel();
    noSchemaModel.abreviatura = noModel.abreviatura;
    noSchemaModel.nome = noModel.nome;
    noSchemaModel.latitude = noModel.latitude;
    noSchemaModel.longitude = noModel.longitude;
    noSchemaModel.estacaoRecolha = noModel.estacaoRecolha;
    noSchemaModel.pontoRendicao = noModel.pontoRendicao;
    noSchemaModel.modeloMapa = noModel.modeloMapa;

    return noSchemaModel;
}

function dto2model(dto) {
    var noModel = new NoModel(
        abreviatura = dto.abreviatura,
        nome = dto.nome,
        latitude = dto.latitude,
        longitude = dto.longitude,
        estacaoRecolha = dto.estacaoRecolha,
        pontoRendicao = dto.pontoRendicao,
        modeloMapa = dto.modeloMapa
    );

    return noModel;
}

function fileObj2dto(obj) {
    var noDTO = new NoDTO(
        abreviatura = obj.$.ShortName,
        codigo = obj.$.Name,
        latitude = obj.$.Latitude,
        longitude = obj.$.Longitude,
        estacaoRecolha = obj.$.IsDepot.toLowerCase(),
        pontoRendicao = obj.$.IsReliefPoint.toLowerCase()
    );
    return noDTO;
};

function objectArray2dto(objArray) {
    let dtoList = [];
    for (let item of objArray) {
        dtoList.push(fileObj2dto(item));
    }
    //console.log(dtoList);
    return dtoList;
}

/**
 * Converts DTO to model array
 * @param {Array} dtoArray 
 */
function dtoArray2Model(dtoArray) {
    let modelList = [];
    for (let item of dtoArray) {
        modelList.push(dto2model(item));
    }
    //console.log(modelList);
    return modelList;
}


module.exports = { domain2data, json2dto, dto2model, dtoArray2Model, objectArray2dto, domain2data, fileObj2dto };