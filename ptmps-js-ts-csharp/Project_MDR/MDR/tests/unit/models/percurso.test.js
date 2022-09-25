
const Percurso = require('../../../src/models/percurso');
const TestFixtures = require('../../fixtures/percurso');

const percursoTest = TestFixtures.percursosModel[0];

describe('#Percurso', () => {
  
    let percursoB = TestFixtures.percursosModel[0];

    test('idLinha should be Line1', () => {
        expect(percursoTest.idLinha).toBe('Line1');
    });
    test('idPercurso should be Path1', () => {
        expect(percursoTest.idPercurso).toBe('Path1')
    });
    test('ida should be true', () => {
        expect(percursoTest.ida).toBe(true)
    });
    test('segmentos should be 8', () => {
        expect(percursoTest.segmentos).toBe(percursoB.segmentos)
    });
        
    test('Should return true for equal objects', () => {
        expect(percursoTest).toMatchObject(percursoB)
    });

    test('Should be instance of Percurso', () => {
        expect(percursoTest).toBeInstanceOf(Percurso);
    });
             
});