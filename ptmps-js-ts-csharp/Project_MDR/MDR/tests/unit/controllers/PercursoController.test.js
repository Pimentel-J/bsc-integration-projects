const { mockRequest, mockResponse } = require('../../tools');
const PercursoController = require('../../../src/controllers/PercursoController');
const PercursoMapper = require('../../../src/mappers/percursoMapper');
const PercursoService = require('../../../src/services//PercursoService');

jest.mock('../../../src/mappers/percursoMapper');
jest.mock('../../../src/services/PercursoService');

const mockReq = mockRequest();
const mockRes = mockResponse();

describe('### PercursoController ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('definirPercurso should call Mapper and Service', function () {
    PercursoController.definirPercurso(mockReq, mockRes);
    expect(PercursoMapper.json2dto && PercursoService.definirPercurso).toHaveBeenCalledTimes(1);
  });

  test('importarPercursos should call Mapper and Service', function () {
    PercursoController.importarPercursos(mockReq);
    expect(PercursoService.importarPercursos).toHaveBeenCalledTimes(1);
  });

  test('importarPercursos error', async function () {
    jest.spyOn(PercursoService, 'importarPercursos').mockImplementation(async () => { throw new Error('error'); });
    try {
      await PercursoController.importarPercursos(mockReq);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('listarPercursos should call Service', function () {
    PercursoController.listarPercursos("query", mockRes);
    expect(PercursoService.listarPercursos).toHaveBeenCalledTimes(1);
  });

  test('listarTodosPercursos should call Service', function () {
    PercursoController.listarTodosPercursos(mockRes);
    expect(PercursoService.listarTodosPercursos).toHaveBeenCalledTimes(1);
  });

  test('obterPercurso should call Service', function () {
    PercursoController.obterPercurso("1", mockRes);
    expect(PercursoService.obterPercurso).toHaveBeenCalledTimes(1);
  });

});
