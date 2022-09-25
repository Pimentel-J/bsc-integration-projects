const { mockResponse } = require('../../tools');
const PercursoRepo = require('../../../src/repositories/percursoRepo');
const PercursoMapper = require('../../../src/mappers/percursoMapper');
const PercursoService = require('../../../src/services/PercursoService');
const SegmentoRepo = require('../../../src/repositories/segmentoRepo');
const TestFixtures = require('../../fixtures/percurso');

jest.mock('../../../src/mappers/percursoMapper');
jest.mock('../../../src/repositories/percursoRepo');
jest.mock('../../../src/mappers/segmentoMapper');
jest.mock('../../../src/repositories/segmentoRepo');

const mockRes = mockResponse();
let dto = TestFixtures.percursosModel1Segmento[0];

describe('### PercursoService ###', function () {

  afterEach(() => { jest.clearAllMocks(); });
  
  test('definirPercurso should call Mapper and Repo', async function () {
    await PercursoService.definirPercurso(dto, mockRes);
    expect(PercursoMapper.dto2model && PercursoRepo.save).toHaveBeenCalledTimes(1);
  });
  
  test('importarPercursos should call Mapper and Repo', async function () {
    jest.spyOn(PercursoMapper, 'dto2model').mockReturnValue(dto);
    await PercursoService.importarPercursos([dto]);
    expect(PercursoRepo.saveFromFile && PercursoMapper.dto2model).toHaveBeenCalledTimes(1);
  });

  test('importarPercursos error', async function () {
    jest.spyOn(PercursoMapper, 'dto2model').mockImplementation(async () => { throw new Error('error'); });
    try {
      await PercursoService.importarPercursos([dto]);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarPercursos should call Repo', function () {
    PercursoService.listarPercursos("query", mockRes);
    expect(PercursoRepo.listarPercursos).toHaveBeenCalledTimes(1);
  });

  test('listarTodosPercursos should call Repo', function () {
    PercursoService.listarTodosPercursos(mockRes);
    expect(PercursoRepo.listarTodosPercursos).toHaveBeenCalledTimes(1);
  });

  test('obterPercurso should call Repo', function () {
    PercursoService.obterPercurso("1", mockRes);
    expect(PercursoRepo.obterPercurso).toHaveBeenCalledTimes(1);
  });

});

describe('### PercursoService - Other Errors ###', function () {

  afterEach(() => { jest.clearAllMocks(); });

  test('definirPercurso error', async function () {
    jest.spyOn(PercursoRepo, 'save').mockImplementation(async () => { throw new Error('error'); });
    try {
      await PercursoService.definirPercurso(dto, mockRes);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('eachSegmento error', async function () {
    jest.spyOn(SegmentoRepo, 'saveFromFile').mockImplementation(async () => { throw new Error('error'); });
    try {
      await PercursoService.definirPercurso(dto, mockRes);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

});
