const xml2js = require('xml2js');
const fs = require('fs');
const util = require('util');
const TipoTripulante = require('../models/tipoTripulante');

/**
 * Lê de forma assíncrona todo o conteúdo de um ficheiro
 * @param {String} filename 
 * @param 
 */
function importXml(filename, callback) {
    fs.readFile(filename, function (err, data) {
        if (err) throw new Error(err);
        try {
            xml2js.parseString(data, { explicitArray: false }, (err, result) => {

                return callback(result);
            });
        } catch (err) {
            console.error("parseXml failed: ", err);
        }
    });
};

// PARA TESTAR COM > node ./src/utils/xml2js
/*
const filename = 'demo-lapr5.glx';
const TipoTripulanteMapper = require('../mappers/tipoTripulanteMapper');

importXml(filename, (resultado) => {
    console.log("\n### Get DriverTypes array of objects ###");
    driverTypes = resultado.GlDocumentInfo.world.GlDocument.GlDocumentNetwork.Network.DriverTypes.DriverType;

    // console.log(driverTypes.length);

    objsDTO = TipoTripulanteMapper.objectArray2dto(driverTypes);
    console.log(objsDTO);

    objsModel = TipoTripulanteMapper.dtoArray2Model(objsDTO);
    console.log(objsModel);

    // console.log("### Get All - JSON ###\n", JSON.stringify(resultado));
    // console.log("### Get All - JSON with indentation ###\n", JSON.stringify(resultado, null, 2));
    // console.log("\n### Example of util.inspect ###\n", util.inspect(resultado, false, 7, true));

});
*/
module.exports = { importXml };
