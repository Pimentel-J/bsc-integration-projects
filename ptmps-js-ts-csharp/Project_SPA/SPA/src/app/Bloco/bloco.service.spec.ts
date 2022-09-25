import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { BlocoService } from './bloco.service';
import { Bloco } from './bloco';
import { ViagemDTO } from '../Viagem/viagemDTO';

describe('BlocoService', () => {
  let blocoService: BlocoService;
  let bloco: Bloco;
  let viagemDTO: ViagemDTO;

  let resultado: any;

  bloco = {
    codigo: "PT",
    startTime: 3600,
    endTime: 33200,
    servicoMotoristaId: "tripulante",
    servicoViaturaId: "viatura",
    viagens: []
  };

  viagemDTO = {
    id: "PT",
    percursoId: null,
    servicoViaturaId: null,
    descritivo: null,
    horaInicio: null,
    horaFim: null,
    passagens: []
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([bloco]));
  httpClientSpy.put.and.returnValue(of([bloco]));
  httpClientSpy.post.and.returnValue(of([bloco]));
  httpClientSpy.delete.and.returnValue(of([bloco]));

  beforeEach(() => {
TestBed.configureTestingModule({
      providers: [BlocoService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([BlocoService], (service: BlocoService) => {
    blocoService = service;
  }));

  it('BlocoService should be created', () => {
    expect(blocoService).toBeTruthy();
  });

  it('should getBlocos using http request', () => {
    blocoService.getBlocos().subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getBloco using http request', () => {
    blocoService.getBloco(bloco.codigo).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addBloco using http request', () => {
    blocoService.addBloco(bloco).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateBloco using http request', () => {
    blocoService.updateBloco(bloco, "PT").subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteBloco using http request', () => {
    blocoService.deleteBloco(bloco).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should deleteBloco using http request (with codigo)', () => {
    blocoService.deleteBloco(bloco.codigo).subscribe(res => expect(res[0].codigo).toEqual("PT"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });
  
  it('should getBlocoData using http request', () => {
    blocoService.getBlocoData("PT");
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should import using http request', () => {
    blocoService.import();
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should getViagensServico using http request', () => {
    blocoService.getViagensServico(viagemDTO.id).subscribe(res => expect(res[0].servicoViaturaId).toEqual("viatura"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

});
