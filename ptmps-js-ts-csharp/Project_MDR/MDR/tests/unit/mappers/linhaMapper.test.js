const LinhaMapper = require('../../../src/mappers/linhaMapper');
const LinhaSchema = require('../../../src/infrastructure/mongoDB/schemas/linhaMongoDB');
const mongoose = require('mongoose');
const TestFixtures = require('../../fixtures/linha');

const mockReq = TestFixtures.linhaRequest;
let mockObjArray = TestFixtures.linhasFromFile;

const dto = TestFixtures.linhaDTO;
const model = TestFixtures.linhaModel;

describe('### LinhaMapper ###', function () {
  let resDTO;
  let resModel;
  let resData;

  test('LinhaMapper should json2dto', function () {
    resDTO = LinhaMapper.json2dto(mockReq);
    expect(resDTO.codigo).toEqual(dto.codigo);
  });

  test('LinhaMapper should dto2model', function () {
    resModel = LinhaMapper.dto2model(dto);
    expect(resModel.codigo).toEqual(model.codigo);
  });

  const linhaSchemaModel = mongoose.model('Linha', LinhaSchema);
  test('LinhaMapper should domain2data', function () {
    resData = LinhaMapper.domain2data(model, linhaSchemaModel());
    expect(resData.codigo).toContain(model.codigo);
  });

  test('LinhaMapper should data2dto', function () {
    resData = LinhaMapper.data2dto(model.codigo, model.nome, model.permissoesTipoViatura,
      model.permissoesTipoMotorista, model.cor);
    expect(resData.codigo).toContain(model.codigo);
  });

});
