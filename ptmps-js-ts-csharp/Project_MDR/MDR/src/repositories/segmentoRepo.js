var segmentoMapper = require('../mappers/segmentoMapper');
var noRepo = require('../repositories/noRepo');
var SegmentoSchema = require('../infrastructure/mongoDB/schemas/segmentoMongoDB');
var mongoose = require('mongoose');


async function save(segmentoModel, res) {

    var noOrigem = await noRepo.getNo(segmentoModel.noOrigem);
    var noDestino = await noRepo.getNo(segmentoModel.noDestino);

    // Obter Schema atrav�s do Mapper
    var segmentoModel2 = mongoose.model('Segmento', SegmentoSchema.index({ noOrigem: 1, noDestino: 1, _id: 1 }, { unique: true }));
    var segmentoSchemaModel2 = await segmentoMapper.domain2data(segmentoModel, segmentoModel2(), noOrigem, noDestino);
    // Pedir ao mongoose para persistir na BD
    await segmentoSchemaModel2.save(function (err) {
        if (err) {
            res.status(400);
            return res.end(err.message);
        } else {
            //res.json({ message: 'Segmento que inicia em ' +/* segmentoSchema.noOrigem*/ +' e termina em ' +/* segmentoSchema.noDestino*/ + ' criado.' });
            //res.status(200);
            //return res.send('Segmento ' + segmentoSchemaModel2.noOrigem + ' criado.');
        }
            

    });
    return segmentoSchemaModel2;
}

async function saveFromFile(segmentoModel) {
    try {
        var noOrigem = await noRepo.getNo(segmentoModel.noOrigem);
        var noDestino = await noRepo.getNo(segmentoModel.noDestino);

        // Obter Schema atrav�s do Mapper
        var segmentoModel2 = mongoose.model('Segmento', SegmentoSchema.index({ noOrigem: 1, noDestino: 1, _id: 1 }, { unique: true }));
        var segmentoSchemaModel2 = await segmentoMapper.domain2data(segmentoModel, segmentoModel2(), noOrigem, noDestino);
        // Pedir ao mongoose para persistir na BD
        await segmentoSchemaModel2.save();
        return segmentoSchemaModel2;
    } catch (e) {
        throw e;
    }
}


module.exports = { save, saveFromFile };
