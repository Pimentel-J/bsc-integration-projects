import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { ServicoTripulanteDetailComponent } from './servicoTripulante-detail.component';
import { ServicoTripulanteService } from '../servicoTripulante.service';
import { ServicoTripulante } from '../servicoTripulante';
import { of } from 'rxjs';

describe('ServicoTripulanteDetailComponent', () => {
  let component: ServicoTripulanteDetailComponent;
  let fixture: ComponentFixture<ServicoTripulanteDetailComponent>;
  let servicoTripulanteService: ServicoTripulanteService;
  let servicoTripulante: ServicoTripulante;
  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;

  servicoTripulante = {
    codigoServicoTripulante: "PT"
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        ServicoTripulanteDetailComponent
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
    servicoTripulanteService = TestBed.inject(ServicoTripulanteService);

    fixture = TestBed.createComponent(ServicoTripulanteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(servicoTripulanteService, 'getServicoTripulante').and.returnValue(of(servicoTripulante));
    spyOn(servicoTripulanteService, 'addServicoTripulante').and.returnValue(of(servicoTripulante));
    // spyOn(servicoTripulanteService, 'updateServicoTripulante').and.returnValue(of(servicoTripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create ServicoTripulanteDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getServicoTripulante should call getServicoTripulante', () => {
    component.getServicoTripulante(servicoTripulante.codigoServicoTripulante);
    expect(servicoTripulanteService.getServicoTripulante).toHaveBeenCalled();
  });

  it('create should call addServicoTripulante', () => {
    component.create();
    expect(servicoTripulanteService.addServicoTripulante).toHaveBeenCalled();
  });

  // it('save should call updateServicoTripulante', () => {
  //   component.save();
  //   expect(servicoTripulanteService.updateServicoTripulante).toHaveBeenCalled();
  // });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(servicoTripulanteService.addServicoTripulante).toHaveBeenCalledTimes(0);
  });

  // it('test save else statement', () => {
  //   spyOnConfirm.and.callThrough();
  //   spyOnConfirm.and.returnValue(false);
  //   component.save();
  //   expect(servicoTripulanteService.updateServicoTripulante).toHaveBeenCalledTimes(0);
  // });
});
