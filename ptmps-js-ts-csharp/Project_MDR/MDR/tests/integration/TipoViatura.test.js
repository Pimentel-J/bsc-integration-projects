const { mockResponse } = require('../tools');
const TipoViaturaController = require('../../src/controllers/TipoViaturaController');
const TipoViaturaMapper = require('../../src/mappers/tipoviaturaMapper');
const TipoViaturaService = require('../../src/services/TipoViaturaService');
const TipoViaturaRepo = require('../../src/repositories/tipoviaturaRepo');
const TestFixtures = require('../fixtures/tipoViatura');

jest.mock('../../src/repositories/tipoviaturaRepo');

const mockReq = TestFixtures.tipoViaturaRequest;
const mockRes = mockResponse();

const mockObjArray = TestFixtures.tiposViaturaFromFile;
const dto = TestFixtures.tiposViaturaDTO[0];
const dtoArray = TestFixtures.tiposViaturaDTO;
const model = TestFixtures.tiposViaturaModel[0];
const modelArray = TestFixtures.tiposViaturaModel;

describe('### TipoViatura - Integration - From Controller to Service) ###', function () {
  
  afterEach(() => { jest.clearAllMocks() })
  
  TipoViaturaMapper.dto2model = jest.fn().mockReturnValue(model);
  TipoViaturaMapper.dtoArray2Model = jest.fn().mockReturnValue(modelArray);
  
  test('criarTipoViatura', function () {
    spyService = jest.spyOn(TipoViaturaService, 'criarTipoViatura');
    spyMapper = jest.spyOn(TipoViaturaMapper, 'dto2model');
    spyRepository = jest.spyOn(TipoViaturaRepo, 'save');

    TipoViaturaController.criarTipoViatura(mockReq, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto, mockRes);
    expect(spyMapper).toHaveBeenCalledWith(dto);
    expect(spyMapper).toHaveLastReturnedWith(model);
    expect(spyRepository).toHaveBeenCalledWith(model, mockRes);
  });
  
  test('criarTiposViaturaFromFile', function () {
    spyService = jest.spyOn(TipoViaturaService, 'criarTiposViaturaFromFile');
    spyMapper = jest.spyOn(TipoViaturaMapper, 'objectArray2dto');
    spyRepository = jest.spyOn(TipoViaturaRepo, 'saveMany');

    TipoViaturaController.criarTiposViaturaFromFile(mockObjArray, mockRes);

    expect(spyService).toHaveBeenCalledWith(dtoArray, mockRes);
    expect(spyMapper).toHaveBeenCalledWith(mockObjArray);
    expect(spyMapper).toHaveLastReturnedWith(dtoArray);
    expect(spyRepository).toHaveBeenCalledWith(modelArray, mockRes);
  });
  
  test('getTipoViatura', function () {
    spyService = jest.spyOn(TipoViaturaService, 'getTipoViatura');
    spyRepository = jest.spyOn(TipoViaturaRepo, 'getTipoViatura');

    TipoViaturaController.getTipoViatura(dto.codigo, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
  });

  test('listarTodosTipos', function () {
    spyService = jest.spyOn(TipoViaturaService, 'listarTodosTipos');
    spyRepository = jest.spyOn(TipoViaturaRepo, 'listarTodosTipos');

    TipoViaturaController.listarTodosTipos(mockRes);

    expect(spyService).toHaveBeenCalledWith(mockRes);
    expect(spyRepository).toHaveBeenCalledWith(mockRes);
  });

  test('delTipoViatura', function () {
    spyService = jest.spyOn(TipoViaturaService, 'delTipoViatura');
    spyRepository = jest.spyOn(TipoViaturaRepo, 'delTipoViatura');

    TipoViaturaController.delTipoViatura(model.codigo, mockRes);
    
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
  });

});
