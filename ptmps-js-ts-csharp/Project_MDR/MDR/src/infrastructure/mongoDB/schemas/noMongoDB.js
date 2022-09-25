var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var NoSchema = new Schema({

    // Todos devem ser obrigat�rios e a abreviatura deve ser �nica
    abreviatura: {
        type: String,
        required: true,
        index: true,
        unique: true,
        validate: [abreviaturaValidator, 'abreviatura deve ser alfanumerica (incluindo _) ate 20 caracteres']
    },
    nome: {
        type: String,
        required: true,
        validate: [nomeValidator, 'nome deve ser alfanumerico (incluindo _, -,  , acentos) ate 200 caracteres']
    },
    //posteriormente considerar usar geoJSON: https://mongoosejs.com/docs/geojson.html
    latitude: {
        type: Number,
        required: true,
        min: -90,
        max: 90
    },
    longitude: {
        type: Number,
        required: true,
        min: -180,
        max: 180
    },
    estacaoRecolha: {
        type: Boolean,
        required: true
    },
    pontoRendicao: {
        type: Boolean,
        required: true
    },
    modeloMapa: {
        type: String,
        required: false
    }

});

function abreviaturaValidator(v) {
    var regExpAlfanum = /^[A-zA-z0-9\_]{1,20}$/;  //alfanum�rico (permite underscore) ate 20 caracteres
    return regExpAlfanum.test(v);
};

function nomeValidator(v) {
    var regExpAlfanum = /^[A-z0-9\-_\ ()çãáàâéèêíóôõú]{1,200}$/;  //alfanum�rico (permite underscore, -, espa�o e acentos) ate 200 caracteres
    return regExpAlfanum.test(v);
};

module.exports = NoSchema;