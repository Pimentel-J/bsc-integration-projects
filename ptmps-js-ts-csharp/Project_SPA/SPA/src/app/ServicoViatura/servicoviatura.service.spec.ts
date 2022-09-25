import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { ServicoViaturaService } from './servicoviatura.service';
import { ServicoViatura } from './servicoviatura';

describe('ServicoviaturaService', () => {
  let servicoServicoViatura: ServicoViaturaService;
  let servicoViatura: ServicoViatura;

  servicoViatura = {
    id: "PT",
    viaturaId: "Teste",
    viagens: []
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([servicoViatura]));
  httpClientSpy.post.and.returnValue(of([servicoViatura]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ServicoViaturaService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([ServicoViaturaService], (service: ServicoViaturaService) => {
    servicoServicoViatura = service;
  }));

  it('should be created', () => {
    expect(servicoServicoViatura).toBeTruthy();
  });

  it('should getServicoViaturas using http request', () => {
    servicoServicoViatura.getServicoViaturas();
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getServicoViatura using http request', () => {
    servicoServicoViatura.getServicoViatura(servicoViatura.id).subscribe(res => expect(res).toBe(res));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addServicoViatura using http request', () => {
    servicoServicoViatura.addServicoViatura(servicoViatura).subscribe(res => expect(res).toBe(res));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });
});