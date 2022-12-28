# SPA - Part of ISEP-LEI - 5th Semester

* [Project's Main Readme](../Project_MDR/README.md)

* [Project's Wiki](../Project_Wiki/)

* [MDR and Planeamento](../Project_MDR/)

* [MDV](../Project_MDV/)

## Work Group

| |
|-----------|
| Calisto   |
| Esperança |
| Magalhães |
| Oliveira  |
| Pimentel  |

> This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 11.0.2.

## Integration Mode

Run `npm start`

* MDR Cloud URL: https://***private***.herokuapp.com

* Planeamento Cloud URL: http://***private***:1234

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

[Cypress e2e - Fast, easy and reliable testing for anything that runs in a browser.](https://www.cypress.io)

  * 1º - Run `npm start`
  * 2º - Run `npm run cy:run` or `npm run cy:open` (Cypress in the interactive mode)

~~Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).~~

## Running test coverage

`ng test --no-watch --code-coverage`

If you want to create code-coverage reports every time you test, you can set the following option in the CLI configuration file, angular.json:

`"test": {
  "options": {
    "codeCoverage": true
  }
}`

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

### Commonly used locators in protractor:

* `by.css('selector-name')`: This is by far the commonly used locator for finding an element based on the name of the CSS selector.
* `by.id('id')`: Locates an element with a matching value for the id attribute.
* `by.name('name-value')`: Locates an element with a matching value for the name attribute.
* `by.buttonText('button-value')`: Locates a button element or an array of button elements based on the inner text.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
