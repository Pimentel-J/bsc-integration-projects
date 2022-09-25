var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const NoSchema = require('../schemas/noMongoDB');
const TipoViatura = require('../schemas/tipoviaturaMongoDB');
const TipoTripulante = require('../schemas/tipoTripulanteMongoDB');
var mongoose_validator = require('mongoose-id-validator');

var LinhaSchema = new Schema({
    codigo: { 
            type : String , 
           unique : true, 
           index: true,
           required: [ true, 'Codigo é obrigatório']
    },
    nome: {
        type: String,
        required: true
    }, 
    permissoesTipoViatura: { type: [TipoViatura], excludeIndexes: true},
    permissoesTipoMotorista: { type: [TipoTripulante], excludeIndexes: true},
    noFinal: { type: [NoSchema], excludeIndexes: true },
    cor: {
        type: String,
        required: true
    }
});

LinhaSchema.plugin(mongoose_validator);
module.exports = LinhaSchema;