/// <reference types="cypress" />

describe('Tipo Tripulante Workspace', () => {

  // before(() => {
  //   cy.login('admin');
  //   cy.wait(1500);
  // });

  it('Main page should display all elements', () => {
    cy.visit('/tiposTripulante');
    cy.wait(1000);
    cy.get('h2').should('contain.text', 'Tipos de Tripulante');
    cy.get('button').should('contain.text', 'Adicionar Tipo de Tripulante');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Código');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Descrição');
  });

  it('Sub page should display all elements', () => {
    cy.visit('/tipoTripulante');
    cy.get('h2').should('contain.text', 'Criar Tipo de Tripulante');
    cy.get(':nth-child(2) > label').should('contain.text', 'Código');
    cy.get(':nth-child(3) > label').should('contain.text', 'Descrição');
    cy.get('app-tipotripulante-detail > :nth-child(1) > :nth-child(6)').should('contain.text', 'Voltar');
    cy.get('.hot').should('contain.text', 'Guardar');
  });
});

describe('Tipo Tripulante e2e Tests', () => {

  it('should add Tipo de Tripulante', () => {
    cy.visit('/tipoTripulante');

    // cy.intercept('https://lapr5-20s5-3na-2-mdr.herokuapp.com/api/tiposTripulante').as('post');

    cy.get('#input-codigo').type('e2e');
    cy.get('#input-descricao').type('teste');

    // cy.intercept('POST', '/tiposTripulante').as('Success');
    cy.get('.hot').contains('Guardar').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(1000);
    cy.visit('/tiposTripulante');
    cy.wait(2000);
    cy.get('app-tipostripulante').should('contain.text', 'e2e');

    // cy.wait('@Success').then((interception) => {
    //   // access the low level request that contains the request body, response body, status, etc
    //   cy.get(interception.request.body).contains('e2e');
    // });
  });

  it('should delete Tipo de Tripulante', () => {
    cy.visit('/tiposTripulante');
    cy.get('app-tipostripulante').should('contain.text', 'e2e');

    cy.get('button[class=delete]').last().click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(2000);
    cy.get('app-tipostripulante').should('not.contain.text', 'e2e');
  });

});
