const TipoViatura = require('../../../src/models/tipoViatura');
const TestFixtures = require('../../fixtures/tipoViatura');

const tipoViaturaTest = TestFixtures.tiposViaturaModel[1];
const tipoViaturaB = TestFixtures.tiposViaturaModel[1];

describe('#TipoViatura', () => {

    test('Codigo/nome should be BUS_GPL7', () => {
        expect(tipoViaturaB.codigo).toBe('BUS_GPL7');
        expect(tipoViaturaB.nome).toBe('BUS_GPL7');
    });

    test('autonmia should be 200000', () => {
        expect(tipoViaturaB.autonomia).toBe(200000.0);
    });

    test('consumo medio should be 40.50', () => {
        expect(tipoViaturaB.consumoMedio).toBe(40.50);
    });

    test('custoKm should be 18.50', () => {
        expect(tipoViaturaB.custoKm).toBe(18.50);
    });
    test('velocidade media should be 50', () => {
        expect(tipoViaturaB.velocidadeMedia).toBe(50);
    });
    test('emissoesshould be 700', () => {
        expect(tipoViaturaB.emissoesCO2).toBe(700);
    });

    test('Should return true for equal objects', () => {
        expect(tipoViaturaTest).toMatchObject(tipoViaturaB)
    });

    const tipoList = [
        'Gasolina',
        'Diesel',
        'Gas',
        'Eletrico',
        'GPL',
    ];

    test('o tipo combustivel consta da lista', () => {
        expect(tipoList).toContain(tipoViaturaTest.tipoCombustivel);
    });

    test('Should be instance of TipoViaturaModel', () => {
        expect(tipoViaturaTest).toBeInstanceOf(TipoViatura);
    });

    test(' tipos de campos corretos', function () {
        expect(typeof (tipoViaturaTest.codigo)).toBe('string');
        expect(typeof (tipoViaturaTest.nome)).toBe('string');
        expect(typeof (tipoViaturaTest.tipoCombustivel)).toBe('string');
        expect(typeof (tipoViaturaTest.autonomia)).toBe('number');
        expect(typeof (tipoViaturaTest.consumoMedio)).toBe('number');
        expect(typeof (tipoViaturaTest.custoKm)).toBe('number');
        expect(typeof (tipoViaturaTest.emissoesCO2)).toBe('number');
    });

});