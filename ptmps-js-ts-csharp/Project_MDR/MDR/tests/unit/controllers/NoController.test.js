const { mockRequest, mockResponse } = require('../../tools');
const NoController = require('../../../src/controllers/NoController');
const NoMapper = require('../../../src/mappers/noMapper');
const NoService = require('../../../src/services/NoService');

jest.mock('../../../src/mappers/noMapper');
jest.mock('../../../src/services/NoService');

const mockReq = mockRequest();
const mockRes = mockResponse();

describe('### NoController ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarNo should call Mapper and Service', function () {
    NoController.criarNo(mockReq, mockRes);
    expect(NoMapper.dto2model && NoService.criarNo).toHaveBeenCalledTimes(1);
  });

  test('criarNosFromFile should call Mapper and Service', function () {
    NoController.criarNosFromFile(mockReq);
    expect(NoMapper.objectArray2dto && NoService.criarNosFromFile).toHaveBeenCalledTimes(1);
  });

  test('criarNosFromFile error', async function () {
    jest.spyOn(NoService, 'criarNosFromFile').mockImplementation(async () => { throw new Error('error'); });
    try {
      await NoController.criarNosFromFile(mockReq);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarNos should call Service', function () {
    NoController.listarNos("query", mockRes);
    expect(NoService.listarNos).toHaveBeenCalledTimes(1);
  });

  test('listarTodosNos should call Service', function () {
    NoController.listarTodosNos(mockRes);
    expect(NoService.listarTodosNos).toHaveBeenCalledTimes(1);
  });

  test('obterNo should call Service', function () {
    NoController.obterNo("GAND", mockRes);
    expect(NoService.obterNo).toHaveBeenCalledTimes(1);
  });

  test('alterarNo should call Mapper and Service', function () {
    NoController.alterarNo("GAND", mockReq, mockRes);
    expect(NoMapper.json2dto && NoService.alterarNo).toHaveBeenCalledTimes(1);
  });
  
  test('removerNo should call Service', function () {
    NoController.removerNo("GAND", mockRes);
    expect(NoService.removerNo).toHaveBeenCalledTimes(1);
  });
  
    test('alterarNo should not call Mapper and Service', function () {
      jest.clearAllMocks();
      NoController.alterarNo("GAND", undefined, mockRes);
      expect(NoMapper.json2dto && NoService.alterarNo).toHaveBeenCalledTimes(0);
    });
});
