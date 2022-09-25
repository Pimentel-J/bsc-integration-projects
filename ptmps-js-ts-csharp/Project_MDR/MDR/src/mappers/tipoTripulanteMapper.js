const TipoTripulanteDTO = require('../dto/tipoTripulanteDTO');
const TipoTripulanteModel = require('../models/tipoTripulante');

/**
 * Converts json to DTO
 * @param {object} json 
 * @returns tipoTripulanteDTO
 */
function json2dto(json) {
    var tipoTripulanteDTO = new TipoTripulanteDTO(
        codigo = json.codigo,
        descricao = json.descricao,
    );

    return tipoTripulanteDTO;
};

/**
 * Converts dto to model
 * @param {object}   dto 
 * @returns tipoTripulanteModel
 */
function dto2model(dto) {
    var tipoTripulanteModel = new TipoTripulanteModel(dto.codigo, dto.descricao);

    return tipoTripulanteModel;
}

/**
 * Converts domain to data
 * @param {object} tipoTripulanteModel 
 * @param {object} tipoTripulanteSchemaModel 
 * @returns tipoTripulanteSchema
 */
function domain2data(tipoTripulanteModel, tipoTripulanteSchemaModel) {
    // var tipoTripulanteSchema = TipoTripulanteSchema();
    tipoTripulanteSchemaModel.codigo = tipoTripulanteModel.codigo;
    tipoTripulanteSchemaModel.descricao = tipoTripulanteModel.descricao;

    return tipoTripulanteSchemaModel;
}

/**
 * Converts file's object to DTO
 * @param {Object} obj
 */
function fileObj2dto(obj) {
    var tipoTripulanteDTO = new TipoTripulanteDTO(
        codigo = obj.$.name,
        descricao = obj.$.description,
    );
    return tipoTripulanteDTO;
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
    // console.log(dtoList);
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
    // console.log(modelList);
    return modelList;
}

/**
 * Converts model to data array
 * @param {Array} dtoArray 
 */
function modelArray2Data(modelArray, tipoTripulanteSchemaModel) {
    let dataList = [];
    for (let item of modelArray) {
        dataList.push(domain2data(item, tipoTripulanteSchemaModel));
    }
    // console.log(dataList);
    return dataList;
}

module.exports = { json2dto, dto2model, domain2data, fileObj2dto, objectArray2dto, dtoArray2Model, modelArray2Data };