var tpvMapper = require('../mappers/tipoviaturaMapper');
var TipoViaturaSchema = require('../infrastructure/mongoDB/schemas/tipoviaturaMongoDB');
var mongoose = require('mongoose');
//const TipoViatura = require('../models/tipoViatura');

const tpsvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);

function save(tpvModel, res) {

    // Obter Schema atraves do Mapper
    var tpvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);
    var tpvSchemaModel2 = tpvMapper.domain2data(tpvModel, tpvSchemaModel());

    //pedir ao mongoose para persistir na BD
    tpvSchemaModel2.save(function(err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            console.log(allErrors);
            res.status(400).json({ message: allErrors });
        } else

            res.json({
            message: 'Tipo Viatura ' + tpvSchemaModel2.codigo +
                ' criado '
        });
    });

}

function listarTodosTipos(res) {

    var tpvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema)
    tpvSchemaModel.find(function(err, viaturas) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(viaturas);
        }
    });
}

function get(cod, res) {
    var TipoViaturaSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);
    return TipoViaturaSchemaModel.findOne({ codigo: cod });
}

function getTipoViatura(cod, res) {
    var tpvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);
    tpvSchemaModel.findOne({ codigo: cod }, function(err, tpv) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(tpv);
        }
    });
}

function altTipoViatura(cod, tpvModel, res) {
    var tpvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);
    var tpvActualModel = tpvMapper.domain2data(tpvModel, tpvSchemaModel);

    tpvSchemaModel.findOne({ codigo: cod }, function(err, tpv) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            // Realizar alteracoes

            tpv.codigo = tpvActualModel.codigo;
            tpv.nome = tpvActualModel.nome;
            tpv.tipoCombustivel = tpvActualModel.tipoCombustivel;
            tpv.autonomia = tpvActualModel.autonomia;
            tpv.consumoMedio = tpvActualModel.consumoMedio;
            tpv.custoKm = tpvActualModel.custoKm;
            tpv.velocidadeMedia = tpvActualModel.velocidadeMedia;
            tpv.emissoesCO2 = tpvActualModel.emissoesCO2;

            // Pedir ao mongoose para persistir na BD
            tpv.save(function(err2) {
                if (err2) {
                    //res.send(err);
                    allErrors = err2.message.split(':');
                    res.status(400).json({ message: allErrors });
                } else {
                    res.json({ message: 'Tipo Viatura ' + tpv.codigo + ' atualizado.' });
                }

            });

        }
    });
}

function delTipoViatura(cod, res) {
    var tpvSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);
    tpvSchemaModel.deleteOne({ codigo: cod }, function(err, tpv) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'Tipo Viatura ' + tpv.codigo + ' removido.' });
        }
    });
}


async function saveMany(tpsvModel) {

    try {
        for (tipo of tpsvModel) {
            var doc = new tpsvSchemaModel(tipo);
            await doc.save();
        }
    } catch (e) {
        throw e;
    }


    console.log('Tipos de viatura criados com sucesso.');

}

module.exports = {
    save,
    saveMany,
    get,
    listarTodosTipos,
    getTipoViatura,
    altTipoViatura,
    delTipoViatura
};