
const No = require('../../../src/models/no');
const TestFixtures = require('../../fixtures/no');

const noTest = TestFixtures.nosModel[0];

describe('#No', () => {
  
    let noB = TestFixtures.nosModel[0];

    test('Abreviatura should be GAND', () => {
        expect(noB.abreviatura).toBe('GAND');
    });
    test('Nome should be Gandra', () => {
        expect(noB.nome).toBe('Gandra')
    });
    test('Latitude should be 41', () => {
        expect(noB.latitude).toBe(41)
    });
    test('Longitude should be 8', () => {
        expect(noB.longitude).toBe(8)
    });
    test('Estacao de Recolha should be false', () => {
        expect(noB.estacaoRecolha).toBe(false)
    });
    test('Ponto Rendicao should be false', () => {
        expect(noB.pontoRendicao).toBe(false)
    });
        
    test('Should return true for equal objects', () => {
        expect(noTest).toMatchObject(noB)
    });

    test('Should be instance of No', () => {
        expect(noTest).toBeInstanceOf(No);
    });

    test(' tipos de campos corretos', function() {
        expect(typeof (noTest.abreviatura)).toBe('string');
        expect(typeof (noTest.nome)).toBe('string');
        expect(typeof (noTest.latitude)).toBe('number');
        expect(typeof (noTest.longitude)).toBe('number');
        expect(typeof (noTest.estacaoRecolha)).toBe('boolean');
        expect(typeof (noTest.pontoRendicao)).toBe('boolean');
      });

         
});