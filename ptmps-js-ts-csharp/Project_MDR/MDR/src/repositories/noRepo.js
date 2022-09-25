var noMapper = require('../mappers/noMapper');
var NoSchema = require('../infrastructure/mongoDB/schemas/noMongoDB');
var mongoose = require('mongoose');
const noSchemaModel = mongoose.model('No', NoSchema);

function save(noModel, res) {

    // Obter Schema atrav�s do Mapper
    var noSchemaModel2 = noMapper.domain2data(noModel, noSchemaModel());

    // Pedir ao mongoose para persistir na BD
    noSchemaModel2.save(function (err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            console.log(allErrors);
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'No ' + noSchemaModel2.abreviatura + ' criado.' });
        }
        
    });
}

function listarNos(qry, res) {


    if (qry.abreviatura == null) {
        var regexp = new RegExp('^' + qry.nome);
        var exp = { nome: regexp };
        var exp_sort = (qry.sort == 'desc') ? { nome: 'desc' } : { nome: 'asc' };

    }
    else {
        var regexp = new RegExp('^' + qry.abreviatura);
        var exp = { abreviatura: regexp };
        var exp_sort = (qry.sort == 'desc') ? { abreviatura: 'desc' } : { abreviatura: 'asc' };

    }

    var query = noSchemaModel.find(exp);
    query.sort(exp_sort);

    query.exec(function (err, nos) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(nos);

            console.log(nos);
        }
    });

}

function listarTodosNos(res) {

    noSchemaModel.find( function (err, nos) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(nos);
        }
    });

}

function getNo(abrev, res) {
    return noSchemaModel.findOne({ abreviatura: abrev });
}

function obterNo(abrev, res) {
    noSchemaModel.findOne({ abreviatura: abrev }, function (err, nos) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(nos);
        }
    }
    );
}

function removerNo(abrev, res) {
    noSchemaModel.deleteOne({ abreviatura: abrev }, function (err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'No '+abrev+' removido com sucesso' });
        }
    }
    );
}

function alterarNo(abrev, noModel, res) {

    var noAtualizadoModel = noMapper.domain2data(noModel, noSchemaModel());

    noSchemaModel.findOne({ abreviatura: abrev }, function (err, no) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {

            // Realizar alteracoes
            no.abreviatura = noAtualizadoModel.abreviatura;
            no.nome = noAtualizadoModel.nome;
            no.latitude = noAtualizadoModel.latitude;
            no.longitude = noAtualizadoModel.longitude;
            no.estacaoRecolha = noAtualizadoModel.estacaoRecolha;
            no.pontoRendicao = noAtualizadoModel.pontoRendicao;
            no.modeloMapa = noAtualizadoModel.modeloMapa;

            // Pedir ao mongoose para persistir na BD
            no.save(function (err2) {
                if (err2) {
                    //res.send(err);
                    allErrors = err2.message.split(':');
                    res.status(400).json({ message: allErrors });
                } else {
                    res.json({ message: 'No ' + no.abreviatura + ' atualizado.' });
                }

            });

        }
    }
    );
}

/**
 * Persiste na BD Nós importados do ficheiro
 */
async function saveMany(nosModel) {

    try {
        for (no of nosModel) {
            var doc = new noSchemaModel(no);
            await doc.save();
        }
    } catch (e) {
        throw e;
    }


    console.log('Nos criados com sucesso.');
    //res.json({ message: 'Tipos de tripulante criados com sucesso.' });

}

module.exports = { save, listarNos, getNo, listarTodosNos, obterNo, alterarNo, removerNo, saveMany};
