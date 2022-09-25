const PercursoMapper = require('../../../src/mappers/percursoMapper');
const PercursoSchema = require('../../../src/infrastructure/mongoDB/schemas/percursoMongoDB');
const TestFixtures = require('../../fixtures/percurso');

const mockReq = TestFixtures.percursoRequest;

const dto = TestFixtures.percursosDTO[0];
const model = TestFixtures.percursosModel[0];
let modelSchema = TestFixtures.percursoSchemaModel;

describe('### PercursoMapper ###', function () {
  let resDTO;
  let resModel;
  let resData;

  test('percursoMapper should json2dto', function () {
    resDTO = PercursoMapper.json2dto(mockReq);
    expect(resDTO).toEqual(dto);
  });

  test('percursoMapper should dto2model', function () {
    resModel = PercursoMapper.dto2model(dto);
    expect(resModel).toEqual(model);
  });

  test('percursoMapper should domain2data', function () {
    resData = PercursoMapper.domain2data(model, modelSchema, model.idLinha);
    expect(resData).toEqual(modelSchema);
  });

});
