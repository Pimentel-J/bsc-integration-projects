import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { LinhaDetailComponent } from './linha-detail.component';
import { LinhaService } from '../linha.service';
import { Linha } from '../linha';
import { Tipoviatura } from '../../tipoviatura/tipoviatura';
import { TipoTripulante } from '../../TipoTripulante/tipoTripulante';
import { of } from 'rxjs';

describe('LinhaDetailComponent', () => {
  let component: LinhaDetailComponent;
  let fixture: ComponentFixture<LinhaDetailComponent>;
  let linhaService: LinhaService;
  let linha: Linha;
  let tipoTripulante: TipoTripulante;
  let tipoViatura: Tipoviatura;

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
        LinhaDetailComponent
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        FormsModule
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

    fixture = TestBed.createComponent(LinhaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(linhaService, 'getLinha').and.returnValue(of(linha));
    spyOn(linhaService, 'addLinha').and.returnValue(of(linha));
    spyOn(linhaService, 'getLinhaData');
    spyOn(linhaService, 'updateLinha').and.returnValue(of(linha));
    spyOn(linhaService, 'getPermissoesViaturas').and.returnValue(of([tipoViatura]));
    spyOn(linhaService, 'getPermissoesMotoristas').and.returnValue(of([tipoTripulante]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });
  it('should create LinhaDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getLinha should call getLinha', () => {
    component.getLinha(linha.codigo);
    expect(linhaService.getLinha).toHaveBeenCalled();
  });

  it('create should call addLinha', () => {
    component.create();
    expect(linhaService.addLinha).toHaveBeenCalled();
  });

  it('save should call updateLinha', () => {
    component.save();
    expect(linhaService.updateLinha).toHaveBeenCalled();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(linhaService.addLinha).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(linhaService.updateLinha).toHaveBeenCalledTimes(0);
  });

  it('getTiposViaturas should call getPermissoesViaturas', () => {
    component.getTiposViaturas(linha.permissaoViatura);
    expect(linhaService.getPermissoesViaturas).toHaveBeenCalled();
  });

  it('getTiposMotoristas should call getPermissoesMotoristas', () => {
    component.getTiposMotoristas(linha.permissaoMotorista);
    expect(linhaService.getPermissoesMotoristas).toHaveBeenCalled();
  });

});
