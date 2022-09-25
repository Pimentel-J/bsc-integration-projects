var mongoose = require('mongoose');
var schema = mongoose.Schema;
var mongoose_validator = require('mongoose-id-validator');

var TipoViaturaSchema = new schema({
    codigo: {
        type: String,
        unique: true,
        index: true,
        maxlength: 20,
        required: [true, 'Codigo é obrigatório'],
        validate: [codigoValidator, 'Codigo deve ser alfanumerico']
    },

    nome: {
        type: String,
        maxlength: 250,
        required: true
    },
    tipoCombustivel: {
        type: String,
        enum: ['Gasoleo', 'Gasolina', 'Eletrico', 'Hidrogenio', 'GPL'],
        required: [true, 'Utilize uma das seguintes opções: Diesel, Gasolina, Eletrico, Gas , GPL']
    },
    autonomia: { type: Number, min: 0, required: true },
    consumoMedio: { type: Number, min: 0, required: true, set: setConsMedio }, //
    custoKm: { type: Number, min: 0, required: true },
    velocidadeMedia: { type: Number, min: 0, required: true },
    emissoesCO2: { type: Number, min: 0, required: true }
});

function setConsMedio(num) {
    return Number(num).toFixed(3);
}

function codigoValidator(v) {
    var regExpAlfanum = /^[A-zA-z0-9\_]{1,20}$/; //alfanum�rico (permite underscore) ate 20 caracteres
    return regExpAlfanum.test(v);
};

//TipoViaturaSchema.path('codigo').validate(function(v) { return v.lenght <= 20;}, 'Tamanho 20 carateres excedido')
TipoViaturaSchema.plugin(mongoose_validator);

//module.exports = mongoose.model('TipoViatura',TipoViaturaSchema);
module.exports = TipoViaturaSchema;