var percursoMapper = require('../mappers/percursoMapper');
var segmentoMapper = require('../mappers/segmentoMapper')
var PercursoRepo = require('../repositories/percursoRepo');
var SegmentoRepo = require('../repositories/segmentoRepo');
var linhaRepo = require('../repositories/linhaRepo');
var linhaMapper = require('../mappers/linhaMapper');
const LinhaDTO = require('../dto/linhaDTO');
async function definirPercurso(percursoDTO, res) {
  
    try {
        var arraySegmentos = await populaArray(percursoDTO.segmentos, res);
    

        // Cria modelo Percurso
        var percursoModel = await percursoMapper.dto2model(percursoDTO);
    
        // Pede ao Repositorio para criar o Segmento
        var percursoSchemaModel = await PercursoRepo.save(percursoModel, arraySegmentos, res);
    } catch (error) {
        res.status(400).json({ message: error });
    }
}

async function importarPercursos(percursos) {

    try {
        for (const p of percursos) {
            
            var arraySegmentos = await populaArray(p.segmentos);

            // Cria modelo Percurso
            var percursoModel = await percursoMapper.dto2model(p);

            // Pede ao Repositorio para criar o Segmento
            await PercursoRepo.saveFromFile(percursoModel, arraySegmentos);

        }
        console.log('Percursos e segmentos criados com sucesso.');
    } catch (e) {
        throw e;
    }
}

function obterPercursoByLine(idLinha, res) {
    PercursoRepo.obterPercursoByLine(idLinha, res);

}

async function populaArray(segmentos) {
    var arraySegmentos = segmentos;
    var arraySegmentosSchemaModel = [];
    for (segmento of arraySegmentos) {
            await eachSegmento(segmento, arraySegmentosSchemaModel);
        
    }
    return arraySegmentosSchemaModel;
}

async function eachSegmento(segmento, arraySegmentosSchemaModel) {
    var segmentoModel = segmentoMapper.dto2model(segmento);
    //console.log(segmentoModel);
    try {
        if (validarSegmentos(segmentoModel, arraySegmentosSchemaModel) == false) {
            throw "Os segmentos devem ser sequenciais";
        }
        var segMod = await SegmentoRepo.saveFromFile(segmentoModel);
        await arraySegmentosSchemaModel.push(segMod);
    } catch (err) {
        console.log(err);
        throw err;
    }
    

}

function validarSegmentos(segmento, array) {
    for (seg of array) {/* istanbul ignore next */
        if (!segmento.validarSegmento(seg))
            return false;
    }
    return true;
}

async function listarPercursos(query, res) {

    PercursoRepo.listarPercursos(query, res);

}

async function listarPercursosIda(query, res) {

    PercursoRepo.listarPercursosIda(query, res);

}

async function listarTodosPercursos(res) {

    PercursoRepo.listarTodosPercursos(res);
    
}

function obterPercurso(idPercurso, res) {

    PercursoRepo.obterPercurso(idPercurso, res);

}



module.exports = { obterPercursoByLine, definirPercurso , listarPercursos, listarPercursosIda , listarTodosPercursos, obterPercurso, importarPercursos };

    
    