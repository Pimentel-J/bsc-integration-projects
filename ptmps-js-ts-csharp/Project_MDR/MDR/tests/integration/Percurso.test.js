const { mockResponse } = require('../tools');
const PercursoController = require('../../src/controllers/PercursoController');
const PercursoMapper = require('../../src/mappers/percursoMapper');
const PercursoService = require('../../src/services/PercursoService');
const PercursoRepo = require('../../src/repositories/percursoRepo');
const TestFixtures = require('../fixtures/percurso');
const TestSegFixtures = require('../fixtures/segmento');

jest.mock('../../src/repositories/percursoRepo');

const mockRes = mockResponse();

const dto = TestFixtures.percursosDTO[0];
const dtoArray = TestFixtures.percursosModel;
const model = TestFixtures.percursosModel[0];
const segModel = TestSegFixtures.segmentosModel[0];

describe('### Percurso - Integration - From Controller to Service) ###', function () {

  afterEach(() => { jest.clearAllMocks() })

  PercursoMapper.dto2model = jest.fn().mockReturnValue(model);
  PercursoMapper.domain2data = jest.fn().mockReturnValue(model);
  PercursoMapper.json2dto = jest.fn().mockReturnValue(dto);

  test('definirPercurso', function () {
    spyService = jest.spyOn(PercursoService, 'definirPercurso');
    spyMapper = jest.spyOn(PercursoMapper, 'dto2model');
    spyRepository = jest.spyOn(PercursoRepo, 'save');

    PercursoController.definirPercurso(dto, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto, mockRes);
  });
  
  test('importarPercursos', function () {
    spyService = jest.spyOn(PercursoService, 'importarPercursos');
    spyMapper = jest.spyOn(PercursoMapper, 'dto2model');
    spyRepository = jest.spyOn(PercursoRepo, 'saveFromFile');

    PercursoController.importarPercursos(dtoArray);

    expect(spyService).toHaveBeenCalledWith(dtoArray);
  });

  test('listarPercursos', function () {
    spyService = jest.spyOn(PercursoService, 'listarPercursos');
    spyRepository = jest.spyOn(PercursoRepo, 'listarPercursos');

    PercursoController.listarPercursos("query", mockRes);

    expect(spyService).toHaveBeenCalledWith("query", mockRes);
    expect(spyRepository).toHaveBeenCalledWith("query", mockRes);
  });

  test('listarTodosPercursos', function () {
    spyService = jest.spyOn(PercursoService, 'listarTodosPercursos');
    spyRepository = jest.spyOn(PercursoRepo, 'listarTodosPercursos');

    PercursoController.listarTodosPercursos(mockRes);

    expect(spyService).toHaveBeenCalledWith(mockRes);
    expect(spyRepository).toHaveBeenCalledWith(mockRes);
  });

  test('obterPercurso', function () {
    spyService = jest.spyOn(PercursoService, 'obterPercurso');
    spyRepository = jest.spyOn(PercursoRepo, 'obterPercurso');

    PercursoController.obterPercurso(dto.idPercurso, mockRes);

    expect(spyService).toHaveBeenCalledWith(dto.idPercurso, mockRes);
    expect(spyRepository).toHaveBeenCalledWith(model.idPercurso, mockRes);
  });

});
