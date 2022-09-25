import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { TipoviaturaService } from './tipoviatura.service';
import { Tipoviatura } from './tipoviatura';


describe('TipoviaturaService', () => {
  let tipoViaturaService: TipoviaturaService;
  let tipoViatura: Tipoviatura;
  let resultado: any;

  tipoViatura = {
    codigo: "BUS_GAS01",
    nome: "BUS_GAS01",
    tipoCombustivel: "GPL",
    autonomia: 30000,
    consumoMedio: 8.45,
    custoKm: 0.8,
    velocidadeMedia: 35,
    emissoesCO2: 700
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([tipoViatura]));
  httpClientSpy.put.and.returnValue(of([tipoViatura]));
  httpClientSpy.post.and.returnValue(of([tipoViatura]));
  httpClientSpy.delete.and.returnValue(of([tipoViatura]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TipoviaturaService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([TipoviaturaService], (service: TipoviaturaService) => {
    tipoViaturaService = service;
  }));

  it('should be created', () => {
    expect(tipoViaturaService).toBeTruthy();
  });

  it('should getTiposviatura using http request', () => {
    tipoViaturaService.getTiposviatura().subscribe(res => expect(res[0].codigo).toEqual("BUS_GAS01"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getTipoviatura using http request', () => {
    tipoViaturaService.getTipoviatura(tipoViatura.codigo).subscribe(res => expect(res[0].codigo).toEqual("BUS_GAS01"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should searchTpv using http request', async () => {
    resultado = await tipoViaturaService.searchTpv(tipoViatura.codigo).toPromise();
    expect(resultado[0].codigo).toEqual(tipoViatura.codigo);
  });

  it('searchTiposviatura should return empty array', async () => {
    resultado = await tipoViaturaService.searchTpv("").toPromise();
    expect(resultado).toEqual([]);
  });

  it('should addTipoviatura using http request', () => {
    tipoViaturaService.addTipoviatura(tipoViatura).subscribe(res => expect(res[0].codigo).toEqual("BUS_GAS01"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateTipoviatura using http request', () => {
    tipoViaturaService.updateTipoviatura(tipoViatura, "BUS_GAS01").subscribe(res => expect(res[0].codigo).toEqual("BUS_GAS01"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteTipoviatura using http request', () => {
    tipoViaturaService.deleteTipoviatura(tipoViatura).subscribe(res => expect(res[0].codigo).toEqual("BUS_GAS01"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

});
