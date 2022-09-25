import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { PercursoService } from './percurso.service';
import { Percurso } from './percurso';
import { PercursoDTO } from './percursoDTO';
import { Segmento } from '../Segmento/segmento';
import { SegmentoDTO } from '../Segmento/segmentoDTO';
import { No } from '../No/no';
import { Linha } from '../Linha/linha';

describe('PercursoService', () => {
  let percursoService: PercursoService;
  let percurso: Percurso;
  let percursoDTO: PercursoDTO;
  let segmento: Segmento;
  let segmentoDTO: SegmentoDTO;
  let no: No;
  let linha: Linha;
  let resultado: any;

  percurso = {
    idLinha: linha,
    idPercurso: "Path1",
    ida: true,
    segmentos: [segmento]
  };

  percursoDTO = {
    linha: "Linha1",
    idPercurso: "Path1",
    ida: true,
    segmentos: [segmentoDTO]
  };

  linha = {
    codigo: "Linha1",
    nome: "teste",
    permissaoViatura: "BUS_ELECT",
    permissaoMotorista: "PTENG",
    noFinal: "GAND",
    cor: "green"
  };

  no = {
    abreviatura: "GAND",
    nome: "Gandra",
    latitude: 41,
    longitude: 8,
    estacaoRecolha: false,
    pontoRendicao: false,
    modeloMapa: ""
  };

  segmento = {
    _id: "asd",
    ordem: 1,
    noOrigem: no,
    noDestino: no,
    duracao: 600,
    distancia: 500
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([percurso]));
  httpClientSpy.put.and.returnValue(of([percurso]));
  httpClientSpy.post.and.returnValue(of([percurso]));
  httpClientSpy.delete.and.returnValue(of([percurso]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PercursoService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([PercursoService], (service: PercursoService) => {
    percursoService = service;
  }));

  it('PercursoService should be created', () => {
    expect(percursoService).toBeTruthy();
  });
  
  it('should getPercursos using http request', () => {
    percursoService.getPercursos().subscribe(res => expect(res[0].idPercurso).toEqual("Path1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getPercurso using http request', () => {
    percursoService.getPercurso(percurso.idPercurso).subscribe(res => expect(res[0].idPercurso).toEqual("Path1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getPercursosByLine using http request', () => {
    percursoService.getPercursosByLine(linha.codigo).subscribe(res => expect(res[0].idPercurso).toEqual("Path1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getPercursosIdaVolta using http request', () => {
    percursoService.getPercursosIdaVolta("true", linha.codigo).subscribe(res => expect(res[0].idPercurso).toEqual("Path1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should addPercurso using http request', () => {
    percursoService.addPercurso(percursoDTO).subscribe(res => expect(res[0].idPercurso).toEqual("Path1"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

});
