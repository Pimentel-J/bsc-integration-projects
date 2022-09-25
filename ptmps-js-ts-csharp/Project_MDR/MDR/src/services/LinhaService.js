var linhaMapper = require('../mappers/linhaMapper');
var LinhaRepo = require('../repositories/linhaRepo');
var tipoviaturaRepo = require('../repositories/tipoviaturaRepo');
var tipoTripulanteMapper = require('../mappers/tipoTripulanteMapper')
var tipoviaturaMapper = require('../mappers/tipoviaturaMapper');
var tipoTripulanteRepo = require('../repositories/tipoTripulanteRepo');
const VIATURAS = 1;
const MOTORISTAS = 2;
const FUNCAO_CRIAR = 1;
const FUNCAO_ALTERAR = 2;

/**
 * vai criar uma linha de base
 * @param {*} linhaDTO 
 * @param {*} res 
 */
async function criarLinha(codigo, linhaDTO, res, funcao) {
    var arraySegmentosMoto = undefined, arraySegmentosViat = undefined;
    // Cria modelo Linha
    var linhaModel = linhaMapper.dto2model(linhaDTO);
    var pm = '' + linhaDTO.permissoesTipoMotorista;
    var pv = '' + linhaDTO.permissoesTipoViatura;
    pm = pm.trim();
    pv = pv.trim();
    if (pm != '')
        var arraySegmentosMoto = await populaArray(MOTORISTAS, linhaDTO.permissoesTipoMotorista, res);
    if (pv != '')
        var arraySegmentosViat = await populaArray(VIATURAS, linhaDTO.permissoesTipoViatura, res);

    var existe;
    existe = await LinhaRepo.getLinha(linhaModel.codigo, res);

    if (funcao == FUNCAO_CRIAR) {
        if (existe == null || existe == undefined) {
            if (pv != '' && arraySegmentosViat == null) {
                res.json({ message: 'Linha não criada porque um ou mais tipos de viaturas nao existem ' });
            } else {/* istanbul ignore next */
                if (pm != '' && arraySegmentosMoto == null) {/* istanbul ignore next */
                    res.json({ message: 'Linha não criada porque um ou mais tipos de motoristas não existem ' });
                } else {
                    LinhaRepo.save(linhaModel, arraySegmentosViat, arraySegmentosMoto, res);
                }
            }
        } else {
            res.json({ message: 'Linha não criada porque o codigo ja foi usado ' });
        }
    } else {
        if (existe == null || existe == undefined || linhaModel.codigo == codigo) {
            if (pv != '' && arraySegmentosViat == null) {
                res.json({ message: 'Linha não alterada porque um ou mais tipos de viaturas nao existem ' });
            } else {/* istanbul ignore next */
                if (pm != '' && arraySegmentosMoto == null) {/* istanbul ignore next */
                    res.json({ message: 'Linha não alterada porque um ou mais tipos de motoristas não existem ' });
                } else {
                    LinhaRepo.alterarLinha(codigo, linhaModel, arraySegmentosViat, arraySegmentosMoto, res);
                }
            }
        } else {
            res.json({ message: 'Linha não alterada porque o codigo ja foi usado ' });
        }
    }
}

/**
 * criar linhas a partir do ficheiro
 * @param {*} linhaDTO 
 * @param {*} pv 
 * @param {*} pd 
 */
async function criarLinhaFile(linhaDTO, pv, pd) {

    try {

        var arraySegmentosMoto = undefined, arraySegmentosViat = undefined;
        if (pd != undefined)
            arraySegmentosMoto = await populaArray(MOTORISTAS, pd);
        if (pv != undefined)
            arraySegmentosViat = await populaArray(VIATURAS, pv);
        // Cria modelo Linha
        var linhaModel = linhaMapper.dto2model(linhaDTO);
        var existe;
        // Pede ao Repositorio para criar o Linha
        existe = await LinhaRepo.getLinha(linhaModel.codigo);
        if (existe == null || existe == undefined) {
            await LinhaRepo.saveFile(linhaModel, arraySegmentosViat, arraySegmentosMoto);
        }

    } catch (e) {/* istanbul ignore next */
        throw e;
    }

}

/**
 * popular o array das permissoes de viaturas ou motoristas
 * @param {*} tipo 
 * @param {*} array 
 */

async function populaArray(tipo, array) {
    var newArray = array;
    var arraySchemaModel = [];
    if (tipo == MOTORISTAS) {
        for (iter of newArray) {
            try {
                iter = iter.trim();
                objeto = await tipoTripulanteRepo.get(iter);
                if (objeto == null || objeto == undefined) {
                    return null;
                }
                await eachMotorista(objeto, arraySchemaModel);
            } catch (err) {/* istanbul ignore next */
                console.log(err.message);/* istanbul ignore next */
                break;
            }
        }
    } else {
        for (iter of newArray) {
            try {
                iter = iter.trim();
                objeto = await tipoviaturaRepo.get(iter);
                if (objeto == null || objeto == undefined) {
                    return null;
                }
                await eachViatura(objeto, arraySchemaModel);
            } catch (err) {/* istanbul ignore next */
                console.log(err.message);/* istanbul ignore next */
                break;
            }
        }
    }
    return arraySchemaModel;
}

async function eachMotorista(motorista, arrayMotoristasSchemaModel) {
    var MotoristaModel = tipoTripulanteMapper.dto2model(motorista);
    await arrayMotoristasSchemaModel.push(MotoristaModel);
}


async function eachViatura(viatura, arrayViaturasSchemaModel) {
    var ViaturaModel = tipoviaturaMapper.dto2model(viatura);
    await arrayViaturasSchemaModel.push(ViaturaModel);
}

/*function listarLinhasWithCod(query, res) {

    LinhaRepo.listarLinhasWithCod(query, res);

}*/

/**
 * lista todas as linhas
 * @param {*} res 
 */
function listarTodosLinhas(res) {
    LinhaRepo.listarTodosLinhas(res);
}

/**
 * obtem linha atraves do codigo
 * @param {*} codigo 
 * @param {*} res 
 */
function obterLinha(codigo, res) {
    LinhaRepo.obterLinha(codigo, res);
}

/**
 * remove linha atraves do codigo
 */
function removerLinha(codigo, res) {
    LinhaRepo.removerLinha(codigo, res);
}

/**
 * obtem as permissoes de motoristas ou viaturas de uma dada linha
 * @param {*} permissao 
 * @param {*} codigo 
 * @param {*} res 
 */
async function obterPermissoes(permissao, codigo, res) {
    var linha = await LinhaRepo.getLinha(codigo);
    var model = linhaMapper.json2dto(linha);
    if (permissao == VIATURAS) {
        res.json(model.permissoesTipoViatura);
    } else {
        res.json(model.permissoesTipoMotorista);
    }
}


module.exports = { eachViatura, eachMotorista, populaArray, criarLinha, obterPermissoes, obterLinha, removerLinha, listarTodosLinhas, listarTodosLinhas, criarLinhaFile };
