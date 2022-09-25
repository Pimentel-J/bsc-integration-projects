const LinhaModel = require('../../../src/models/linha');
const TestFixLinha = require('../../fixtures/linha');

const linhaTest = TestFixLinha.linhaModel;

describe('#Linha', function () {
    test('Codigo should be Linha1', () => {
        expect(linhaTest.codigo).toBe("Linha1");
    });
    test('Descricao should be teste', () => {
        expect(linhaTest.nome).toBe("teste");
    });
    test('PermissoesTipoViatura should be BUS_ELECT', () => {
        expect(linhaTest.permissoesTipoViatura).toEqual(["BUS_ELECT"]);
    });
    test('PermissoesTipoMotorista should be PTENG', () => {
        expect(linhaTest.permissoesTipoMotorista).toEqual(["PTENG"]);
    });

    test('Should return true for equal objects', () => {
        expect(linhaTest).toMatchObject(TestFixLinha.linhaModel);
    });
    test('Should be instance of TipoTripulanteModel', () => {
        expect(linhaTest).toBeInstanceOf(LinhaModel);
    });
});