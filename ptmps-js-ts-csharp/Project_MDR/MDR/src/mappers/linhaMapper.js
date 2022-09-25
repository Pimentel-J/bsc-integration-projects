var LinhaDTO = require('../dto/linhaDTO');
var LinhaModel = require('../models/linha');
//var LinhaSchema = require('../infrastructure/mongoDB/schemas/linhaMongoDB')


function json2dto(json) {
    var linhaDTO = new LinhaDTO(
        codigo = json.codigo,
        nome = json.nome,
        permissoesTipoMotorista = json.permissoesTipoMotorista,
        permissoesTipoViatura = json.permissoesTipoViatura,
        cor = json.cor
        );

    return linhaDTO;
};

function dto2model(dto) {
    var linhaModel = new LinhaModel(
        codigo = dto.codigo,
        nome = dto.nome,
        permissoesTipoMotorista = dto.permissoesTipoMotorista,
        permissoesTipoViatura = dto.permissoesTipoViatura,
        //noFinal = dto.noFinal,
        cor = dto.cor
    );
    return linhaModel;
}

function domain2data(linhaModel, linhaSchemaModel) {
    //var linhaSchema = LinhaSchema();
    linhaSchemaModel.codigo = linhaModel.codigo;
    linhaSchemaModel.nome = linhaModel.nome;
    linhaSchemaModel.cor = linhaModel.cor;
    return linhaSchemaModel;
}

function data2dto(cod,n,pv,pm,cor ){
    var linhaDTO = new LinhaDTO(
        codigo =cod,
        nome= n,
        permissoesTipoMotorista = pm,
        permissoesTipoViatura = pv,
        cor = cor
    );
        return linhaDTO;
}

module.exports = { json2dto, dto2model, domain2data, data2dto };