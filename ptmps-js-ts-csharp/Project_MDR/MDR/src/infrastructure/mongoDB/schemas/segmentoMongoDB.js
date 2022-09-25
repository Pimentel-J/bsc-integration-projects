var mongoose     = require('mongoose');
const NoSchema = require('../schemas/noMongoDB');
var Schema = mongoose.Schema;


var SegmentoSchema = new Schema({
    // Todos devem ser obrigat�rios
    ordem: {
        type: Number,
        required: true
    },
    noOrigem: { type: NoSchema, excludeIndexes: true, required: true },
    noDestino: { type: NoSchema, excludeIndexes: true, required: true },
    duracao: {type: Number, min: 0, required: true},
    distancia: { type: Number, min: 0, required: true }
});

//module.exports = mongoose.model('Segmento', SegmentoSchema);
module.exports = SegmentoSchema;