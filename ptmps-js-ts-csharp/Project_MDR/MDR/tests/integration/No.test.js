const { mockResponse, mockNext } = require('../tools');
const NoController = require('../../src/controllers/NoController');
const NoMapper = require('../../src/mappers/noMapper');
const NoService = require('../../src/services/NoService');
const NoRepo = require('../../src/repositories/noRepo');
const TestFixtures = require('../fixtures/no');

jest.mock('../../src/repositories/noRepo');

const mockReq = TestFixtures.noRequest;
const mockRes = mockResponse();
const next = mockNext();

const mockObjArray = TestFixtures.nosFromFile;
const dto = TestFixtures.nosDTO[0];
const dtoArray = TestFixtures.nosDTO2;
const model = TestFixtures.nosModel[0];
const modelArray = TestFixtures.nosModel2;

describe('### No - Integration - From Controller to Service) ###', function () {
  
  afterEach(() => { jest.clearAllMocks() })
  
  NoMapper.dto2model = jest.fn().mockReturnValue(model);
  NoMapper.dtoArray2Model = jest.fn().mockReturnValue(modelArray);
  
  test('criarNo', function () {
    spyService = jest.spyOn(NoService, 'criarNo');
    spyMapper = jest.spyOn(NoMapper, 'json2dto');
    spyRepository = jest.spyOn(NoRepo, 'save');

    NoController.criarNo(mockReq, mockRes, next);

    expect(spyService).toHaveBeenCalledWith(dto, mockRes);
    expect(spyMapper).toHaveBeenCalledWith(dto);
    expect(spyMapper).toHaveLastReturnedWith(dto);
    expect(spyRepository).toHaveBeenCalledWith(model, mockRes);
  });
  
  test('criarNosFromFile', function () {
    spyService = jest.spyOn(NoService, 'criarNosFromFile');
    spyMapper = jest.spyOn(NoMapper, 'objectArray2dto');
    spyRepository = jest.spyOn(NoRepo, 'saveMany');

    NoController.criarNosFromFile(mockObjArray);

    expect(spyMapper).toHaveBeenCalledWith(mockObjArray);
    expect(spyMapper).toHaveLastReturnedWith(modelArray);
    expect(spyService).toHaveBeenCalledWith(dtoArray);
    expect(spyRepository).toHaveBeenCalledWith(modelArray);
  });
  
  test('obterNo', function () {
    spyService = jest.spyOn(NoService, 'obterNo');
    spyRepository = jest.spyOn(NoRepo, 'obterNo');

    NoController.obterNo(dto.abreviatura, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto.abreviatura, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(model.abreviatura, mockRes);
  });

  test('listarTodosNos', function () {
    spyService = jest.spyOn(NoService, 'listarTodosNos');
    spyRepository = jest.spyOn(NoRepo, 'listarTodosNos');

    NoController.listarTodosNos(mockRes);

    expect(spyService).toHaveBeenCalledWith(mockRes);
    expect(spyRepository).toHaveBeenCalledWith(mockRes);
  });

  test('removerNo', function () {
    spyService = jest.spyOn(NoService, 'removerNo');
    spyRepository = jest.spyOn(NoRepo, 'removerNo');

    NoController.removerNo(model.abreviatura, mockRes);

    expect(spyRepository).toHaveBeenCalledWith(model.abreviatura, mockRes);
    expect(spyService).toHaveBeenCalledWith(dto.abreviatura, mockRes);
  });

  test('listarNos', function () {
    spyService = jest.spyOn(NoService, 'listarNos');
    spyRepository = jest.spyOn(NoRepo, 'listarNos');

    NoController.listarNos("query", mockRes);

    expect(spyService).toHaveBeenCalledWith("query", mockRes);
    expect(spyRepository).toHaveBeenCalledWith("query", mockRes);
  });

});
