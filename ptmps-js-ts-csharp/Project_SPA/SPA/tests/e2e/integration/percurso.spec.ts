/// <reference types="cypress" />

describe('Percurso Workspace', () => {

  it('Main page should display all elements', () => {
    cy.visit('/percursos');
    cy.get('h2').should('contain.text', 'Percursos da rede');
    cy.get('button').should('contain.text', 'Adicionar Percurso');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Linha');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Código Percurso');
    cy.get('tr > :nth-child(3)').should('contain.text', 'Ida');
  });

  it('Sub page should display all elements', () => {
    cy.visit('/percurso');
    cy.get('h2').should('contain.text', 'Criar percurso');
    cy.get(':nth-child(2) > label').should('contain.text', 'Cod. Percurso');
    cy.get(':nth-child(3) > label').should('contain.text', 'Linha');
    cy.get(':nth-child(4) > label').should('contain.text', 'Ida');
    cy.get(':nth-child(5) > h2').should('contain.text', 'Segmentos');
    cy.get('button').should('contain.text', 'Criar percurso');
    cy.get('button').should('contain.text', 'Voltar');
  });
});

describe('Percurso e2e Tests', () => {

  it('should add Percurso', () => {
    cy.visit('/percurso');

    cy.intercept('POST', '/percurso').as('postUser');

    cy.get(':nth-child(2) > label > .ng-untouched').type('ze2e?');
    // cy.get('select').select('Paredes_Aguiar');
    cy.get('button').contains('Criar percurso').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });
    
    cy.wait('@postUser').its('response.statusCode').should('equal', 400)
  });
});
