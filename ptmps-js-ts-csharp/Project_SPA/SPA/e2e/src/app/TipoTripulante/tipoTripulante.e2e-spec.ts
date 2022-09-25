import { TipoTripulantePage } from './tipoTripulante.po';
import { browser, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: TipoTripulantePage;

  beforeEach(() => {
    page = new TipoTripulantePage();
  });

  it('should display Tipos de Tripulante header', async () => {
    await page.navigateTo();
    expect(await page.getTitleText()).toEqual('Tipos de Tripulante');
  });

  it('should display Adicionar Tipo de Tripulante button', async () => {
    await page.navigateTo();
    expect(await page.getAddButton()).toEqual('Adicionar Tipo de Tripulante');
  });

  it('should display table headers', async () => {
    await page.navigateTo();
    expect(await page.getTableHeader1()).toEqual('Código');
    expect(await page.getTableHeader2()).toEqual('Descrição');
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
