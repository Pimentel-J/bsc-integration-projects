import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { NoDetailComponent } from './no-detail.component';
import { NoService } from '../no.service';
import { No } from '../no';
import { of } from 'rxjs';

describe('NoDetailComponent', () => {
  let component: NoDetailComponent;
  let fixture: ComponentFixture<NoDetailComponent>;
  let noService: NoService;
  let no: No;

  let routeMock = { snapshot: { paramMap: { get: () => { return "GAND" } } } };
  let spyOnConfirm: any;

  no = {
    abreviatura: "GAND",
    nome: "Gandra",
    latitude: 41,
    longitude: 8,
    estacaoRecolha: false,
    pontoRendicao: false,
    modeloMapa: ""
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        NoDetailComponent
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
    noService = TestBed.inject(NoService);

    fixture = TestBed.createComponent(NoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(noService, 'getNo').and.returnValue(of(no));
    spyOn(noService, 'addNo').and.returnValue(of(no));
    spyOn(noService, 'updateNo').and.returnValue(of(no));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create NoDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getNo should call getNo', () => {
    component.getNo(no.abreviatura);
    expect(noService.getNo).toHaveBeenCalled();
  });

  it('create should call addTipoTripulante', () => {
    component.create();
    expect(noService.addNo).toHaveBeenCalled();
  });

  it('save should call updateTipoTripulante', () => {
    component.save();
    expect(noService.updateNo).toHaveBeenCalled();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(noService.addNo).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(noService.updateNo).toHaveBeenCalledTimes(0);
  });
});
