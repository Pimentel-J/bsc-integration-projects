const SegmentoMapper = require('../../../src/mappers/segmentoMapper');
const SegmentoSchema = require('../../../src/infrastructure/mongoDB/schemas/segmentoMongoDB');
const TestFixtures = require('../../fixtures/segmento');

const mockReq = TestFixtures.segmentoRequest;

const dto = TestFixtures.segmentosDTO[0];
const model = TestFixtures.segmentosTestValidar[0];
let modelSchema = TestFixtures.segmentosModel[0];

describe('### SegmentoMapper ###', function () {
  let resDTO;
  let resModel;
  let resData;

  test('segmentoMapper should json2dto', function () {
    resDTO = SegmentoMapper.json2dto(mockReq);
    expect(resDTO).toEqual(dto);
  });

  test('segmentoMapper should dto2model', function () {
    resModel = SegmentoMapper.dto2model(TestFixtures.segmentosDTOTestValidar);
    expect(resModel).toEqual(model);
  });

  test('segmentoMapper should domain2data', function () {
    resData = SegmentoMapper.domain2data(model, modelSchema, model.idLinha);
    expect(resData).toEqual(modelSchema);
  });

});
