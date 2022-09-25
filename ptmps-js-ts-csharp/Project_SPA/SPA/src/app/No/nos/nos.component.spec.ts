import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { NosComponent } from './nos.component';
import { NoService } from '../no.service';
import { No } from '../no';
import { of } from 'rxjs';

describe('NosComponent', () => {
  let component: NosComponent;
  let fixture: ComponentFixture<NosComponent>;
  let noService: NoService;
  let no: No;
  let nos: No[] = [];

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
        NosComponent
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
    noService = TestBed.inject(NoService);

    fixture = TestBed.createComponent(NosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(noService, 'getNos').and.returnValue(of([no]));
    spyOn(noService, 'deleteNo').and.returnValue(of(no));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create NosComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getNos should call getNos', () => {
    component.getNos();
    expect(noService.getNos).toHaveBeenCalled();
  });

  it('delete should call deleteNo', () => {
    component.nos.push(no);
    component.delete(no);
    expect(noService.deleteNo).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(no);
    expect(noService.deleteNo).toHaveBeenCalledTimes(0);
  });

});
