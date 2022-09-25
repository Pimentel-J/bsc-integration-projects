import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { ViagemDetailComponent } from './viagem-detail.component';
import { ViagemService } from '../viagem.service';
import { Viagem } from '../viagem';
import { ViagemDTO } from '../viagemDTO';
import { Passagem } from '../../Passagem/passagem';
import { PassagemService } from '../../Passagem/passagem.service';
import { of } from 'rxjs';

xdescribe('ViagemDetailComponent', () => {
  let component: ViagemDetailComponent;
  let fixture: ComponentFixture<ViagemDetailComponent>;
  let viagemService: ViagemService;
  let viagem: Viagem;
  let viagemDTO: ViagemDTO;
  let passagem: Passagem;
  let passagemService: PassagemService;

  viagem = {
    id: "V1",
    percursoId: "P1",
    descritivo: "Teste",
    horaInicio: 3600,
    horaFim: 7200,
    passagens: [passagem]
  };

  viagemDTO = {
    id: "V1",
    servicoViaturaId: "SV1",
    percursoId: "P1",
    descritivo: "Teste",
    horaInicio: 3600,
    horaFim: 7200,
    passagens: ["P1"]
  };

  passagem = {
    id: "P1",
    viagemId: viagem,
    horaPassagem: 3600,
    abreviaturaNo: "N1"
  };

  let routeMock = { snapshot: { paramMap: { get: () => { return "id" } } } };
  let spyOnConfirm: any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        ViagemDetailComponent
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
    viagemService = TestBed.inject(ViagemService);
    passagemService = TestBed.inject(PassagemService);

    fixture = TestBed.createComponent(ViagemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(viagemService, 'getViagem').and.returnValue(of(viagemDTO));
    spyOn(passagemService, 'getPassagem').and.returnValue(of(passagem));
    // spyOn(linhaService, 'getLinhas').and.returnValue(of([linha]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('getViagem should call getViagem', () => {
    component.viagem = viagemDTO;
    component.selectPassagens = [passagem];
    component.getViagem(viagem.id);
    expect(viagemService.getViagem).toHaveBeenCalled();
  });

  // it('test ngOnInit else statement', () => {
  //   routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
  //   expect(component).toBeTruthy();
  // });

});
