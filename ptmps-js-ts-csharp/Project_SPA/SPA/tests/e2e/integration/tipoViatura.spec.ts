/// <reference types="cypress" />

describe('Tipo Viatura Workspace', () => {

  it('Main page should display all elements', () => {
    cy.visit('/tiposviatura');
    cy.get('h2').should('contain.text', 'Tipos de Viatura');
    cy.get('button').should('contain.text', 'Adicionar');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Código');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Detalhes');
  });

  it('Sub page should display all elements', () => {
    cy.visit('/tipoviatura');
    cy.get('h2').should('contain.text', 'Criar Tipo Viatura');
    cy.get(':nth-child(2) > label').should('contain.text', 'Codigo');
    cy.get(':nth-child(3) > label').should('contain.text', 'Nome');
    cy.get(':nth-child(4) > label').should('contain.text', 'Tipo Combustivel');
    cy.get(':nth-child(5) > label').should('contain.text', 'Autonomia');
    cy.get(':nth-child(6) > label').should('contain.text', 'Consumo Medio');
    cy.get(':nth-child(7) > label').should('contain.text', 'Custo Km');
    cy.get(':nth-child(8) > label').should('contain.text', 'Velocidade Media');
    cy.get(':nth-child(9) > label').should('contain.text', 'Emissões CO2');
    cy.get('button').should('contain.text', 'Guardar');
    cy.get('button').should('contain.text', 'Voltar');
  });
});

describe('Tipo Viatura e2e Tests', () => {

  it('should add Tipo de Viatura', () => {
    cy.visit('/tipoviatura');

    cy.get(':nth-child(2) > label > .ng-untouched').type('ze2e');
    cy.get(':nth-child(3) > label > .ng-untouched').type('ze2e');
    cy.get('select').select('GPL');
    cy.get(':nth-child(5) > label > .ng-untouched').type('1000');
    cy.get(':nth-child(6) > label > .ng-untouched').type('10');
    cy.get(':nth-child(7) > label > .ng-untouched').type('10');
    cy.get(':nth-child(8) > label > .ng-untouched').type('50');
    cy.get(':nth-child(9) > label > .ng-untouched').type('100');

    cy.get('button').contains('Guardar').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(1000);
    cy.visit('/tiposviatura');
    cy.wait(2000);
    cy.get('app-tpv-list').should('contain.text', 'ze2e');
  });

  it('should delete Tipo de Viatura', () => {
    cy.visit('/tiposviatura');
    cy.get('app-tpv-list').should('contain.text', 'ze2e');

    cy.get('button[class=delete]').last().click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(2000);
    cy.get('app-tpv-list').should('not.contain.text', 'ze2e');
  });

  // it('should update Tipo de Viatura', () => {
  //   cy.visit('/tiposviatura');
  //   cy.get('li').last().contains('e2e').click();
  //   // cy.get('input').clear();
  //   cy.get(':nth-child(2) > label > .ng-untouched').type('edited');

  //   cy.get('button').contains('Alterar').click().then(() => {
  //     cy.on('window:alert' || 'window:confirm', () => true);
  //   });
  //   cy.wait(1000);
  //   cy.visit('/tiposviatura');
  //   cy.get('li').last().should('contain.text', 'e2eedited');
  // });
});
