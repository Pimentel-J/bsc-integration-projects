/// <reference types="cypress" />

describe('Viatura Workspace', () => {
  
  it('Main page should display all elements', () => {
    cy.visit('/viaturas');
    cy.wait(1000);
    cy.get('h2').should('contain.text', 'Viaturas');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Código');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Chassis');
    cy.get('tr > :nth-child(3)').should('contain.text', 'Tipo Viatura');
    cy.get('tr > :nth-child(4)').should('contain.text', 'Data Entrada Serviço');
    cy.get('button').should('contain.text', 'Adicionar Viatura');
  });
  
  it('Sub page should display all elements', () => {
    cy.visit('/viatura');
    cy.get('h2').should('contain.text', 'Criar Viatura');
    cy.get(':nth-child(2) > label').should('contain.text', 'Matricula');
    cy.get(':nth-child(3) > label').should('contain.text', 'N I V');
    cy.get(':nth-child(4) > label').should('contain.text', 'Tipo Viatura');
    cy.get(':nth-child(5) > label').should('contain.text', 'Data Entrada Serviço');
    cy.get('.hot').should('contain.text', 'Criar');
    cy.get('button').should('contain.text', 'Voltar');
  });
});

describe('Viatura e2e Tests', () => {

  it('should add Viatura', () => {
    cy.visit('/viatura');
    
    cy.get(':nth-child(2) > label > .ng-untouched').type('99-ZZ-99');
    cy.get(':nth-child(3) > label > .ng-untouched').type('123123E2E12345657');
    cy.get('select').select('Autocarro');
    cy.get(':nth-child(5) > label > .ng-untouched').type('01-01-2020');

    cy.get('.hot').contains('Criar').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(1000);
    cy.visit('/viaturas');
    cy.wait(2000);
    cy.get('app-viaturas').should('contain.text', '99-ZZ-99');
  });
  
  it('should delete Viatura', () => {
    cy.visit('/viaturas');
    cy.get('app-viaturas').should('contain.text', '99-ZZ-99');

    cy.get('button[class=delete]').last().click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(2000);
    cy.get('app-viaturas').should('not.contain.text', '99-ZZ-99');
  });

  // it('should update Viatura', () => {
  //   cy.visit('/viaturas');
  //   cy.get('li').contains('99-ZZ-99').click();
  //   cy.get(':nth-child(3) > label > .ng-untouched').clear();
  //   cy.get(':nth-child(3) > label > .ng-untouched').type('123123R2R12345657');

  //   cy.get('button').contains('Alterar').click().then(() => {
  //     cy.on('window:alert' || 'window:confirm', () => true);
  //   });
  //   cy.wait(1000);
  //   cy.visit('/viaturas');
  //   cy.get('li').should('contain.text', '123123R2R12345657');
  // });
});
