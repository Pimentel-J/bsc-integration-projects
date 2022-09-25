var linhaMapper = require('../mappers/linhaMapper');
var LinhaSchema = require('../infrastructure/mongoDB/schemas/linhaMongoDB');
var mongoose = require('mongoose');
const linhachemaModel = mongoose.model('Linha', LinhaSchema);

/** 
 * persiste o objeto na base de dados
 */
function save(linhaModel, tipoV, tipoM, res) {
    // Obter Schema atrav�s do Mapper
    var linhaSchemaModel2 = linhaMapper.domain2data(linhaModel, linhachemaModel());
    // Pedir ao mongoose para persistir na BD
    linhaSchemaModel2.save(function (err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            console.log(allErrors);
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'Linha ' + linhaSchemaModel2.codigo + ' criada.' });
        }
    });
    if (tipoV != undefined) {
        for (permissaoViatura of tipoV) {
            linhaSchemaModel2.permissoesTipoViatura.push(permissaoViatura);
        }
    }
    if (tipoM != undefined) {
        for (permissaoMotorista of tipoM) {
            linhaSchemaModel2.permissoesTipoMotorista.push(permissaoMotorista);
        }
    }

}

/**
 * metodo utilizado no import de fiheiros, extrutura igual ao do save, mas evita o conflito do res
 * @param {*} linhaModel 
 * @param {*} tipoV 
 * @param {*} tipoM 
 */
async function saveFile(linhaModel, tipoV, tipoM) {

    try {

        // Obter Schema atrav�s do Mapper
        var linhaSchemaModel2 = linhaMapper.domain2data(linhaModel, linhachemaModel());


        // Pedir ao mongoose para persistir na BD
        linhaSchemaModel2.save();

        if (tipoV != undefined) {
            for (permissaoViatura of tipoV) {
                linhaSchemaModel2.permissoesTipoViatura.push(permissaoViatura);
            }
        }
        if (tipoM != undefined) {
            for (permissaoMotorista of tipoM) {
                linhaSchemaModel2.permissoesTipoMotorista.push(permissaoMotorista);
            }
        }

    } catch (e) {
        throw e;
    }

    console.log('Linhas criadas com sucesso.');
    
}

/*function listarLinhasWithCod(qry, res) {
    if (qry.codigo == null) {
        var regexp = new RegExp('^' + qry.nome);
        console.log(qry.nome);
        var exp = { nome: regexp };
        var exp_sort = (qry.sort == 'desc') ? { nome: 'desc' } : { nome: 'asc' };

    }
    else {
        var regexp = new RegExp('^' + qry.codigo);
        console.log(qry.codigo);
        var exp = { codigo: regexp };
        var exp_sort = (qry.sort == 'desc') ? { codigo: 'desc' } : { codigo: 'asc' };

    }

    var query = linhachemaModel.find(exp);
    query.select('codigo nome');
    // console.log(query,qry.codigo,qry.nome, qry.sort);
    query.sort(exp_sort);

    query.exec(function (err, linhas) {
        if (err) {
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(linhas);

            console.log(linhas);
        }
    });

}
*/

/**
 * lista todas as linhas
 * @param {*} res 
 */
function listarTodosLinhas(res) {
    linhachemaModel.find(function (err, linhas) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(linhas);
        }
    });

}

/**
 * devolve a linha no return nao ivocando o res
 * @param {*} idLinha 
 * @param {*} res 
 */
function getLinha(idLinha, res) {
    return linhachemaModel.findOne({ codigo: idLinha });

}

/**
 * este get linha devolve a linha no res
 * @param {*} cod 
 * @param {*} res 
 */
function obterLinha(cod, res) {
    linhachemaModel.findOne({ codigo: cod }, function (err, linhas) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(linhas);
        }
    }
    );
}

/**
 * remove a linha
 * @param {*} cod 
 * @param {*} res 
 */
function removerLinha(cod, res) {
    linhachemaModel.deleteOne({ codigo: cod }, function (err) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json({ message: 'Linha ' + cod + ' removido com sucesso' });
        }
    }
    );
}

/**
 * altera a linha
 * @param {*} cod 
 * @param {*} linhaModel 
 * @param {*} arraySegmentosViat 
 * @param {*} arraySegmentosMoto 
 * @param {*} res 
 */
function alterarLinha(cod, linhaModel, arraySegmentosViat, arraySegmentosMoto, res) {
    var linhaAtualizadoModel = linhaMapper.domain2data(linhaModel, linhachemaModel());

    linhachemaModel.findOne({ codigo: cod }, function (err, linha) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            // Realizar alteracoes
            linha.codigo = linhaAtualizadoModel.codigo;
            linha.nome = linhaAtualizadoModel.nome;
            linha.permissoesTipoViatura = arraySegmentosViat;
            linha.permissoesTipoMotorista = arraySegmentosMoto;
            // Pedir ao mongoose para persistir na BD
            linha.save(function (err2) {
                if (err2) {
                    //res.send(err);
                    allErrors = err2.message.split(':');
                    res.status(400).json({ message: allErrors });
                } else {
                    res.json({ message: 'Linha ' + linha.codigo + ' atualizada.' });
                }

            });

        }
    }
    );
}

module.exports = { alterarLinha, save, getLinha, listarTodosLinhas, obterLinha, removerLinha, saveFile };
