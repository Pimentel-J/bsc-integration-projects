import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { ServicosTripulanteComponent } from './servicosTripulante.component';
import { ServicoTripulanteService } from '../servicoTripulante.service';
import { ServicoTripulante } from '../servicoTripulante';
import { of } from 'rxjs';

describe('ServicosTripulanteComponent', () => {
  let component: ServicosTripulanteComponent;
  let fixture: ComponentFixture<ServicosTripulanteComponent>;
  let servicoTripulanteService: ServicoTripulanteService;
  let servicoTripulante: ServicoTripulante;
  let servicosTripulante: ServicoTripulante[] = [];
  
  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;
  
  servicoTripulante = {
    codigoServicoTripulante: "PT"
  };
  
  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        ServicosTripulanteComponent
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
    servicoTripulanteService = TestBed.inject(ServicoTripulanteService);

    fixture = TestBed.createComponent(ServicosTripulanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(servicoTripulanteService, 'getServicosTripulante').and.returnValue(of([servicoTripulante]));
    spyOn(servicoTripulanteService, 'deleteServicoTripulante').and.returnValue(of(servicoTripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);

  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create ServicosTripulanteComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getServicoTripulante should call getServicoTripulante', () => {
    component.getServicosTripulante();
    expect(servicoTripulanteService.getServicosTripulante).toHaveBeenCalled();
  });
  
    it('delete should call deleteServicoTripulante', () => {
      component.servicosTripulante.push(servicoTripulante);
      component.delete(servicoTripulante);
      expect(servicoTripulanteService.deleteServicoTripulante).toHaveBeenCalled();
    });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(servicoTripulante);
    expect(servicoTripulanteService.deleteServicoTripulante).toHaveBeenCalledTimes(0);
  });

});