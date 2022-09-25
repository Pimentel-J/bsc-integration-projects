import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { SvListComponent } from './sv-list.component';
import { ServicoViatura } from '../servicoviatura';
import { ServicoViaturaService } from '../servicoviatura.service';

import { of } from 'rxjs';

describe('SvListComponent', () => {
  let component: SvListComponent;
  let fixture: ComponentFixture<SvListComponent>;
  let servicoServicoViatura: ServicoViaturaService;
  let servicoViatura: ServicoViatura;

  let routeMock = { snapshot: { paramMap: { get: () => { return "TRP123456" } } } };
  let spyOnConfirm: any;

  servicoViatura = {
    id: "PT",
    viaturaId: "Teste",
    viagens: []
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        SvListComponent
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
    servicoServicoViatura = TestBed.inject(ServicoViaturaService);

    fixture = TestBed.createComponent(SvListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(servicoServicoViatura, 'getServicoViaturas').and.returnValue(of([servicoViatura]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('getServicoViatura should call get getServicosViatura', () => {
    component.getServicosViatura();
    expect(servicoServicoViatura.getServicoViaturas).toHaveBeenCalled();
  });

});
