
const Segmento = require('../../../src/models/segmento');
const TestFixtures = require('../../fixtures/segmento');

const segmentoTest = TestFixtures.segmentosModel[0];
const segmentoTestValidar1 = TestFixtures.segmentosTestValidar[0];
const segmentoTestValidar2 = TestFixtures.segmentosTestValidar[1];
const segmentoTestValidar3 = TestFixtures.segmentosTestValidar[2];

describe('#Segmento', () => {

    let segmentoB = TestFixtures.segmentosModel[0];
    let segmentoValidarFalse = segmentoTestValidar3;
    let segmentoValidarElse = segmentoTestValidar2;

    test('ordem should be 1', () => {
        expect(segmentoTest.ordem).toBe('1');
    });
    test('noOrigem should be AGUIA', () => {
        expect(segmentoTest.noOrigem).toBe('AGUIA')
    });
    test('noDestino should be RECAR', () => {
        expect(segmentoTest.noDestino).toBe('RECAR')
    });
    test('duracao should be 8', () => {
        expect(segmentoTest.duracao).toBe(600)
    });

    test('segmentos should be 8', () => {
        expect(segmentoTest.distancia).toBe(500)
    });

    test('Should return true for equal objects', () => {
        expect(segmentoTest).toMatchObject(segmentoB)
    });

    test('Should be instance of Segmento', () => {
        expect(segmentoTest).toBeInstanceOf(Segmento);
    });

    test('validarSegmento should return true', () => {
        const result = segmentoTestValidar1.validarSegmento(segmentoTestValidar3);
        expect(result).toEqual(true);
    });

    test('validarSegmento should return 1st if - false', () => {
        segmentoValidarFalse.ordem = 2;
        const result = segmentoTestValidar1.validarSegmento(segmentoValidarFalse);
        expect(result).toEqual(false);
    });

    test('validarSegmento should return 2nd if - false', () => {
        segmentoValidarFalse.ordem = 1;
        const result = segmentoTestValidar2.validarSegmento(segmentoValidarFalse);
        expect(result).toEqual(false);
    });

    test('validarSegmento should test 1st else', () => {
        segmentoValidarFalse.ordem = 3;
        segmentoValidarElse.noDestino = "PARED";
        const result = segmentoValidarElse.validarSegmento(segmentoValidarFalse);
        expect(result).toEqual(true);
    });

    test('validarSegmento should test 2nd else', () => {
        segmentoValidarFalse.ordem = 1;
        segmentoValidarElse.noOrigem = "GAND";
        const result = segmentoValidarElse.validarSegmento(segmentoValidarFalse);
        expect(result).toEqual(true);
    });

});