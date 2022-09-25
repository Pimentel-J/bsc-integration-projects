import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { TipoTripulanteService } from './tipoTripulante.service';
import { TipoTripulante } from './tipoTripulante';

describe('TipoTripulanteService', () => {
  let tipoTripulanteService: TipoTripulanteService;
  let tipoTripulante: TipoTripulante;
  let resultado: any;

  tipoTripulante = {
    codigo: "PT",
    descricao: "Teste"
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([tipoTripulante]));
  httpClientSpy.put.and.returnValue(of([tipoTripulante]));
  httpClientSpy.post.and.returnValue(of([tipoTripulante]));
  httpClientSpy.delete.and.returnValue(of([tipoTripulante]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TipoTripulanteService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([TipoTripulanteService], (service: TipoTripulanteService) => {
    tipoTripulanteService = service;
  }));

  it('should be defined', () => {
    expect(tipoTripulanteService).toBeTruthy();
  });

  it('should getTiposTripulante using http request', () => {
    tipoTripulanteService.getTiposTripulante().subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getTipoTripulante using http request', () => {
    tipoTripulanteService.getTipoTripulante(tipoTripulante.codigo).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  // Exemplos com toPromise()
  it('should searchTiposTripulante using http request', async () => {
    resultado = await tipoTripulanteService.searchTiposTripulante(tipoTripulante.codigo).toPromise();
    expect(resultado[0].codigo).toEqual(tipoTripulante.codigo);
  });

  it('searchTiposTripulante should return empty array', async () => {
    resultado = await tipoTripulanteService.searchTiposTripulante("").toPromise();
    expect(resultado).toEqual([]);
  });

  it('should addTipoTripulante using http request', () => {
    tipoTripulanteService.addTipoTripulante(tipoTripulante).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateTipoTripulante using http request', () => {
    tipoTripulanteService.updateTipoTripulante(tipoTripulante, "PT").subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteTipoTripulante using http request', () => {
    tipoTripulanteService.deleteTipoTripulante(tipoTripulante).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

});