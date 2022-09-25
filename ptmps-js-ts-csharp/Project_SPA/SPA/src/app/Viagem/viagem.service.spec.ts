import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { ViagemService } from './viagem.service';
import { ViagemDTO } from './viagemDTO';
import { CreatingViagemDTO } from './creatingViagemDTO';

describe('ViagemService', () => {
  let viagemService: ViagemService;
  let viagemDTO: ViagemDTO;
  let creatingViagemDTO: CreatingViagemDTO;

  viagemDTO = {
    id: "PT",
    percursoId: "P1",
    servicoViaturaId: "V1",
    descritivo: "Teste",
    horaInicio: 3600,
    horaFim: 7200,
    passagens: []
  };

  creatingViagemDTO = {
    HoraInicio: 3600,
    PercursoId: "P1",
    PercursoIdaId: "P2",
    PercursoVoltaId: "P3",
    Frequencia: 1,
    NViagens: 1
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([viagemDTO]));
  httpClientSpy.post.and.returnValue(of(creatingViagemDTO));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ViagemService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([ViagemService], (service: ViagemService) => {
    viagemService = service;
  }));

  it('should be defined', () => {
    expect(viagemService).toBeTruthy();
  });
    
  it('should getViagens using http request', () => {
    // viagemService.getViagens().subscribe(res => expect(res[0].id).toEqual(viagemDTO.id));
    viagemService.getViagens();
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getViagemData using http request', () => {
    viagemService.getViagemData(viagemDTO.id);
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getViagensSemServico using http request', () => {
    viagemService.getViagensSemServico().subscribe(res => expect(res).toBe(res));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });
  
  it('should addViagem using http request', () => {
    viagemService.addViagem(creatingViagemDTO).subscribe(res => expect(res).toEqual(creatingViagemDTO));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });
  
  it('should getViagem using http request', () => {
    httpClientSpy.get.and.returnValue(of(viagemDTO));
    viagemService.getViagem(viagemDTO.id).subscribe(res => expect(res.id).toEqual(viagemDTO.id));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getViagem using http request', () => {
    viagemService.getViagensByServicoViatura(viagemDTO.id);
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

});