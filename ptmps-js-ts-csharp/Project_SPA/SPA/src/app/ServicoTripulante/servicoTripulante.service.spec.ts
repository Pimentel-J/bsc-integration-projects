import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { ServicoTripulanteService } from './servicoTripulante.service';
import { ServicoTripulante } from './servicoTripulante';

describe('ServicoTripulanteService', () => {
  let servicoTripulanteService: ServicoTripulanteService;
  let servicoTripulante: ServicoTripulante;
  let resultado: any;

  servicoTripulante = {
    codigoServicoTripulante: "PT"
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([servicoTripulante]));
  httpClientSpy.put.and.returnValue(of([servicoTripulante]));
  httpClientSpy.post.and.returnValue(of([servicoTripulante]));
  httpClientSpy.delete.and.returnValue(of([servicoTripulante]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ServicoTripulanteService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([ServicoTripulanteService], (service: ServicoTripulanteService) => {
    servicoTripulanteService = service;
  }));

  it('should be defined', () => {
    expect(servicoTripulanteService).toBeTruthy();
  });

  it('should getServicosTripulante using http request', () => {
    servicoTripulanteService.getServicosTripulante().subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getServicoTripulante using http request', () => {
    servicoTripulanteService.getServicoTripulante(servicoTripulante.codigoServicoTripulante).subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addServicoTripulante using http request', () => {
    servicoTripulanteService.addServicoTripulante(servicoTripulante).subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  // it('should updateServicoTripulante using http request', () => {
  //   servicoTripulanteService.updateServicoTripulante(servicoTripulante, "PT").subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
  //   expect(httpClientSpy.put).toHaveBeenCalled();
  // });

  it('should deleteServicoTripulante using http request (codigoServicoTripulante)', () => {
    servicoTripulanteService.deleteServicoTripulante(servicoTripulante.codigoServicoTripulante).subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should deleteServicoTripulante using http request (servicoTripulante)', () => {
    servicoTripulanteService.deleteServicoTripulante(servicoTripulante).subscribe(res => expect(res[0].codigoServicoTripulante).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

});