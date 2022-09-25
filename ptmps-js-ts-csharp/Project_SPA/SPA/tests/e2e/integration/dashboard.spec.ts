/// <reference types="cypress" />

describe('The Home Page - Ride AIT', () => {

    // beforeEach(() => {
    //     // reset and seed the database prior to every test
    //     cy.exec('npm run db:reset && npm run db:seed');
    // })

    it('successfully loads', () => {
        cy.visit('/');
    });

    it('should display app name', () => {
        cy.get('h1').should('contain.text', 'rideAIT');
    });

    it('should display menus', () => {
        cy.get('.active').should('contain.text', 'Home');
        cy.get('.dropbtn').should('contain.text', 'Acesso');
    });
});