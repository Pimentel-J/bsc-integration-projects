const { mockRequest, mockResponse } = require('../../tools');
const SegmentoService = require('../../../src/services/SegmentoService');
const SegmentoMapper = require('../../../src/mappers/segmentoMapper');
const SegmentoRepo = require('../../../src/repositories/segmentoRepo');
const TestFixtures = require('../../fixtures/segmento');

jest.mock('../../../src/mappers/segmentoMapper');
jest.mock('../../../src/repositories/segmentoRepo');

const mockRes = mockResponse();
const dto = TestFixtures.segmentosDTO[0];

describe('### SegmentoService ###', function () {
  // afterEach(() => { jest.resetModules(); });
  test('criarSegmento should call Mapper and Repo', function () {
    SegmentoService.criarSegmento(dto, mockRes);
    expect(SegmentoMapper.dto2model && SegmentoRepo.save).toHaveBeenCalledTimes(1);
  });

});


