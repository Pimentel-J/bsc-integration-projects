var percursoMapper = require('../mappers/percursoMapper');
var segmentoRepo = require('../repositories/segmentoRepo');
var linhaRepo = require('../repositories/linhaRepo');
var PercursoSchema = require('../infrastructure/mongoDB/schemas/percursoMongoDB');
var mongoose = require('mongoose');


async function save(percursoModel, arraySegmentos, res) {

    var linha = await linhaRepo.getLinha(percursoModel.idLinha);
    // Obter Schema atrav�s do Mapper
    var percursoSchemaModel = mongoose.model('Percurso', PercursoSchema.index({ idPercurso: 1, idLinha: 1 }, { unique: true }));
    var percursoSchemaModel2 = await percursoMapper.domain2data(percursoModel, percursoSchemaModel(), linha);
    
    for (segmento of arraySegmentos) {
        percursoSchemaModel2.segmentos.push(segmento);
    }
    
    // Pedir ao mongoose para persistir na BD
    percursoSchemaModel2.save(function (err) {
        if (err) {
            res.status(400);
            return res.end(err.message);
        } else {
            var arrayResp = [];
            for (segmento of percursoSchemaModel2.segmentos) {
                arrayResp.push('[No Origem = ' + segmento.noOrigem.abreviatura + ', No Destino = ' + segmento.noDestino.abreviatura + ']');
            }
            res.json({
                message: 'Percurso ' + percursoSchemaModel2.idPercurso + ' composto pelos segmentos: ' + arrayResp + ' criado.'
            });
        }
            
        //console.log(percursoSchemaModel2.segmentos);
        
    });
    
    
}

async function saveFromFile(percursoModel, arraySegmentos) {

    try {

        var linha = await linhaRepo.getLinha(percursoModel.idLinha);
        // Obter Schema atrav�s do Mapper
        var percursoSchemaModel = mongoose.model('Percurso', PercursoSchema.index({ idPercurso: 1, idLinha: 1 }, { unique: true }));
        var percursoSchemaModel2 = await percursoMapper.domain2data(percursoModel, percursoSchemaModel(), linha);

        for (segmento of arraySegmentos) {
            percursoSchemaModel2.segmentos.push(segmento);
        }

        // Pedir ao mongoose para persistir na BD
        await percursoSchemaModel2.save();

    } catch (e) {
        throw e;
    }
    
    

}

function listarPercursos(query, res) {

    var percursoSchemaModel =mongoose.model('Percurso', PercursoSchema)
    percursoSchemaModel.find({ "idLinha.codigo" : query.linha }, function (err, percursos) {
        if (err)
            res.send(err);


        res.json(percursos);
    });
}

function listarPercursosIda(query, res) {
    if (query.ida == 'true') {
        var percursoSchemaModel = mongoose.model('Percurso', PercursoSchema)
        percursoSchemaModel.find({ "ida": true }, function (err, percursos) {
            if (err)
                res.send(err);


            res.json(percursos);
        });
    }
    else {
            var percursoSchemaModel = mongoose.model('Percurso', PercursoSchema)
            percursoSchemaModel.find({ "ida": false, "idLinha.codigo": query.linha }, function (err, percursos) {
                if (err)
                    res.send(err);


                res.json(percursos);
            });
            
    }
    
    
}

function listarTodosPercursos(res) {

    var percursoSchemaModel =mongoose.model('Percurso', PercursoSchema)
    percursoSchemaModel.find( function (err, percursos) {
        if (err)
            res.send(err);


        res.json(percursos);
    });
}

function obterPercursoByLine(idLinha, res) {
    console.log(idLinha);
    var percursoSchemaModel =mongoose.model('Percurso', PercursoSchema)
    percursoSchemaModel.find(   {"idLinha.codigo" : idLinha },function (err, percursos) {
        if (err)
            res.send(err);


        res.json(percursos);
    });
}

function obterPercurso(idPerc, res) {
    var percursoSchemaModel = mongoose.model('Percurso', PercursoSchema);
    percursoSchemaModel.findOne({ idPercurso: idPerc }, function (err, percursos) {
        if (err) {
            //res.send(err);
            allErrors = err.message.split(':');
            res.status(400).json({ message: allErrors });
        } else {
            res.json(percursos);
        }
    }
    );
}

module.exports = { obterPercursoByLine, save , saveFromFile, listarTodosPercursos, listarPercursosIda, listarPercursos, obterPercurso};
