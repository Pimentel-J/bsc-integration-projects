import { browser, by, element } from 'protractor';

export class TipoTripulantePage {
  async navigateTo(): Promise<unknown> {
    return browser.get(browser.baseUrl + '/tiposTripulante');
  }

  async navigateToDetail(): Promise<unknown> {
    return browser.get(browser.baseUrl + '/tipoTripulante');
  }

  async getTitleText(): Promise<string> {
    return element(by.css('app-tipostripulante h2')).getText();
  }

  async getAddButton(): Promise<string> {
    return element(by.css('app-tipostripulante div')).getText();
  }

  async getTableHeader1(): Promise<string> {
    return element(by.className('header1')).getText();
  }

  async getTableHeader2(): Promise<string> {
    return element(by.className('header2')).getText();
  }

}
