import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { NoService } from './no.service';
import { No } from './no';

describe('NoService', () => {
  let noService: NoService;
  let no: No;
  let resultado: any;

  no = {
    abreviatura: "GAND",
    nome: "Gandra",
    latitude: 41,
    longitude: 8,
    estacaoRecolha: false,
    pontoRendicao: false,
    modeloMapa: ""
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([no]));
  httpClientSpy.put.and.returnValue(of([no]));
  httpClientSpy.post.and.returnValue(of([no]));
  httpClientSpy.delete.and.returnValue(of([no]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([NoService], (service: NoService) => {
    noService = service;
  }));

  it('NoService should be defined', () => {
    expect(noService).toBeTruthy();
  });

  it('should getNos using http request', () => {
    noService.getNos().subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getNo using http request', () => {
    noService.getNo(no.abreviatura).subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  // Exemplos com toPromise()
  it('should getNo using http request', async () => {
    resultado = await noService.getNo(no.abreviatura).toPromise();
    expect(resultado[0].abreviatura).toEqual(no.abreviatura);
  });

  it('should addNo using http request', () => {
    noService.addNo(no).subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateNo using http request', () => {
    noService.updateNo(no, "GAND").subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteNo using http request', () => {
    noService.deleteNo(no).subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should deleteNo using http request (with abreviatura)', () => {
    noService.deleteNo(no.abreviatura).subscribe(res => expect(res[0].abreviatura).toEqual("GAND"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

});
