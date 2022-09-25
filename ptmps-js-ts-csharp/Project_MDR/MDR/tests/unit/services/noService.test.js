const { mockRequest, mockResponse } = require('../../tools');
const NoRepo = require('../../../src/repositories/noRepo');
const NoController = require('../../../src/controllers/NoController');
const NoMapper = require('../../../src/mappers/noMapper');
const NoService = require('../../../src/services/NoService');
const TestFixtures = require('../../fixtures/no');

jest.mock('../../../src/mappers/noMapper');
jest.mock('../../../src/repositories/noRepo');

const mockReq = mockRequest();
const mockRes = mockResponse();
const dto = TestFixtures.nosDTO[0];

describe('### NoService ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarNo should call Mapper and Repo', function () {
    NoService.criarNo(dto, mockRes);
    expect(NoMapper.dto2model && NoRepo.save).toHaveBeenCalledTimes(1);
  });

  test('criarNosFromFile should call Mapper and Repo', function () {
    NoService.criarNosFromFile(dto);
    expect(NoRepo.saveMany).toHaveBeenCalledTimes(1);
    expect(NoMapper.dtoArray2Model).toHaveBeenCalledTimes(1);
  });

  test('criarNosFromFile error', async function () {
    jest.spyOn(NoRepo, 'saveMany').mockImplementation(async () => { throw new Error('error'); });
    try {
      await NoService.criarNosFromFile(mockReq, mockRes);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarNos should call Repo', function () {
    NoService.listarNos("query", mockRes);
    expect(NoRepo.listarNos).toHaveBeenCalledTimes(1);
  });

  test('listarTodosNos should call Repo', function () {
    NoService.listarTodosNos(mockRes);
    expect(NoRepo.listarTodosNos).toHaveBeenCalledTimes(1);
  });

  test('obterNo should call Repo', function () {
    NoService.obterNo("GAND", mockRes);
    expect(NoRepo.obterNo).toHaveBeenCalledTimes(1);
  });

  test('alterarTipoTripulante should call Repo', function () {
    NoService.alterarNo("GAND", dto, mockRes);
    expect(NoRepo.alterarNo).toHaveBeenCalledTimes(1);
  });

  test('apagarTipoTripulante should call Repo', function () {
    NoService.removerNo("GAND", mockRes);
    expect(NoRepo.removerNo).toHaveBeenCalledTimes(1);
  });
});


