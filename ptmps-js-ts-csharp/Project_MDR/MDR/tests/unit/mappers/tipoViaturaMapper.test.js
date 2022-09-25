const TipoViaturaMapper = require('../../../src/mappers/tipoviaturaMapper');
const TipoViaturaSchema = require('../../../src/infrastructure/mongoDB/schemas/tipoviaturaMongoDB');
const TestFixtures = require('../../fixtures/tipoViatura');
const mongoose = require('mongoose');

const mockReq = TestFixtures.tipoViaturaRequest;
let mockObjArray = TestFixtures.tiposViaturaFromFile;

const dto = TestFixtures.tiposViaturaDTO[0];
const dtoArray = TestFixtures.tiposViaturaDTO;
const model = TestFixtures.tiposViaturaModel[0];
const modelArray = TestFixtures.tiposViaturaModel;

const ttSchemaModel = mongoose.model('TipoViatura', TipoViaturaSchema);

describe('### TipoViaturaMapper ###', function () {
    let resDTO;
    let resModel;
    let resData;

    test('tipoViaturaMapper should json2dto', function () {
        resDTO = TipoViaturaMapper.json2dto(mockReq);
        expect(resDTO).toEqual(dto);
    });

    test('tipoViaturaMapper should dto2model', function () {
        resModel = TipoViaturaMapper.dto2model(dto);
        expect(resModel).toEqual(model);
    });

    test('tipoViaturaMapper should domain2data', function () {
        resData = TipoViaturaMapper.domain2data(model, ttSchemaModel());
        expect(resData.codigo).toContain(model.codigo);
    });

    test('tipoViaturaMapper should objectArray2dto', function () {
        resDTO = TipoViaturaMapper.objectArray2dto(mockObjArray);
        expect(resDTO[0]).toEqual(dtoArray[0]);
    });

    test('tipoViaturaMapper should dtoArray2model', function () {
        resModel = TipoViaturaMapper.dtoArray2Model(dtoArray);
        expect(resModel[0]).toEqual(modelArray[0]);
    });

    test('tipoViaturaMapper should modelArray2Data', function () {
        resData = TipoViaturaMapper.modelArray2Data(modelArray, ttSchemaModel);
        expect(resData[0].codigo).toEqual(modelArray[1].codigo);
    });

    test('tipoViaturaMapper should fileObj2dto', function () {
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO).toEqual(dto);
        
        mockObjArray[0].$.EnergySource = "23";
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO.tipoCombustivel).toEqual('Gasoleo');

        mockObjArray[0].$.EnergySource = "50";
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO.tipoCombustivel).toEqual('Hidrogenio');

        mockObjArray[0].$.EnergySource = "75";
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO.tipoCombustivel).toEqual('Eletrico');

        mockObjArray[0].$.EnergySource = "1";
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO.tipoCombustivel).toEqual('Gasolina');
        // default
        mockObjArray[0].$.EnergySource = "3";
        resDTO = TipoViaturaMapper.fileObj2dto(mockObjArray[0]);
        expect(resDTO.tipoCombustivel).toEqual('erro');
    });

});