import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { LinhasComponent } from './linhas.component';
import { LinhaService } from '../linha.service';
import { Linha } from '../linha';
import { of } from 'rxjs';

describe('LinhasComponent', () => {
  let component: LinhasComponent;
  let fixture: ComponentFixture<LinhasComponent>;
  let linhaService: LinhaService;
  let linha: Linha;
  let linhas: Linha[] = [];

  let routeMock = { snapshot: { paramMap: { get: () => { return "Linha1" } } } };
  let spyOnConfirm: any;

  linha = {
    codigo: "Linha1",
    nome: "teste",
    permissaoViatura: "BUS_ELECT",
    permissaoMotorista: "PTENG",
    noFinal: "GAND",
    cor: "green"
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        LinhasComponent
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: routeMock
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    linhaService = TestBed.inject(LinhaService);

    fixture = TestBed.createComponent(LinhasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(linhaService, 'getLinhas').and.returnValue(of([linha]));
    spyOn(linhaService, 'deleteLinha').and.returnValue(of(linha));
    spyOn(linhaService, 'import');

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);

  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create LinhasComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getLinhas should call getLinhas', () => {
    component.getLinhas();
    expect(linhaService.getLinhas).toHaveBeenCalled();
  });

  it('delete should call deleteLinha', () => {
    component.linhas.push(linha);
    component.delete(linha);
    expect(linhaService.deleteLinha).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(linha);
    expect(linhaService.deleteLinha).toHaveBeenCalledTimes(0);
  });

  it('import should call import', () => {
    component.import();
    expect(linhaService.import).toHaveBeenCalled();
  });
});
