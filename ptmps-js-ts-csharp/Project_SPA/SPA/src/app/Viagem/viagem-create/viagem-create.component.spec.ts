import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { PercursoCreateComponent } from '../../Percurso/percurso-create/percurso-create.component';
import { PercursoService } from '../../Percurso/percurso.service';
import { Percurso } from '../../Percurso/percurso';
import { LinhaService } from 'src/app/Linha/linha.service';
import { Linha } from '../../Linha/linha';
import { of } from 'rxjs';


describe('PercursoCreateComponent', () => {
  let component: PercursoCreateComponent;
  let fixture: ComponentFixture<PercursoCreateComponent>;
  let percursoService: PercursoService;
  let percurso: Percurso;
  let linha: Linha;

  percurso = {
    idLinha: linha,
    idPercurso: "Path1",
    ida: true,
    segmentos: []
  };

  linha = {
    codigo: "Linha1",
    nome: "teste",
    permissaoViatura: "BUS_ELECT",
    permissaoMotorista: "PTENG",
    noFinal: "GAND",
    cor: "green"
  };
  
  let routeMock = { snapshot: { paramMap: { get: () => { return "Path1" } } } };
  let spyOnConfirm: any;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        PercursoCreateComponent
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
    percursoService = TestBed.inject(PercursoService);

    fixture = TestBed.createComponent(PercursoCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
    spyOn(percursoService, 'addPercurso').and.returnValue(of(percurso));
    // spyOn(percursoService, 'getLinhas').and.returnValue(of([linha]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create PercursoCreateComponent', () => {
    expect(component).toBeTruthy();
  });

  it('create should call addPercurso', () => {
    component.create();
    expect(percursoService.addPercurso).toHaveBeenCalled();
  });
  
  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(percursoService.addPercurso).toHaveBeenCalledTimes(0);
  });
});
