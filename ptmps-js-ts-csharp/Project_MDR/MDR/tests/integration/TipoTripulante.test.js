const { mockResponse, mockNext } = require('../tools');
const TipoTripulanteController = require('../../src/controllers/TipoTripulanteController');
const TipoTripulanteMapper = require('../../src/mappers/tipoTripulanteMapper');
const TipoTripulanteService = require('../../src/services/TipoTripulanteService');
const TipoTripulanteRepo = require('../../src/repositories/tipoTripulanteRepo');
const TestFixtures = require('../fixtures/tipoTripulante');

jest.mock('../../src/repositories/tipoTripulanteRepo');

const mockReq = TestFixtures.tipoTripulanteRequest;
const mockRes = mockResponse();
const next = mockNext();

const mockObjArray = TestFixtures.tiposTripulanteFromFile;
const dto = TestFixtures.tiposTripulanteDTO[0];
const dtoArray = TestFixtures.tiposTripulanteDTO;
const model = TestFixtures.tiposTripulanteModel[0];
const modelArray = TestFixtures.tiposTripulanteModel;

describe('### TipoTripulante - Integration - From Controller to Service) ###', function () {
  
  afterEach(() => { jest.clearAllMocks() })
  
  TipoTripulanteMapper.dto2model = jest.fn().mockReturnValue(model);
  TipoTripulanteMapper.dtoArray2Model = jest.fn().mockReturnValue(modelArray);
  
  test('criarTipoTripulante', function () {
    spyService = jest.spyOn(TipoTripulanteService, 'criarTipoTripulante');
    spyMapper = jest.spyOn(TipoTripulanteMapper, 'dto2model');
    spyRepository = jest.spyOn(TipoTripulanteRepo, 'save');

    TipoTripulanteController.criarTipoTripulante(mockReq, mockRes, next);

    expect(spyService).toHaveBeenCalledWith(dto, mockRes);
    expect(spyMapper).toHaveBeenCalledWith(dto);
    expect(spyMapper).toHaveLastReturnedWith(model);
    expect(spyRepository).toHaveBeenCalledWith(model, mockRes);
  });
  
  test('criarTiposTripulanteFromFile', function () {
    spyService = jest.spyOn(TipoTripulanteService, 'criarTiposTripulanteFromFile');
    spyMapper = jest.spyOn(TipoTripulanteMapper, 'dtoArray2Model');
    spyRepository = jest.spyOn(TipoTripulanteRepo, 'saveMany');

    TipoTripulanteController.criarTiposTripulanteFromFile(mockObjArray);

    expect(spyService).toHaveBeenCalledWith(dtoArray);
    expect(spyMapper).toHaveBeenCalledWith(dtoArray);
    expect(spyMapper).toHaveLastReturnedWith(modelArray);
    expect(spyRepository).toHaveBeenCalledWith(modelArray);
  });
  
  test('obterTipoTripulante', function () {
    spyService = jest.spyOn(TipoTripulanteService, 'obterTipoTripulante');
    spyRepository = jest.spyOn(TipoTripulanteRepo, 'obterTipoTripulante');

    TipoTripulanteController.obterTipoTripulante(dto.codigo, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
  });

  test('listarTodosTiposTripulante', function () {
    spyService = jest.spyOn(TipoTripulanteService, 'listarTodosTiposTripulante');
    spyRepository = jest.spyOn(TipoTripulanteRepo, 'listarTodosTiposTripulante');

    TipoTripulanteController.listarTodosTiposTripulante(mockReq, mockRes);

    expect(spyService).toHaveBeenCalledWith(mockReq, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(mockRes);
  });

  test('apagarTipoTripulante', function () {
    spyService = jest.spyOn(TipoTripulanteService, 'apagarTipoTripulante');
    spyRepository = jest.spyOn(TipoTripulanteRepo, 'apagarTipoTripulante');

    TipoTripulanteController.apagarTipoTripulante(model.codigo, mockRes);
    
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
  });

});
