import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { TripulantesComponent } from './tripulantes.component';
import { TripulanteService } from '../tripulante.service';
import { Tripulante } from '../tripulante';
import { TipoTripulanteService } from '../../TipoTripulante/tipoTripulante.service';
import { TipoTripulante } from '../../TipoTripulante/tipoTripulante';
import { of } from 'rxjs';

describe('TripulanteComponent', () => {
  let component: TripulantesComponent;
  let fixture: ComponentFixture<TripulantesComponent>;
  let tripulanteService: TripulanteService;
  let tripulante: Tripulante;
  let tipoTripulanteService: TipoTripulanteService;
  let tipoTripulante: TipoTripulante;
  let tiposTripulante: TipoTripulante[] = [];

  let routeMock = { snapshot: { paramMap: { get: () => { return "TRP123456" } } } };
  let spyOnConfirm: any;

  tipoTripulante = {
    codigo: "PTFR",
    descricao: "Teste"
  };

  tripulante = {
    numeroMecanografico: "TRP123456",
    nome: "Miguel Gomes",
    dataNascimento: "02-03-1985",
    numeroCartaoCidadao: "12345678 4ZX6",
    nif: "123456789",
    numeroCartaConducao: "P-1234567",
    dataEmissaoLicencaConducao: "20-05-2003",
    dataValidadeLicencaConducao: "20-05-2053",
    tipoTripulante: tipoTripulante.codigo,
    dataEntradaEmpresa: "01-01-2020",
    dataSaidaEmpresa: null
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TripulantesComponent
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
    tripulanteService = TestBed.inject(TripulanteService);

    fixture = TestBed.createComponent(TripulantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tripulanteService, 'getTripulantes').and.returnValue(of([tripulante]));
    spyOn(tripulanteService, 'deleteTripulante').and.returnValue(of(tripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);

  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create TripulanteComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getTripulante should call getTripulante', () => {
    component.getTripulantes();
    expect(tripulanteService.getTripulantes).toHaveBeenCalled();
  });

  it('delete should call deleteTripulante', () => {
    component.tripulantes.push(tripulante);
    component.delete(tripulante);
    expect(tripulanteService.deleteTripulante).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(tripulante);
    expect(tripulanteService.deleteTripulante).toHaveBeenCalledTimes(0);
  });

});