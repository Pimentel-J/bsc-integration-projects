const { mockResponse, mockRequest, mockNext } = require('../../tools');
const LinhaController = require('../../../src/controllers/LinhaController');
const LinhaMapper = require('../../../src/mappers/linhaMapper');
const LinhaService = require('../../../src/services/LinhaService');

jest.mock('../../../src/mappers/linhaMapper');
jest.mock('../../../src/services/LinhaService');

const mockReq = mockRequest();
const mockRes = mockResponse();
const next = mockNext();
const reqBody = { codigo: "5", nome: "teste", permissoesViaturas: ' ', permissoesMotoristas: ' ' };

describe('### LinhaController ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarLinha should call Mapper and Service', function () {
    LinhaController.criarLinha(1, mockReq.body, mockRes, 1);
    expect(LinhaService.criarLinha && LinhaMapper.data2dto).toHaveBeenCalledTimes(1);
  });

  test('criarLinha if undefined', function () {
    LinhaController.criarLinha(1, undefined, mockRes, next);
    expect(mockRes.status).toEqual(mockRes.status);
  });

  test('criarLinhaWithData error', async function () {
    jest.spyOn(LinhaService, 'criarLinhaFile').
      mockImplementation(async () => { throw new Error('error'); });
    try {
      await LinhaController.criarLinhaWithData(reqBody.codigo, reqBody.nome,
        reqBody.permissoesViaturas, reqBody.permissoesMotoristas);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarTodosLinhas should call Service', function () {
    LinhaController.listarTodosLinhas(mockRes);
    expect(LinhaService.listarTodosLinhas).toHaveBeenCalledTimes(1);
  });

  test('obterLinha should call Service', function () {
    LinhaController.obterLinha(reqBody.codigo, mockRes);
    expect(LinhaService.obterLinha).toHaveBeenCalledTimes(1);
  });

  test('removerLinha should call Service', function () {
    LinhaController.removerLinha(reqBody.codigo, mockRes);
    expect(LinhaService.removerLinha).toHaveBeenCalledTimes(1);
  });

  test('obterPermissoes should call Service', function () {
    LinhaController.obterPermissoes(reqBody.permissoesMotoristas[0], reqBody.codigo, mockRes);
    expect(LinhaService.obterPermissoes).toHaveBeenCalledTimes(1);
  });

});
