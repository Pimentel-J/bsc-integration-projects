const { mockResponse, mockRequest } = require('../../tools');
const TipoViaturaService = require('../../../src/services/TipoViaturaService');
const TipoViaturaMapper = require('../../../src/mappers/tipoviaturaMapper');
const TipoViaturaRepo = require('../../../src/repositories/tipoviaturaRepo');
const CriarTipoViaturaDTO = require('../../../src/dto/tipoViaturaDTO');

jest.mock('../../../src/mappers/tipoviaturaMapper');
jest.mock('../../../src/repositories/tipoviaturaRepo');

const mockRes = mockResponse();
const mockReq = mockRequest();

const dto = new CriarTipoViaturaDTO("5", "teste");

describe('### TipoViaturaService ###', function () {
    // afterEach(() => { jest.resetModules(); });
    test('criarTipoViatura should call Mapper and Repo', function () {
        TipoViaturaService.criarTipoViatura(dto, mockRes);
        expect(TipoViaturaMapper.dto2model && TipoViaturaRepo.save).toHaveBeenCalledTimes(1);
    });

    test('criarTiposViaturaFromFile should call Mapper and Repo', function () {
        TipoViaturaService.criarTiposViaturaFromFile(dto);
        expect(TipoViaturaRepo.saveMany && TipoViaturaMapper.dtoArray2Model).toHaveBeenCalledTimes(1);
    });

    test('criarTiposViaturaFromFile error', async function () {
        jest.spyOn(TipoViaturaRepo, 'saveMany').mockImplementation(async () => { throw new Error('error'); });
        try {
            await TipoViaturaService.criarTiposViaturaFromFile(mockReq);
        } catch (err) {
            expect(err).toEqual(new Error('error'));
        };
    });

    test('listarTodosTipos should call Repo', function () {
        TipoViaturaService.listarTodosTipos(dto, mockRes);
        expect(TipoViaturaRepo.listarTodosTipos).toHaveBeenCalledTimes(1);
    });

    test('getTipoViatura should call Repo', function () {
        TipoViaturaService.getTipoViatura(dto.codigo, mockRes);
        expect(TipoViaturaRepo.getTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('altTipoViatura should call Repo', function () {
        TipoViaturaService.altTipoViatura("6", dto, mockRes);
        expect(TipoViaturaRepo.altTipoViatura).toHaveBeenCalledTimes(1);
    });

    test('delTipoViatura should call Repo', function () {
        TipoViaturaService.delTipoViatura("6", mockRes);
        expect(TipoViaturaRepo.delTipoViatura).toHaveBeenCalledTimes(1);
    });
});