const TipoTripulanteModel = require('../../../src/models/tipoTripulante');
const TestFixtures = require('../../fixtures/tipoTripulante');

const tipoTripulanteTest = TestFixtures.tiposTripulanteModel[0];
const tipoTripulanteTestEmpty = new TipoTripulanteModel(" ", " ");
const tipoTripulanteTestUndefined = new TipoTripulanteModel();

describe('### TipoTripulanteModel ###', function () {

    test('Codigo should be 2 and Descricao should be \"descr\"', () => {
        expect(tipoTripulanteTest.codigo).toBe("5");
        expect(tipoTripulanteTest.descricao).toBe("teste5");
    });

    test('Should return true for equal objects', () => {
        let tipoTripulanteClone = TestFixtures.tiposTripulanteModel[0];
        expect(tipoTripulanteTest).toMatchObject(tipoTripulanteClone);
    });

    test('Should be instance of TipoTripulanteModel', () => {
        expect(tipoTripulanteTest).toBeInstanceOf(TipoTripulanteModel);
    });

    test('Should be empty', () => {
        expect(tipoTripulanteTestEmpty.codigo && tipoTripulanteTestEmpty.descricao).toBe(" ");
    });

    test('Should be undefined', () => {
        expect(tipoTripulanteTestUndefined.codigo && tipoTripulanteTestUndefined.descricao).toBe(undefined);
    });
});