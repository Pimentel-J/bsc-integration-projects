const utils = require('../utils/utils');
const LinhaDTO = require('../dto/linhaDTO');
var linhaMapper = require('../mappers/linhaMapper')
var LinhaService = require('../services/LinhaService');

/**
 * vai criar uma linha ou alterala consoante o campo funcao
 * @param {*} body 
 * @param {*} res 
 */
function criarLinha(codigo, body, res, funcao) {
    if (body == undefined) {
        res.status(400).json({ message: 'Please send a JSON body in the request!' });
    } else {/* istanbul ignore next */
        if (body.permissaoViatura == ' ') {
            body.permissaoViatura = [];
        } else {
            body.permissaoViatura = utils.line2Array(body.permissaoViatura);
        }/* istanbul ignore next */
        if (body.permissaoMotorista == ' ') {
            body.permissaoMotorista = [];
        } else {
            body.permissaoMotorista = utils.line2Array(body.permissaoMotorista);
        }
        var linhaDTO = linhaMapper.data2dto(body.codigo, body.nome, body.permissaoViatura, body.permissaoMotorista, body.cor);
        LinhaService.criarLinha(codigo, linhaDTO, res, funcao)
    }
}

/** criaçao das linhas atraves do import */
async function criarLinhaWithData(cod, nome, pv, pd, cor) {

    try {
        var linhaDTO = new LinhaDTO(cod, nome, [], [], cor);
        // Pede a Serviço para criarLinhas e envia o DTO
        await LinhaService.criarLinhaFile(linhaDTO, pv, pd);
    } catch (e) {
        throw e;
    }

}

/*
 * listar linhas com o codigo X
 * @param {*} query 
 * @param {*} res 
 
function listarLinhasWithCod(query, res) {
    LinhaService.listarLinhasWithCod(query, res);
}*/

/**obter linha com o codigo x */
function obterLinha(codigo, res) {
    LinhaService.obterLinha(codigo, res);
}

/**
 * elemina a linha x
 * @param {*} codigo 
 * @param {*} res 
 */
function removerLinha(codigo, res) {
    LinhaService.removerLinha(codigo, res);
}

/**
 * devolve todas as linhas
 * @param {*} res 
 */
function listarTodosLinhas(res) {

    LinhaService.listarTodosLinhas(res);

}

/**
 * Metodo usado para retornar as permissoes de viaturas ou motoristas de um dado veiculo
 * @param {*} codigo 
 * @param {*} res 
 */
function obterPermissoes(permissao, codigo, res) {
        LinhaService.obterPermissoes(permissao,codigo, res);
}

module.exports = { criarLinha, listarTodosLinhas, obterLinha, obterPermissoes, removerLinha, criarLinhaWithData };