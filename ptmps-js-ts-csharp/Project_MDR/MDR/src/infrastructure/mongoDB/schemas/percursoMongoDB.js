var mongoose = require('mongoose');
var mongoose_validator = require('mongoose-id-validator');
const SegmentoSchema = require('../schemas/segmentoMongoDB');
const LinhaSchema = require('../schemas/linhaMongoDB');
var Schema = mongoose.Schema;

var PercursoSchema = new Schema({
    // Todos devem ser obrigat�rios
    idLinha: { type: LinhaSchema, excludeIndexes: true/*, required: true*/ },
    idPercurso: {
        type: String,
        unique: true,
        validate: [idPercursoValidator, 'abreviatura deve ser alfanumerica (incluindo _) ate 20 caracteres']
    },
    ida:
    {
        type: Boolean, required: true
    },
    segmentos: { type: [SegmentoSchema], excludeIndexes: true }
});

function idPercursoValidator(v) {
    var regExpAlfanum = /^[A-zA-z0-9\_:]{1,20}$/;  //alfanum�rico (permite underscore) ate 20 caracteres
    return regExpAlfanum.test(v);
};

PercursoSchema.plugin(mongoose_validator);
//module.exports = mongoose.model('Percurso', PercursoSchema);
module.exports = PercursoSchema;
