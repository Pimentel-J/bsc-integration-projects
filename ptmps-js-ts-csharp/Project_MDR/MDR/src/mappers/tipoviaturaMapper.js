var TipoViaturaDTO = require('../dto/tipoViaturaDTO');
var TipoViaturaModel = require('../models/tipoViatura');

function json2dto(json) {
    var tpvDTO = new TipoViaturaDTO(
        codigo = json.codigo,
        nome = json.nome,
        tipoCombustivel = json.tipoCombustivel,
        autonomia = json.autonomia,
        consumoMedio = json.consumoMedio,
        custoKm = json.custoKm,
        velocidadeMedia = json.velocidadeMedia,
        emissoesCO2 = json.emissoesCO2
    );
    return tpvDTO;

};

function dto2model(dto) {

    var tpvModel = new TipoViaturaModel(
        codigo = dto.codigo,
        nome = dto.nome,
        tipoCombustivel = dto.tipoCombustivel,
        autonomia = dto.autonomia,
        consumoMedio = dto.consumoMedio,
        custoKm = dto.custoKm,
        velocidadeMedia = dto.velocidadeMedia,
        emissoesCO2 = dto.emissoesCO2
    );
    return tpvModel;
}


function domain2data(tpvModel, tpvSchemaModel) {

    //var TpvSchemaModel = mongoose.model('TipoViatura',TipoViaturaSchema);
    // var tpvSchemaModel = new TpvSchemaModel();
    tpvSchemaModel.codigo = tpvModel.codigo;
    tpvSchemaModel.nome = tpvModel.nome;
    tpvSchemaModel.tipoCombustivel = tpvModel.tipoCombustivel;
    tpvSchemaModel.autonomia = tpvModel.autonomia;
    tpvSchemaModel.consumoMedio = tpvModel.consumoMedio;
    tpvSchemaModel.custoKm = tpvModel.custoKm;
    tpvSchemaModel.velocidadeMedia = tpvModel.velocidadeMedia;
    tpvSchemaModel.emissoesCO2 = tpvModel.emissoesCO2;

    return tpvSchemaModel;

}


/**
 * Converts file's object to DTO
 * | EnergySource | descrição |
 * | 23 | Gasoleo |
 * | 20  | GPL |
 * | 50 | Hidrogénio |
 * | 75	| Eletrico |
 * | 1	| Gasolina |
 */
function fileObj2dto(obj) {

    const tipo = obj.$.EnergySource;

    switch (tipo) {
        case '23':
            tipoC = 'Gasoleo';
            break;
        case '20':
            tipoC = 'GPL';
            break;
        case '50':
            tipoC = 'Hidrogenio';
            break;
        case '75':
            tipoC = 'Eletrico';
            break;
        case '1':
            tipoC = 'Gasolina';
            break;
        default:
            tipoC = 'erro';
            break;
    }

    var tpvDTO = new TipoViaturaDTO(
        codigo = obj.$.Name,
        nome = obj.$.Name,
        tipoCombustivel = tipoC,
        autonomia = obj.$.Autonomy,
        consumoMedio = obj.$.Consumption,
        custoKm = obj.$.Cost,
        velocidadeMedia = obj.$.AverageSpeed,
        emissoesCO2 = obj.$.Emissions
    );
    return tpvDTO;
};



/**
 * Converts file's objects to DTO array
 * @param {Array} objArray
 */
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

/**
 * Converts model to data array
 * @param {Array} dtoArray 
 */
function modelArray2Data(modelArray, tpvSchemaModel) {
    let dataList = [];
    for (let item of modelArray) {
        dataList.push(domain2data(item, tpvSchemaModel));
    }
    //console.log(dataList);
    return dataList;
}

module.exports = {
    json2dto,
    dto2model,
    domain2data,
    fileObj2dto,
    objectArray2dto,
    dtoArray2Model,
    modelArray2Data,

};