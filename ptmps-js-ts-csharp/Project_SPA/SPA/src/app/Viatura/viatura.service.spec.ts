import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { ViaturaService } from './viatura.service';
import { Viatura } from './viatura';

describe('ViaturaServiceService', () => {
  let viaturaService: ViaturaService;
  let viatura: Viatura;

  viatura = {
    id: "PT",
    niv: "N1",
    tipoviatura: "TV",
    data_entrada_servico: "data"
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([viatura]));
  httpClientSpy.put.and.returnValue(of([viatura]));
  httpClientSpy.post.and.returnValue(of([viatura]));
  httpClientSpy.delete.and.returnValue(of([viatura]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ViaturaService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([ViaturaService], (service: ViaturaService) => {
    viaturaService = service;
  }));

  it('ViaturaService should be created', () => {
    expect(viaturaService).toBeTruthy();
  });

  it('should getViaturas using http request', () => {
    viaturaService.getViaturas().subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getViatura using http request', () => {
    viaturaService.getViatura(viatura.id).subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addViatura using http request', () => {
    viaturaService.addViatura(viatura).subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateViatura using http request', () => {
    viaturaService.updateViatura(viatura, "PT").subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteViatura using http request', () => {
    viaturaService.deleteViatura(viatura).subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should deleteViatura using http request (with id)', () => {
    viaturaService.deleteViatura(viatura.id).subscribe(res => expect(res[0].id).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should getViaturaData using http request', () => {
    viaturaService.getViaturaData("PT");
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should import using http request', () => {
    viaturaService.import();
    expect(httpClientSpy.post).toHaveBeenCalled();
  });
});
