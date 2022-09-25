import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { TripulanteService } from './tripulante.service';
import { Tripulante } from './tripulante';
import { TipoTripulante } from '../TipoTripulante/tipoTripulante';

describe('TripulanteService', () => {
  let tripulanteService: TripulanteService;
  let tripulante: Tripulante;
  let tipoTripulante: TipoTripulante;

  tipoTripulante = {
    codigo: "PTFR",
    descricao: "Teste"
  };

  tripulante = {
    numeroMecanografico: "TRP123456",
    nome: "Miguel Gomes",
    dataNascimento: "02-03-1985",
    numeroCartaoCidadao: "12345678 4ZX6",
    nif: "123456789",
    numeroCartaConducao: "P-1234567",
    dataEmissaoLicencaConducao: "20-05-2003",
    dataValidadeLicencaConducao: "20-05-2053",
    tipoTripulante: tipoTripulante.codigo,
    dataEntradaEmpresa: "01-01-2020",
    dataSaidaEmpresa: null
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([tripulante]));
  httpClientSpy.put.and.returnValue(of([tripulante]));
  httpClientSpy.post.and.returnValue(of([tripulante]));
  httpClientSpy.delete.and.returnValue(of([tripulante]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TripulanteService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([TripulanteService], (service: TripulanteService) => {
    tripulanteService = service;
  }));

  it('should be defined', () => {
    expect(tripulanteService).toBeTruthy();
  });

  it('should getTiposTripulante using http request', () => {
    tripulanteService.getTripulantes().subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getTripulante using http request', () => {
    tripulanteService.getTripulante(tripulante.numeroMecanografico).subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addTripulante using http request', () => {
    tripulanteService.addTripulante(tripulante).subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should deleteTripulante using http request', () => {
    tripulanteService.deleteTripulante(tripulante).subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should updateTripulante using http request', () => {
    tripulanteService.updateTripulante(tripulante, "TRP123456").subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });
  
  it('should deleteTripulante using http request', () => {
    tripulanteService.deleteTripulante(tripulante.numeroMecanografico).subscribe(res => expect(res[0].numeroMecanografico).toEqual("TRP123456"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

});