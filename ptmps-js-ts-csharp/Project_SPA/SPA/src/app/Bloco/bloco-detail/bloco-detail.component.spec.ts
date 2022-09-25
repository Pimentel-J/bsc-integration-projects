import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

import { BlocoDetailComponent } from './bloco-detail.component';
import { BlocoService } from '../bloco.service';
import { Bloco } from '../bloco';
import { ViagemDTO } from '../../Viagem/viagemDTO';
import { of } from 'rxjs';

describe('BlocoDetailComponent', () => {
  let component: BlocoDetailComponent;
  let fixture: ComponentFixture<BlocoDetailComponent>;
  let blocoService: BlocoService;
  let bloco: Bloco;
  let viagemDTO: ViagemDTO;

  let routeMock = { snapshot: { paramMap: { get: () => { return "Linha1" } } } };
  let spyOnConfirm: any;

  bloco = {
    codigo: "PT",
    startTime: 3600,
    endTime: 33200,
    servicoMotoristaId: null,
    servicoViaturaId: null,
    viagens: []
  };

  viagemDTO = {
    id: "PT",
    percursoId: null,
    servicoViaturaId: null,
    descritivo: null,
    horaInicio: null,
    horaFim: null,
    passagens: []
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        BlocoDetailComponent
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
    blocoService = TestBed.inject(BlocoService);

    fixture = TestBed.createComponent(BlocoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(blocoService, 'addBloco').and.returnValue(of(bloco));
    spyOn(blocoService, 'getBloco').and.returnValue(of(bloco));
    spyOn(blocoService, 'getBlocoData');
    spyOn(blocoService, 'updateBloco').and.returnValue(of(bloco));
    spyOn(blocoService, 'getViagensServico').and.returnValue(of([viagemDTO]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create BlocoDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getBloco should call getBloco', () => {
    component.getBloco(bloco.codigo);
    expect(blocoService.getBloco).toHaveBeenCalled();
  });

  it('create should call addBloco', () => {
    component.create();
    expect(blocoService.addBloco).toHaveBeenCalled();
  });

  it('save should call updateBloco', () => {
    component.save();
    expect(blocoService.updateBloco).toHaveBeenCalled();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(blocoService.addBloco).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(blocoService.updateBloco).toHaveBeenCalledTimes(0);
  });

  it('should addViagem', () => {
    component.addViagem();
    expect(bloco.viagens[0]).toBeUndefined();
  });

});
