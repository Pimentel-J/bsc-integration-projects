const { mockResponse } = require('../tools');
const LinhaController = require('../../src/controllers/LinhaController');
const LinhaMapper = require('../../src/mappers/linhaMapper');
const LinhaService = require('../../src/services/LinhaService');
const LinhaRepo = require('../../src/repositories/linhaRepo');
const TestFixtures = require('../fixtures/linha');

jest.mock('../../src/repositories/linhaRepo');

const mockRes = mockResponse();

const mockObjFile = TestFixtures.linhaFromFile;
const dto = TestFixtures.linhaDTO;
const model = TestFixtures.linhaModel;
const linhaDtoSemPermissoes = TestFixtures.linhaDtoSemPermissoes;

describe('### Linha - Integration - From Controller to Service) ###', function () {
  
  afterEach(() => { jest.clearAllMocks() })
  
  LinhaMapper.dto2model = jest.fn().mockReturnValue(model);
  LinhaMapper.data2dto = jest.fn().mockReturnValue(dto);
  
  test('criarLinha', function () {
    spyService = jest.spyOn(LinhaService, 'criarLinha');
    spyMapper = jest.spyOn(LinhaMapper, 'dto2model');

    LinhaController.criarLinha(dto.codigo, mockObjFile, mockRes, 1);

    expect(spyService).toHaveBeenCalledWith(dto.codigo, dto, mockRes, 1);
    expect(spyMapper).toHaveBeenCalledWith(dto);
    expect(spyMapper).toHaveLastReturnedWith(model);
  });
  
  test('criarLinhaWithData', function () {
    spyService = jest.spyOn(LinhaService, 'criarLinhaFile');
    spyMapper = jest.spyOn(LinhaMapper, 'dto2model');
    spyRepository = jest.spyOn(LinhaRepo, 'saveFile');

    LinhaController.criarLinhaWithData(dto.codigo, dto.nome, dto.permissoesTipoViatura, 
      dto.permissoesTipoMotorista, dto.cor);

    expect(spyService).toHaveBeenCalledWith(linhaDtoSemPermissoes, dto.permissoesTipoViatura, dto.permissoesTipoMotorista);
  });
  
  test('obterLinha', function () {
    spyService = jest.spyOn(LinhaService, 'obterLinha');
    spyRepository = jest.spyOn(LinhaRepo, 'obterLinha');

    LinhaController.obterLinha(dto.codigo, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
  });

  test('listarTodosLinhas', function () {
    spyService = jest.spyOn(LinhaService, 'listarTodosLinhas');
    spyRepository = jest.spyOn(LinhaRepo, 'listarTodosLinhas');

    LinhaController.listarTodosLinhas(mockRes);

    expect(spyService).toHaveBeenCalledWith(mockRes);
    expect(spyRepository).toHaveBeenCalledWith(mockRes);
  });

  test('removerLinha', function () {
    spyService = jest.spyOn(LinhaService, 'removerLinha');
    spyRepository = jest.spyOn(LinhaRepo, 'removerLinha');

    LinhaController.removerLinha(model.codigo, mockRes);
    
    expect(spyRepository).toHaveBeenCalledWith(model.codigo, mockRes);
    expect(spyService).toHaveBeenCalledWith(dto.codigo, mockRes);
  });

});
