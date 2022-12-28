/// <reference types="cypress" />

describe('Linha Workspace', () => {

  it('Main page should display all elements', () => {
    cy.visit('/linhas');
    cy.get('h2').should('contain.text', 'Linhas da rede');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Código');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Nome');
    cy.get('app-linhas > div > button').should('contain.text', 'Adicionar');
  });

  it('Sub page should display all elements', () => {
    cy.visit('/linha');
    cy.get('h2').should('contain.text', 'Criar linha');
    cy.get(':nth-child(2) > label').should('contain.text', 'Codigo');
    cy.get(':nth-child(3) > label').should('contain.text', 'Nome');
    cy.get('app-linha-detail > :nth-child(1) > :nth-child(5)').should('contain.text', 'Voltar');
    cy.get('.hot').should('contain.text', 'Criar linha');
  });
});

describe('Linha e2e Tests', () => {

  it('should add Linha', () => {
    cy.visit('/linha');

    cy.intercept('POST', 'https://***private***.herokuapp.com/api/linha').as('post');

    cy.get(':nth-child(2) > label').type('ze2e');
    cy.get('button').contains('Criar linha').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });
    // cy.wait(1000);
    cy.wait('@post').its('response.statusCode').should('equal', 400)
  });

});
