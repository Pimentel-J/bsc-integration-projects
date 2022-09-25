const NoMapper = require('../../../src/mappers/noMapper');
const NoSchema = require('../../../src/infrastructure/mongoDB/schemas/noMongoDB');
const TestFixtures = require('../../fixtures/no');
const mongoose = require('mongoose');

const ttSchemaModel = mongoose.model('No', NoSchema);

const mockReq = TestFixtures.noRequest;
let mockObjArray = TestFixtures.nosFromFile;

const dto = TestFixtures.nosDTO[0];
const dtoArray = TestFixtures.nosDTO;
const model = TestFixtures.nosModel[0];
const modelArray = TestFixtures.nosModel;

describe('### NoMapper ###', function () {
  let resDTO;
  let resModel;
  let resData;

  test('noMapper should json2dto', function () {
    resDTO = NoMapper.json2dto(mockReq);
    expect(resDTO).toEqual(dto);
  });

  test('noMapper should dto2model', function () {
    resModel = NoMapper.dto2model(dto);
    expect(resModel).toEqual(model);
  });

  test('noMapper should domain2data', function () {
    resData = NoMapper.domain2data(model, ttSchemaModel());
    expect(JSON.stringify(resData)).toContain(model.abreviatura);
  });

  test('noMapper should fileObj2dto', function () {
    resDTO = NoMapper.fileObj2dto(mockObjArray[0]);
    expect(resDTO.abreviatura).toEqual(dto.abreviatura);
  });

  test('noMapper should objectArray2dto', function () {
    resDTO = NoMapper.objectArray2dto(mockObjArray);
    expect(resDTO[0].abreviatura).toEqual(dtoArray[0].abreviatura);
  });

  test('noMapper should dtoArray2Model', function () {
    resModel = NoMapper.dtoArray2Model(modelArray);
    expect(resModel).toEqual(modelArray);
  });

});
