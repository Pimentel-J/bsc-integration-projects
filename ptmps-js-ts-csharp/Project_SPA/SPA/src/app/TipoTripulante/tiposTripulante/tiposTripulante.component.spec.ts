import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { TiposTripulanteComponent } from './tiposTripulante.component';
import { TipoTripulanteService } from '../tipoTripulante.service';
import { TipoTripulante } from '../tipoTripulante';
import { of } from 'rxjs';

describe('TiposTripulanteComponent', () => {
  let component: TiposTripulanteComponent;
  let fixture: ComponentFixture<TiposTripulanteComponent>;
  let tipoTripulanteService: TipoTripulanteService;
  let tipoTripulante: TipoTripulante;
  let tiposTripulante: TipoTripulante[] = [];
  
  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;
  
  tipoTripulante = {
    codigo: "PT",
    descricao: "Teste"
  };
  
  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TiposTripulanteComponent
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
    tipoTripulanteService = TestBed.inject(TipoTripulanteService);

    fixture = TestBed.createComponent(TiposTripulanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tipoTripulanteService, 'getTiposTripulante').and.returnValue(of([tipoTripulante]));
    spyOn(tipoTripulanteService, 'deleteTipoTripulante').and.returnValue(of(tipoTripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);

  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create TiposTripulanteComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getTipoTripulante should call getTipoTripulante', () => {
    component.getTiposTripulante();
    expect(tipoTripulanteService.getTiposTripulante).toHaveBeenCalled();
  });
  
    it('delete should call deleteTipoTripulante', () => {
      component.tiposTripulante.push(tipoTripulante);
      component.delete(tipoTripulante);
      expect(tipoTripulanteService.deleteTipoTripulante).toHaveBeenCalled();
    });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(tipoTripulante);
    expect(tipoTripulanteService.deleteTipoTripulante).toHaveBeenCalledTimes(0);
  });

});