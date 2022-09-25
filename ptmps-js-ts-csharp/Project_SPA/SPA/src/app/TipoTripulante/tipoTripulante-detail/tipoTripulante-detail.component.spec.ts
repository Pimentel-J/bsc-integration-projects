import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { TipoTripulanteDetailComponent } from './tipoTripulante-detail.component';
import { TipoTripulanteService } from '../tipoTripulante.service';
import { TipoTripulante } from '../tipoTripulante';
import { of } from 'rxjs';

describe('TipoTripulanteDetailComponent', () => {
  let component: TipoTripulanteDetailComponent;
  let fixture: ComponentFixture<TipoTripulanteDetailComponent>;
  let tipoTripulanteService: TipoTripulanteService;
  let tipoTripulante: TipoTripulante;
  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;

  tipoTripulante = {
    codigo: "PT",
    descricao: "Teste"
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TipoTripulanteDetailComponent
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
    tipoTripulanteService = TestBed.inject(TipoTripulanteService);

    fixture = TestBed.createComponent(TipoTripulanteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tipoTripulanteService, 'getTipoTripulante').and.returnValue(of(tipoTripulante));
    spyOn(tipoTripulanteService, 'addTipoTripulante').and.returnValue(of(tipoTripulante));
    spyOn(tipoTripulanteService, 'updateTipoTripulante').and.returnValue(of(tipoTripulante));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create TipoTripulanteDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getTipoTripulante should call getTipoTripulante', () => {
    component.getTipoTripulante(tipoTripulante.codigo);
    expect(tipoTripulanteService.getTipoTripulante).toHaveBeenCalled();
  });

  it('create should call addTipoTripulante', () => {
    component.create();
    expect(tipoTripulanteService.addTipoTripulante).toHaveBeenCalled();
  });

  it('save should call updateTipoTripulante', () => {
    component.save();
    expect(tipoTripulanteService.updateTipoTripulante).toHaveBeenCalled();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(tipoTripulanteService.addTipoTripulante).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(tipoTripulanteService.updateTipoTripulante).toHaveBeenCalledTimes(0);
  });
});
