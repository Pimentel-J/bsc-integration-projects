import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { TripulanteDetailComponent } from './tripulante-detail.component';
import { TripulanteService } from '../tripulante.service';
import { Tripulante } from '../tripulante';
import { of } from 'rxjs';

describe('TripulanteDetailComponent', () => {
  let component: TripulanteDetailComponent;
  let fixture: ComponentFixture<TripulanteDetailComponent>;
  let tripulanteService: TripulanteService;
  let tripulante: Tripulante;
  let routeMock = { snapshot: { paramMap: { get: () => { return "TRP123456" } } } };
  let spyOnConfirm: any;

  tripulante = {
    numeroMecanografico: "TRP123456",
    nome: "Miguel Gomes",
    dataNascimento: "02-03-1985",
    numeroCartaoCidadao: "12345678 4ZX6",
    nif: "123456789",
    numeroCartaConducao: "P-1234567",
    dataEmissaoLicencaConducao: "20-05-2003",
    dataValidadeLicencaConducao: "20-05-2053",
    tipoTripulante: "PT",
    dataEntradaEmpresa: "01-01-2020",
    dataSaidaEmpresa: null
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TripulanteDetailComponent
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
    tripulanteService = TestBed.inject(TripulanteService);

    fixture = TestBed.createComponent(TripulanteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tripulanteService, 'getTripulante').and.returnValue(of(tripulante));
    spyOn(tripulanteService, 'addTripulante').and.returnValue(of(tripulante));
    spyOn(tripulanteService, 'updateTripulante').and.returnValue(of(tripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create TripulanteDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getTripulante should call getTripulante', () => {
    component.getTripulante(tripulante.numeroMecanografico);
    expect(tripulanteService.getTripulante).toHaveBeenCalled();
  });

  it('create should call addTripulante', () => {
    component.create();
    expect(tripulanteService.addTripulante).toHaveBeenCalled();
  });

  it('save should call updateTripulante', () => {
    component.save();
    expect(tripulanteService.updateTripulante).toHaveBeenCalled();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(tripulanteService.addTripulante).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(tripulanteService.updateTripulante).toHaveBeenCalledTimes(0);
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

});
