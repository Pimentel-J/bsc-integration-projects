var mongoose = require('mongoose');
var Joi = require('joi');
var Schema = mongoose.Schema;

/**
 * Mongoose TipoTripulante schema constructor
 */
var TipoTripulanteSchema = new Schema({
    // Todos devem ser obrigatórios e o código deve ser único
    codigo: {
        type: String,
        required: true,
        index: true,
        unique: true,
        maxlength: 20
    },
    descricao: {
        type: String,
        required: true,
        maxlength: 250
    },
});

module.exports = TipoTripulanteSchema;