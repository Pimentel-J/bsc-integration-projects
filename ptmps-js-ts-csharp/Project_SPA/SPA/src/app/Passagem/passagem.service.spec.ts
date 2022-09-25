import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { PassagemService } from './passagem.service';
import { Passagem } from './passagem';
import { Viagem } from '../Viagem/viagem';

describe('PassagemService', () => {
  let passagemService: PassagemService;
  let passagem: Passagem;
  let viagem: Viagem;

  passagem = {
    id: "P1",
    viagemId: viagem,
    horaPassagem: 3600,
    abreviaturaNo: "N1"
  };

  viagem = {
    id: "V1",
    percursoId: "P1",
    descritivo: "Teste",
    horaInicio: 3600,
    horaFim: 7200,
    passagens: [passagem]
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([passagem]));
  httpClientSpy.post.and.returnValue(of(passagem));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PassagemService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([PassagemService], (service: PassagemService) => {
    passagemService = service;
  }));

  it('should be defined', () => {
    expect(passagemService).toBeTruthy();
  });
    
  it('should getPassagens using http request', () => {
    passagemService.getPassagens().subscribe(res => expect(res).toBe(res));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getPassagem using http request', () => {
    passagemService.getPassagem(passagem.id).subscribe(res => expect(res).toBe(res));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

});