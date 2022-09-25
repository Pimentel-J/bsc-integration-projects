import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { TpvDetailComponent } from './tpv-detail.component';
import { TipoviaturaService } from '../tipoviatura.service';
import { Tipoviatura } from '../tipoviatura';
import { of } from 'rxjs';


describe('TpvDetailComponent', () => {
  let component: TpvDetailComponent;
  let fixture: ComponentFixture<TpvDetailComponent>;
  let tipoViaturaService: TipoviaturaService;
  let tipoViatura: Tipoviatura;

  let routeMock = { snapshot: { paramMap: { get: () => { return "BUS_GAS01" } } } };
  let spyOnConfirm: any;

  tipoViatura = {
    codigo: "BUS_GAS01",
    nome: "BUS_GAS01",
    tipoCombustivel: "GPL",
    autonomia: 30000,
    consumoMedio: 8.45,
    custoKm: 0.8,
    velocidadeMedia: 35,
    emissoesCO2: 700
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TpvDetailComponent
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
    tipoViaturaService = TestBed.inject(TipoviaturaService);
    
    fixture = TestBed.createComponent(TpvDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tipoViaturaService, 'getTipoviatura').and.returnValue(of(tipoViatura));
    spyOn(tipoViaturaService, 'addTipoviatura').and.returnValue(of(tipoViatura));
    spyOn(tipoViaturaService, 'updateTipoviatura').and.returnValue(of(tipoViatura));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create TpvDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getTipoviatura should call getTipoviatura', () => {
    component.getTipoviatura(tipoViatura.codigo);
    expect(tipoViaturaService.getTipoviatura).toHaveBeenCalled();
  });

  it('create should call addTipoviatura', () => {
    component.create();
    expect(tipoViaturaService.addTipoviatura).toHaveBeenCalled();
  });

  it('save should call updateTipoviatura', () => {
    component.save();
    expect(tipoViaturaService.updateTipoviatura).toHaveBeenCalled();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(tipoViaturaService.addTipoviatura).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(tipoViaturaService.updateTipoviatura).toHaveBeenCalledTimes(0);
  });
});
