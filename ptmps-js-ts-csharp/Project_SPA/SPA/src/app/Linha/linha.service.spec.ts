import { TestBed, inject } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

import { LinhaService } from './linha.service';
import { Linha } from './linha';

describe('LinhaService', () => {
  let linhaService: LinhaService;
  let linha: Linha;
  let resultado: any;

  linha = {
    codigo: "Linha1",
    nome: "teste",
    permissaoViatura: "BUS_ELECT",
    permissaoMotorista: "PTENG",
    noFinal: "GAND",
    cor: "green"
  };

  const httpClientSpy: jasmine.SpyObj<HttpClient> = jasmine.createSpyObj('httpClient', ['get', 'put', 'post', 'delete']);
  httpClientSpy.get.and.returnValue(of([linha]));
  httpClientSpy.put.and.returnValue(of([linha]));
  httpClientSpy.post.and.returnValue(of([linha]));
  httpClientSpy.delete.and.returnValue(of([linha]));

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LinhaService,
        {
          provide: HttpClient,
          useValue: httpClientSpy
        }
      ]
    }).compileComponents();
  });

  beforeEach(inject([LinhaService], (service: LinhaService) => {
    linhaService = service;
  }));

  it('Linha should be defined', () => {
    expect(linhaService).toBeTruthy();
  });

  it('should getLinhas using http request', () => {
    linhaService.getLinhas().subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getLinha using http request', () => {
    linhaService.getLinha(linha.codigo).subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  // Exemplos com toPromise()
  it('should searchLinha using http request', async () => {
    resultado = await linhaService.searchLinha(linha.codigo).toPromise();
    expect(resultado[0].codigo).toEqual(linha.codigo);
  });

  it('searchLinha should return empty array', async () => {
    resultado = await linhaService.searchLinha("").toPromise();
    expect(resultado).toEqual([]);
  });

  it('should addLinha using http request', () => {
    linhaService.addLinha(linha).subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

  it('should updateLinha using http request', () => {
    linhaService.updateLinha(linha, "Linha1").subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.put).toHaveBeenCalled();
  });

  it('should deleteLinha using http request', () => {
    linhaService.deleteLinha(linha).subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should deleteLinha using http request (with codigo)', () => {
    linhaService.deleteLinha(linha.codigo).subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.delete).toHaveBeenCalled();
  });

  it('should getPermissoesViaturas using http request', () => {
    linhaService.getPermissoesViaturas("Linha1").subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getPermissoesMotoristas using http request', () => {
    linhaService.getPermissoesMotoristas("Linha1").subscribe(res => expect(res[0].codigo).toEqual("Linha1"));
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should getLinhaData using http request', () => {
    linhaService.getLinhaData("Linha1");
    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('should import using http request', () => {
    linhaService.import();
    expect(httpClientSpy.post).toHaveBeenCalled();
  });

});
