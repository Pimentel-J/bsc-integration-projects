const { mockRequest, mockResponse } = require('../../tools');
const TipoTripulanteService = require('../../../src/services/TipoTripulanteService');
const TipoTripulanteMapper = require('../../../src/mappers/tipoTripulanteMapper');
const TipoTripulanteRepo = require('../../../src/repositories/tipoTripulanteRepo');
const TestFixtures = require('../../fixtures/tipoTripulante');

jest.mock('../../../src/mappers/tipoTripulanteMapper');
jest.mock('../../../src/repositories/tipoTripulanteRepo');

const mockReq = mockRequest();
const mockRes = mockResponse();
const dto = TestFixtures.tiposTripulanteDTO[0];

describe('### TipoTripulanteService ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarTipoTripulante should call Mapper and Repo', function () {
    TipoTripulanteService.criarTipoTripulante(dto, mockRes);
    expect(TipoTripulanteMapper.dto2model && TipoTripulanteRepo.save).toHaveBeenCalledTimes(1);
  });

  test('criarTiposTripulanteFromFile should call Mapper and Repo', function () {
    TipoTripulanteService.criarTiposTripulanteFromFile(dto);
    expect(TipoTripulanteRepo.saveMany).toHaveBeenCalledTimes(1);
    expect(TipoTripulanteMapper.dtoArray2Model).toHaveBeenCalledTimes(1);
  });

  test('criarTiposTripulanteFromFile error', async function () {
    jest.spyOn(TipoTripulanteRepo, 'saveMany').mockImplementation(async () => { throw new Error('error'); });
    try {
      await TipoTripulanteService.criarTiposTripulanteFromFile(mockReq);     
      // await expect(TipoTripulanteService.criarTiposTripulanteFromFile).rejects.toThrowError();
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarTodosTiposTripulante should call Repo', function () {
    TipoTripulanteService.listarTodosTiposTripulante(mockReq, mockRes);
    expect(TipoTripulanteRepo.listarTodosTiposTripulante).toHaveBeenCalledTimes(1);
  });

  test('obterTipoTripulante should call Repo', function () {
    TipoTripulanteService.obterTipoTripulante(mockReq, mockRes);
    expect(TipoTripulanteRepo.obterTipoTripulante).toHaveBeenCalledTimes(1);
  });

  test('alterarTipoTripulante should call Repo', function () {
    TipoTripulanteService.alterarTipoTripulante("5", dto, mockRes);
    expect(TipoTripulanteRepo.alterarTipoTripulante).toHaveBeenCalledTimes(1);
  });

  test('apagarTipoTripulante should call Repo', function () {
    TipoTripulanteService.apagarTipoTripulante("5", mockRes);
    expect(TipoTripulanteRepo.apagarTipoTripulante).toHaveBeenCalledTimes(1);
  });
});


