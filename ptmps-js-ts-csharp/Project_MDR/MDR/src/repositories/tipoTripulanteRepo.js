const TipoTripulanteMapper = require('../mappers/tipoTripulanteMapper');
const TipoTripulanteSchema = require('../infrastructure/mongoDB/schemas/tipoTripulanteMongoDB');
const mongoose = require('mongoose');

const ttSchemaModel = mongoose.model('TipoTripulante', TipoTripulanteSchema);

/**
 * Persiste na BD o tipo de tripulante
 * @param {object} tipoTripulanteModel 
 * @param {object} res 
 */
function save(tipoTripulanteModel, res) {
    // Obter Schema através do Mapper
    const ttSchemaModeltoSave = TipoTripulanteMapper.domain2data(tipoTripulanteModel, ttSchemaModel());

    // Pedido ao mongoose para persistir na BD
    ttSchemaModeltoSave.save(function (err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'Tipo de tripulante ' + ttSchemaModeltoSave.codigo + ' criado.' });
        }
    });
}

/**
 * Listar Tipos de Tripulante
 * @param {object} res 
 */
function listarTodosTiposTripulante(res) {

    ttSchemaModel.find(function (err, tiposTripulantes) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(tiposTripulantes);
        }
    });
}

/**
 * Obter um Tipo de Tripulante
 * @param {string} cod 
 * @param {object} res 
 */
function obterTipoTripulante(cod, res) {

    ttSchemaModel.findOne({ codigo: cod }, function (err, tipoTripulante) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(tipoTripulante);
        }
    });
}

/**
 * Persiste na BD Tipos de Tripulante importados do ficheiro
 * @param {object} tiposTripulanteModel 
 * @param {object} res 
 */
async function saveMany(tiposTripulanteModel) {

    try {
        for (tipo of tiposTripulanteModel) {
            let doc = new ttSchemaModel(tipo);
            await doc.save();
        }
    } catch (e) {
        throw e;
    }


    console.log('Tipos de tripulante criados com sucesso.');

}

/**
 * Persiste na BD as alterações ao Tipo de Tripulante
 * @param {string} code 
 * @param {object} tipoTripulanteModel 
 * @param {object} res 
 */
function alterarTipoTripulante(code, tipoTripulanteModel, res) {
    const ttSchemaModeltoChange = TipoTripulanteMapper.domain2data(tipoTripulanteModel, ttSchemaModel);

    ttSchemaModel.findOne({ codigo: code }, function (err, tipoTripulante) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            tipoTripulante.codigo = ttSchemaModeltoChange.codigo;
            tipoTripulante.descricao = ttSchemaModeltoChange.descricao;

            tipoTripulante.save(function (err2) {
                if (err2) {
                    //res.send(err);
                    allErrors = err2.message.split(':');
                    res.status(400).json({ message: allErrors });
                } else {
                    res.json({ message: 'Tipo de tripulante ' + tipoTripulante.codigo + ' atualizado com sucesso.' });
                }
            });
        }
    });
}

/**
 * GET alternativo para utilizar o find sem res
 * @param {string} cod 
 * @param {object} res 
 */
function get(cod, res) {
    return ttSchemaModel.findOne({ codigo: cod });
}

/**
 * Obter um Tipo de Tripulante
 * @param {string} cod 
 * @param {object} res 
 */
function apagarTipoTripulante(cod, res) {

    ttSchemaModel.deleteOne({ codigo: cod }, function (err, tipoTripulante) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'Tipo de Tripulante ' + cod + ' removido.' });
        }
    });
}

module.exports = {
    save,
    saveMany,
    listarTodosTiposTripulante,
    obterTipoTripulante,
    alterarTipoTripulante,
    apagarTipoTripulante,
    get
};