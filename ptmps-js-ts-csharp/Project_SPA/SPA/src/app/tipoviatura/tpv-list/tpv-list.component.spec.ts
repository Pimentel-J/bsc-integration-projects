import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { TpvListComponent } from './tpv-list.component';
import { TipoviaturaService } from '../tipoviatura.service';
import { Tipoviatura } from '../tipoviatura';
import { of } from 'rxjs';

describe('TpvListComponent', () => {
  let component: TpvListComponent;
  let fixture: ComponentFixture<TpvListComponent>;
  let tipoViaturaService: TipoviaturaService;
  let tipoViatura: Tipoviatura;
  let tiposviatura: Tipoviatura[] = [];

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
        TpvListComponent
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
    tipoViaturaService = TestBed.inject(TipoviaturaService);

    fixture = TestBed.createComponent(TpvListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tipoViaturaService, 'getTiposviatura').and.returnValue(of([tipoViatura]));
    spyOn(tipoViaturaService, 'deleteTipoviatura').and.returnValue(of(tipoViatura));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('getTipoTripulante should call getTiposviatura', () => {
    component.getTiposviatura();
    expect(tipoViaturaService.getTiposviatura).toHaveBeenCalled();
  });

  it('delete should call delete', () => {
    component.tiposviatura.push(tipoViatura);
    component.delete(tipoViatura);
    expect(tipoViaturaService.deleteTipoviatura).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(tipoViatura);
    expect(tipoViaturaService.deleteTipoviatura).toHaveBeenCalledTimes(0);
  });

});
