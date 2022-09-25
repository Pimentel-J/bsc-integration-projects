// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
import "cypress-localstorage-commands";

Cypress.Commands.add("login", (username) => {
    cy.visit('/login');
    cy.get(':nth-child(1) > .form-control').type(username);
    cy.get(':nth-child(2) > .form-control').type('***private***');
    cy.get('button').contains('Login').click();

    cy.setCookie('session_id', 'remember_token');
})

var headers_login = new Headers()
headers_login.append('Content-Type', 'application/json')


Cypress.Commands.add('postToken', () => {
    let token
    cy.request({
        method: 'POST',
        url: 'https://lapr5-20s5-3na-2-auth.herokuapp.com/users/authenticate',
        failOnStatusCode: false,
        json: true,
        form: true,
        body: { username: 'admin', password: '***private***' },
        headers: headers_login
    }).then((json) => {
        cy.setLocalStorage('identity_token', json.body.token)
        // token = json.body.token
        // return token;
    });
});
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })
