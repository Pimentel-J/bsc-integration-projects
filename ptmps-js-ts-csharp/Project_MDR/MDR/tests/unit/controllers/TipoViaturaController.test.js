const { mockRequest, mockResponse, mockNext } = require('../../tools');
const TipoViaturaController = require('../../../src/controllers/TipoViaturaController');
const TipoViaturaMapper = require('../../../src/mappers/tipoviaturaMapper');
const TipoViaturaService = require('../../../src/services/TipoViaturaService');


jest.mock('../../../src/mappers/tipoviaturaMapper');
jest.mock('../../../src/services/TipoViaturaService');

const mockReq = mockRequest();
const mockRes = mockResponse();
const next = mockNext();

describe('### TipoViaturaController ###', function () {
    // afterEach(() => { jest.resetModules(); });
    test('criarTipoViatura should call Mapper and Service', function () {
        TipoViaturaController.criarTipoViatura(mockReq, mockRes, next);
        expect(TipoViaturaMapper.json2dto && TipoViaturaService.criarTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('listarTodosTipos should call Service', function () {
        TipoViaturaController.listarTodosTipos(mockReq, mockRes);
        expect(TipoViaturaService.listarTodosTipos).toHaveBeenCalledTimes(1);
    });

    test('getTipoViatura should call Service', function () {
        TipoViaturaController.getTipoViatura(mockReq, mockRes);
        expect(TipoViaturaService.getTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('altTipoViatura should call Service', function () {
        TipoViaturaController.altTipoViatura("Bus", mockReq, mockRes);
        expect(TipoViaturaMapper.json2dto && TipoViaturaService.altTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('delTipoViatura should call Service', function () {
        TipoViaturaController.delTipoViatura("Bus", mockRes);
        expect(TipoViaturaService.delTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('criarTiposViaturaFromFile should call Mapper and Service', function () {
        TipoViaturaController.criarTiposViaturaFromFile(mockReq, mockRes);
        expect(TipoViaturaMapper.objectArray2dto && TipoViaturaService.criarTiposViaturaFromFile)
            .toHaveBeenCalledTimes(1);
    });

    test('criarTiposViaturaFromFile error', async function () {
        jest.spyOn(TipoViaturaService, 'criarTiposViaturaFromFile').
            mockImplementation(async () => { throw new Error('error'); });
        try {
            await TipoViaturaController.criarTiposViaturaFromFile(mockReq, mockRes);
        } catch (err) {
            expect(err).toEqual(new Error('error'));
        };
    });
});