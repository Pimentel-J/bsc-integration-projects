const { mockRequest, mockResponse, mockNext } = require('../../tools');
const TipoTripulanteController = require('../../../src/controllers/TipoTripulanteController');
const TipoTripulanteMapper = require('../../../src/mappers/tipoTripulanteMapper');
const TipoTripulanteService = require('../../../src/services/TipoTripulanteService');

jest.mock('../../../src/mappers/tipoTripulanteMapper');
jest.mock('../../../src/services/TipoTripulanteService');

const mockReq = mockRequest();
const mockRes = mockResponse();
const next = mockNext();

describe('### TipoTripulanteController ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarTipoTripulante should call Mapper and Service', function () {
    TipoTripulanteController.criarTipoTripulante(mockReq, mockRes, next);
    expect(TipoTripulanteMapper.json2dto && TipoTripulanteService.criarTipoTripulante).toHaveBeenCalledTimes(1);
  });

  test('criarTiposTripulanteFromFile should call Mapper and Service', function () {
    TipoTripulanteController.criarTiposTripulanteFromFile(mockReq);
    expect(TipoTripulanteMapper.objectArray2dto &&
      TipoTripulanteService.criarTiposTripulanteFromFile).toHaveBeenCalledTimes(1);
  });

  test('criarTiposTripulanteFromFile error', async function () {
    jest.spyOn(TipoTripulanteService, 'criarTiposTripulanteFromFile').
      mockImplementation(async () => { throw new Error('error'); });
    try {
      await TipoTripulanteController.criarTiposTripulanteFromFile(mockReq);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarTodosTiposTripulante should call Service', function () {
    TipoTripulanteController.listarTodosTiposTripulante(mockReq, mockRes);
    expect(TipoTripulanteService.listarTodosTiposTripulante).toHaveBeenCalledTimes(1);
  });

  test('obterTipoTripulante should call Service', function () {
    TipoTripulanteController.obterTipoTripulante("5", mockRes);
    expect(TipoTripulanteService.obterTipoTripulante).toHaveBeenCalledTimes(1);
  });

  test('alterarTipoTripulante should call Mapper and Service', function () {
    TipoTripulanteController.alterarTipoTripulante("6", mockReq, mockRes);
    expect(TipoTripulanteMapper.json2dto && TipoTripulanteService.alterarTipoTripulante).toHaveBeenCalledTimes(1);
  });

  test('apagarTipoTripulante should call Service', function () {
    TipoTripulanteController.apagarTipoTripulante("6", mockRes);
    expect(TipoTripulanteService.apagarTipoTripulante).toHaveBeenCalledTimes(1);
  });
});
