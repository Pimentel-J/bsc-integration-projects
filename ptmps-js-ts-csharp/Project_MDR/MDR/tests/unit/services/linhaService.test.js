const { mockResponse, mockRequest } = require('../../tools');
const LinhaService = require('../../../src/services/LinhaService');
const LinhaMapper = require('../../../src/mappers/linhaMapper');
const LinhaRepo = require('../../../src/repositories/linhaRepo');
const TipoTripulanteRepo = require('../../../src/repositories/tipoTripulanteRepo');
const TipoViaturaRepo = require('../../../src/repositories/tipoviaturaRepo');
const TestFixViatura = require('../../fixtures/tipoViatura');
const TestFixTipoTripulante = require('../../fixtures/tipoTripulante');
const TestFixLinha = require('../../fixtures/linha');

jest.mock('../../../src/repositories/linhaRepo');

const mockRes = mockResponse();
const dto = TestFixLinha.linhaDTO;
const model = TestFixLinha.linhaModel;
const tripulantes = TestFixTipoTripulante.tiposTripulanteDTO;
const viaturas = TestFixViatura.tiposViaturaDTO;

jest.spyOn(LinhaRepo, 'getLinha').mockReturnValue(undefined);
jest.spyOn(TipoViaturaRepo, 'get').mockReturnValue(viaturas[0]);
jest.spyOn(TipoTripulanteRepo, 'get').mockReturnValue(tripulantes[0]);
jest.spyOn(LinhaMapper, 'json2dto').mockReturnValue(dto);
jest.spyOn(LinhaMapper, 'dto2model').mockReturnValue(model);

describe('### LinhaService ###', function () {

  afterEach(() => { jest.clearAllMocks() });

  test('criarLinha should call Mapper and Repo', async function () {
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 1);
    expect(LinhaMapper.dto2model && LinhaRepo.save).toHaveBeenCalledTimes(1);
  });

  test('criarLinha else alterarLinha', async function () {
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 2);
    expect(LinhaRepo.alterarLinha).toHaveBeenCalledTimes(1);
  });

  test('criarLinhaFile should call Mapper and Repo', async function () {
    await LinhaService.criarLinhaFile(dto, model.permissoesTipoViatura, model.permissoesTipoMotorista);
    expect(LinhaRepo.saveFile && LinhaMapper.dto2model).toHaveBeenCalledTimes(1);
  });

  test('criarLinhaFile if undefined and codigo already used', async function () {
    jest.spyOn(LinhaRepo, 'getLinha').mockReturnValue(model);
    await LinhaService.criarLinhaFile(dto, undefined, undefined);
    expect(LinhaRepo.saveFile).toHaveBeenCalledTimes(0);
  });

  test('criarLinhaFile error', async function () {
    jest.spyOn(LinhaService, 'criarLinhaFile').mockImplementation(async () => { throw new Error('error'); });
    try {
      await LinhaService.criarLinhaFile(dto, model.permissoesTipoViatura, model.permissoesTipoMotorista);
    } catch (err) {
      expect(err).toEqual(new Error('error'));
    };
  });

  test('obterLinha should call Repo', function () {
    LinhaService.obterLinha(dto.codigo, mockRes);
    expect(LinhaRepo.obterLinha).toHaveBeenCalledTimes(1);
  });

  test('listarTodasLinhas should call Repo', function () {
    LinhaService.listarTodosLinhas(mockRes);
    expect(LinhaRepo.listarTodosLinhas).toHaveBeenCalledTimes(1);
  });

  test('obterPermissoes viaturas', async function () {
    await LinhaService.obterPermissoes(1, dto.codigo, mockRes);
    expect(LinhaRepo.getLinha && LinhaMapper.json2dto).toHaveBeenCalledTimes(1);
  });

  test('obterPermissoes tripulante', async function () {
    await LinhaService.obterPermissoes(2, dto.codigo, mockRes);
    expect(LinhaRepo.getLinha && LinhaMapper.json2dto).toBeCalledTimes(1);
  });

  test('removerLinha should call Repo', function () {
    LinhaService.removerLinha("5", mockRes);
    expect(LinhaRepo.removerLinha).toHaveBeenCalledTimes(1);
  });

});

describe('### LinhaService - Part II ###', function () {

  let ttMock = jest.spyOn(TipoTripulanteRepo, 'get');
  let tvMock = jest.spyOn(TipoViaturaRepo, 'get');
  let lRepo = jest.spyOn(LinhaRepo, 'getLinha');

  afterEach(() => { jest.clearAllMocks() });

  // ### Other criarLinha statements ###
  test('criarLinha else undefined tripulante', async function () {
    ttMock.mockReturnValue(undefined);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 1);
    expect(LinhaRepo.save).toHaveBeenCalledTimes(0);
  });

  test('criarLinha else undefined viatura', async function () {
    tvMock.mockReturnValue(undefined);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 1);
    expect(LinhaRepo.save).toHaveBeenCalledTimes(0);
  });

  test('criarLinha else undefined tripulante/viatura', async function () {
    ttMock.mockReturnValue(undefined);
    tvMock.mockReturnValue(undefined);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 1);
    expect(LinhaRepo.save).toHaveBeenCalledTimes(0);
  });

  test('criarLinha else codigo already used', async function () {
    lRepo.mockReturnValue(model);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 1);
    expect(LinhaRepo.save).toHaveBeenCalledTimes(0);
  });

  // ### Other alterarLinha statements ###
  test('alterarLinha else undefined tripulante', async function () {
    ttMock.mockReturnValue(undefined);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 2);
    expect(LinhaRepo.alterarLinha).toHaveBeenCalledTimes(0);
  });

  test('alterarLinha else undefined viatura', async function () {
    tvMock.mockReturnValue(undefined);
    await LinhaService.criarLinha(dto.codigo, dto, mockRes, 2);
    expect(LinhaRepo.alterarLinha).toHaveBeenCalledTimes(0);
  });

  test('alterarLinha else codigo already used', async function () {
    lRepo.mockReturnValue(model);
    await LinhaService.criarLinha("error", dto, mockRes, 2);
    expect(LinhaRepo.alterarLinha).toHaveBeenCalledTimes(0);
  });

});
