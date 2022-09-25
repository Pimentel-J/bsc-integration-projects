const TipoTripulanteMapper = require('../../../src/mappers/tipoTripulanteMapper');
const TipoTripulanteSchema = require('../../../src/infrastructure/mongoDB/schemas/tipoTripulanteMongoDB');
const TestFixtures = require('../../fixtures/tipoTripulante');
const mongoose = require('mongoose');

const mockReq = TestFixtures.tipoTripulanteRequest;
let mockObjArray = TestFixtures.tiposTripulanteFromFile;

const dto = TestFixtures.tiposTripulanteDTO[0];
const dtoArray = TestFixtures.tiposTripulanteDTO;
const model = TestFixtures.tiposTripulanteModel[0];
const modelArray = TestFixtures.tiposTripulanteModel;

describe('### TipoTripulanteMapper ###', function () {
  let resDTO;
  let resModel;
  let resData;

  test('tipoTripulanteMapper should json2dto', function () {
    resDTO = TipoTripulanteMapper.json2dto(mockReq);
    expect(resDTO).toEqual(dto);
  });

  test('tipoTripulanteMapper should dto2model', function () {
    resModel = TipoTripulanteMapper.dto2model(dto);
    expect(resModel).toEqual(model);
  });

  const ttSchemaModel = mongoose.model('TipoTripulante', TipoTripulanteSchema);
  test('tipoTripulanteMapper should domain2data', function () {
    resData = TipoTripulanteMapper.domain2data(model, ttSchemaModel());
    expect(resData.codigo).toContain(model.codigo);
  });

  test('tipoTripulanteMapper should fileObj2dto', function () {
    resDTO = TipoTripulanteMapper.fileObj2dto(mockObjArray[0]);
    expect(resDTO).toEqual(dto);
  });

  test('tipoTripulanteMapper should objectArray2dto', function () {
    resDTO = TipoTripulanteMapper.objectArray2dto(mockObjArray);
    expect(resDTO).toEqual(dtoArray);
  });

  test('tipoTripulanteMapper should dtoArray2Model', function () {
    resModel = TipoTripulanteMapper.dtoArray2Model(modelArray);
    expect(resModel).toEqual(modelArray);
  });

  test('tipoTripulanteMapper should modelArray2Data', function () {
    resData = TipoTripulanteMapper.modelArray2Data(modelArray, ttSchemaModel());
    // pop() na function saveAll() na tipoTripulanteRepo.js troca a ordem do array
    expect(resData[0].codigo).toEqual(modelArray[1].codigo);
  });
});
