import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

import { SvDetailComponent } from './sv-detail.component';
import { ServicoViatura } from '../servicoviatura';
import { ServicoViaturaService } from '../servicoviatura.service';
import { ViagemDTO } from '../../Viagem/viagemDTO';

import { of } from 'rxjs';

describe('SvDetailComponent', () => {
  let component: SvDetailComponent;
  let fixture: ComponentFixture<SvDetailComponent>;
  let servicoServicoViatura: ServicoViaturaService;
  let servicoViatura: ServicoViatura;

  let routeMock = { snapshot: { paramMap: { get: () => { return "SV1" } } } };
  let spyOnConfirm: any;

  servicoViatura = {
    id: "PT",
    viaturaId: "Teste",
    viagens: ["PT"]
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        SvDetailComponent
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

    fixture = TestBed.createComponent(SvDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(servicoServicoViatura, 'addServicoViatura').and.returnValue(of(servicoViatura));
    spyOn(servicoServicoViatura, 'getServicoViatura').and.returnValue(of(servicoViatura));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('create should not call addServicoViatura', () => {
    component.create();
    expect(servicoServicoViatura.addServicoViatura).toHaveBeenCalledTimes(0);
  });

  it('create should call addServicoViatura', () => {
    component.servicoViatura = servicoViatura;
    component.create();
    expect(servicoServicoViatura.addServicoViatura).toHaveBeenCalled();
  });

  it('create should call getServicoViatura', () => {
    component.getServicoViatura(servicoViatura.id);
    expect(servicoServicoViatura.getServicoViatura).toHaveBeenCalled();
  });

  it('test create else statement', () => {
    component.servicoViatura = servicoViatura;
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(servicoServicoViatura.addServicoViatura).toHaveBeenCalledTimes(0);
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

});
