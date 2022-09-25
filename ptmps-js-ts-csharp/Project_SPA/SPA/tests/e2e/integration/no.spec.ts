/// <reference types="cypress" />

describe('Nó Workspace', () => {

  it('Main page should display all elements', () => {
    cy.visit('/nos');
    cy.get('h2').should('contain.text', 'Nós da rede');
    cy.get('button').should('contain.text', 'Adicionar nó');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Código');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Nome');
  });

  it('Sub page should display all elements', () => {
    cy.visit('/no');
    cy.get('h2').should('contain.text', 'Criar nó');
    cy.get('button').should('contain.text', 'Criar nó');
    cy.get('button').should('contain.text', 'Voltar');
    cy.get(':nth-child(2) > label').should('contain.text', 'Abreviatura');
    cy.get(':nth-child(3) > label').should('contain.text', 'Nome');
  });
});

describe('Nó e2e Tests', () => {

  it('should add Nó', () => {
    cy.visit('/no');

    cy.get(':nth-child(2) > label > .ng-untouched').type('ze2e');
    cy.get(':nth-child(3) > label > .ng-untouched').type('zteste');
    cy.get(':nth-child(4) > label > .ng-untouched').type('1');
    cy.get(':nth-child(5) > label > .ng-untouched').type('2');

    cy.get('.hot').contains('Criar nó').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(1000);
    cy.visit('/nos');
    cy.wait(2000);
    cy.get('app-nos').should('contain.text', 'ze2e');
  });

  it('should delete Nó', () => {
    cy.visit('/nos');
    cy.get('app-nos').last().should('contain.text', 'ze2e');

    cy.get('button[class=delete]').last().click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(2000);
    cy.get('app-nos').should('not.contain.text', 'ze2e');
  });

  // it('should update Nó', () => {
  //   cy.visit('/nos');
  //   cy.get('li').last().contains('e2e').click();
  //   // cy.get('input').clear();
  //   cy.get(':nth-child(2) > label > .ng-untouched').type('edited');

  //   cy.get('button').contains('Alterar').click().then(() => {
  //     cy.on('window:alert' || 'window:confirm', () => true);
  //   });

  //   cy.wait(1000);
  //   cy.visit('/nos');
  //   cy.get('li').last().should('contain.text', 'e2eedited');
  // });

});
