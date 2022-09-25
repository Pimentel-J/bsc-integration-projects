/// <reference types="cypress" />

describe('Auth Token Test', () => {
  before(() => {
    cy.postToken();
    cy.saveLocalStorage();
  });

  beforeEach(() => {
    cy.restoreLocalStorage();
  });

  it("should exist identity in localStorage", () => {
    cy.getLocalStorage("identity_token").should("exist");
    cy.getLocalStorage("identity_token").then(token => {
      console.log("Identity token", token);
    });
  });

  it("should still exist identity in localStorage", () => {
    cy.getLocalStorage("identity_token").should("exist");
    cy.getLocalStorage("identity_token").then(token => {
      console.log("Identity token", token);
    });
  });
});

describe('Login', () => {

  it("should have access to Admin menus", () => {
    cy.login('admin');
    cy.wait(3000);
    cy.get(':nth-child(2) > .dropbtn').should('contain.text', 'Rede');
    cy.get(':nth-child(3) > .dropbtn').should('contain.text', 'Viaturas');
    cy.get(':nth-child(4) > .dropbtn').should('contain.text', 'Tripulantes');
    cy.get(':nth-child(5) > .dropbtn').should('contain.text', 'Viagens');
    cy.get(':nth-child(6) > .dropbtn').should('contain.text', 'Planeamento');
    cy.get('[routerlink="/importFile"]').should('contain.text', 'Importar dados');
  });
});