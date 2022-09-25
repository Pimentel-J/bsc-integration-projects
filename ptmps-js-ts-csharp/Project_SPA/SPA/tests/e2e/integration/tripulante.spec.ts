/// <reference types="cypress" />

describe('Tripulante Workspace', () => {
  
  it('Main page should display all elements', () => {
    cy.visit('/tripulantes');
    cy.get('h2').should('contain.text', 'Tripulantes');
    cy.get('button').should('contain.text', 'Adicionar Tripulante');
    cy.get('tr > :nth-child(1)').should('contain.text', 'Nr. Mecanográfico');
    cy.get('tr > :nth-child(2)').should('contain.text', 'Nome');
  });
  
  it('Sub page should display all elements', () => {
    cy.visit('/tripulante');
    cy.get('h2').should('contain.text', 'Criar Tripulante');
    cy.get(':nth-child(2) > label').should('contain.text','Número Mecanográfico');
    cy.get(':nth-child(4) > label').should('contain.text','Nome');
    cy.get(':nth-child(5) > label').should('contain.text','Data de Nascimento');
    cy.get(':nth-child(6) > label').should('contain.text','Número Cartão de Cidadão');
    cy.get(':nth-child(7) > label').should('contain.text','NIF');
    cy.get(':nth-child(8) > label').should('contain.text','Número Carta Condução');
    cy.get(':nth-child(9) > label').should('contain.text','Data Emissão Licença Condução');
    cy.get(':nth-child(10) > label').should('contain.text','Data Validade Licença Condução');
    cy.get(':nth-child(11) > label').should('contain.text','Tipo de Tripulante');
    cy.get(':nth-child(12) > label').should('contain.text','Data de Entrada na Empresa');
    cy.get(':nth-child(13) > label').should('contain.text','Data de Saída na Empresa');
    cy.get('.hot').should('contain.text', 'Guardar');
    cy.get('button').should('contain.text', 'Voltar');
  });
});

describe('Tipo Tripulante e2e Tests', () => {

  it('should add Tripulante', () => {
    cy.visit('/tripulante');
    cy.wait(1000);
    cy.get(':nth-child(2) > label > .ng-untouched').type('TRP999999');
    cy.get(':nth-child(4) > label').type('ze2e');
    cy.get(':nth-child(5) > label > .ng-untouched').type('01-01-1985');
    cy.get(':nth-child(6) > label > .ng-untouched').type('123456 5ZX0');
    cy.get(':nth-child(7) > label > .ng-untouched').type('123456789');
    cy.get(':nth-child(8) > label > .ng-untouched').type('P-123456');
    cy.get(':nth-child(9) > label > .ng-untouched').type('01-01-2010');
    cy.get(':nth-child(10) > label > .ng-untouched').type('01-01-2060');
    cy.get('select').select('PTFR');
    cy.get(':nth-child(12) > label > .ng-untouched').type('01-01-2020');
    cy.get(':nth-child(13) > label > .ng-untouched').type('31-12-2021');

    cy.get('.hot').contains('Guardar').click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(1000);
    cy.visit('/tripulantes');
    cy.wait(2000);
    cy.get('app-tripulantes').should('contain.text', 'ze2e');
  });

  it('should delete Tripulante', () => {
    cy.visit('/tripulantes');
    cy.get('app-tripulantes').should('contain.text', 'ze2e');

    cy.get('button[class=delete]').last().click().then(() => {
      cy.on('window:alert' || 'window:confirm', () => true);
    });

    cy.wait(2000);
    cy.get('app-tripulantes').should('not.contain.text', 'ze2e');
  });
  
  // it('should update Tripulante', () => {
  //   cy.visit('/tripulantes');
  //   cy.get('td[class=coluna1]').contains('e2e').click();
  //   // cy.get('input').clear();
  //   cy.get('#input-nome').type(' edited');
    
  //   cy.get('button').contains('Alterar').click().then(() => {
  //     cy.on('window:alert' || 'window:confirm', () => true);
  //   });

  //   cy.wait(1000);
  //   cy.visit('/tripulantes');
  //   cy.get('td[class=coluna2]').should('contain.text', 'e2e edited');
  // });
});
