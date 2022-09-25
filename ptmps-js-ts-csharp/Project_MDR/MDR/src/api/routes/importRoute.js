var express = require('express');
var router = express.Router();
const PercursoDTO = require('../../dto/percursoDTO');
const SegmentoDTO = require('../../dto/segmentoDTO');
const { importXml } = require('../../utils/xml2js');
const TipoTripulanteController = require('../../controllers/TipoTripulanteController');
const TipoViaturaController = require('../../controllers/TipoViaturaController');
const NoController = require('../../controllers/NoController');
const LinhaController = require('../../controllers/LinhaController');
const PercursoController = require('../../controllers/PercursoController');
const schema = require('../validations/criarTipoTripulante');
const formidable = require('formidable');

/* POST importar dados de ficheiro */
router.post('/importarDados', async function (req, res, next) {

    var form = new formidable.IncomingForm();
    form.parse(req);
    form.on('fileBegin', function (name, file) {
        file.path = __dirname + '/uploadedFiles/' + file.name;
    });
    form.on('file', function (name, file) {
        var filename = file.path;
        console.log(filename);
        importXml(filename, async (resultado) => {

            try {

                await importarTipoTripulante(resultado);
                await importarTipoViatura(resultado);
                await importarNos(resultado);
                await importarLinhas(resultado);
                await importarPercursos(resultado);

                res.json({ message: 'sucesso!' });

            } catch (e) {
                res.status(400).json({ error: e.message });
            }

        });
    })

});

async function importarTipoTripulante(dados) {
    driverTypes = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.DriverTypes.DriverType;

    const { error, value } = schema.schemaArray.validate(driverTypes);
    const valid = error == null;

    if (valid) {

        try {
            await TipoTripulanteController.criarTiposTripulanteFromFile(driverTypes);
        } catch (e) {
            throw e;
        }

        
    } else {
        const { details } = error;
        const message = details.map(i => i.message).join(',');
        const objectKey = details.map((err) => err.path[0]);

        console.log(`Error: ${value[objectKey].$.key} -> `, message);
        throw { error: `${value[objectKey].$.key} -> ` + message };
    }
};

async function importarTipoViatura(dados) {

    vehicleType = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork
        .Network.VehicleTypes.VehicleType;

    try {
        await TipoViaturaController.criarTiposViaturaFromFile(vehicleType);
    } catch (e) {
        throw e;
    }
};

async function importarNos(dados) {

    no = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork
        .Network.Nodes.Node;

    try {
        await NoController.criarNosFromFile(no);
    } catch (e) {
        throw e;
    }
};

async function importarLinhas(dados) {

    try {

        var Line = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[0];
        var i = 0, j = 0, k = 0, res = '', pv = [], pd = [];
        var vehicles = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedVehicles[j];
        var drivers = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedDrivers[k];
        do {
            if (dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedVehicles[0] == undefined) {
                pv = undefined;
            } else {
                do {
                    pv.put(vehicles.$.key);
                    j++
                    vehicles = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedVehicles[j];
                } while (vehicles != undefined);
            }
            if (dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedDrivers[0] == undefined) {
                pd = undefined
            } else {
                do {
                    pd.put(drivers.$.key);
                    k++
                    drivers = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i].AllowedDrivers[k];
                } while (drivers != undefined);
            }
            await LinhaController.criarLinhaWithData(Line.$.key, Line.$.Name, pv, pd, Line.$.Color);
            i++;
            j = 0;
            k = 0;
            Line = dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line[i];

        } while (Line != undefined);

    } catch (e) {
        throw e;
    }
    


};

async function importarPercursos(dados) {

    try {

        var percursos = [];

        let nosMap = new Map();

        for (const no of dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Nodes.Node) {
            nosMap.set(no.$.key, no.$.ShortName);
        }

        for (const perc of dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Paths.Path) {

            percursoDTO = new PercursoDTO(idLinha = null,
                idPercurso = null,
                ida = false,
                segmentos = []);
            var pId = perc.$.key.split(":")[1];
            percursoDTO.idPercurso = pId;
            var i = 0;
            //console.log(perc.PathNodes.PathNode);
            for (const node of perc.PathNodes.PathNode) {
                for (const n of dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Paths.Path) {

                }
                if (i < perc.PathNodes.PathNode.length - 1) {
                    percursoDTO.segmentos[i] = new SegmentoDTO();
                    percursoDTO.segmentos[i].ordem = i + 1;
                    percursoDTO.segmentos[i].noOrigem = nosMap.get(node.$.Node);
                }
                //console.log(node.$.Duration);
                if (node.$.Duration != null) {

                    percursoDTO.segmentos[i - 1].noDestino = nosMap.get(node.$.Node);
                    percursoDTO.segmentos[i - 1].duracao = node.$.Duration;
                    percursoDTO.segmentos[i - 1].distancia = node.$.Distance;
                }
                else {
                    percursoDTO.segmentos[i].ordem = i + 1;
                    percursoDTO.segmentos[i].noOrigem = nosMap.get(node.$.Node);
                }
                i++;
            }
            //console.log(percursoDTO.idLinha);
            for (const lin of dados.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.Lines.Line) {
                for (const linP of lin.LinePaths.LinePath) {
                    if (linP.$.Path == perc.$.key) {
                        //console.log(linP.$.Path + ' = ' + perc.$.key);
                        percursoDTO.idLinha = lin.$.key;
                        if (linP.$.Orientation == "Go") {
                            percursoDTO.ida = true;
                        }
                        else if ((linP.$.Orientation == "Return")) {
                            percursoDTO.ida = false;
                        }
                    }
                }
            }

            if (/*percursoDTO.idLinha != null &&*/ percursoDTO.idPercurso != null && percursoDTO.segmentos != []) {
                //console.log(percursoDTO);
                percursos.push(percursoDTO);
            }
        }
        await PercursoController.importarPercursos(percursos);

    } catch (e) {
        throw e;
    }

};


module.exports = router;
