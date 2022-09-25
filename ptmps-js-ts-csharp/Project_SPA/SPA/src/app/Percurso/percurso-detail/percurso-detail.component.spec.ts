import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

import { PercursoDetailComponent } from './percurso-detail.component';
import { PercursoService } from '../percurso.service';
import { Percurso } from '../percurso';
import { LinhaService } from 'src/app/Linha/linha.service';
import { Linha } from '../../Linha/linha';
import { of } from 'rxjs';

xdescribe('PercursoDetailComponent', () => {
  let component: PercursoDetailComponent;
  let fixture: ComponentFixture<PercursoDetailComponent>;
  let percursoService: PercursoService;
  let percurso: Percurso;
  let linhaService: LinhaService;
  let linha: Linha;
  let criarPercurso: boolean = true;
  let editable: boolean = false;

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
    await TestBed.configureTestingModule({
      declarations: [
        PercursoDetailComponent
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        FormsModule,
        Location
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
    
    fixture = TestBed.createComponent(PercursoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
    spyOn(percursoService, 'getPercurso').and.returnValue(of(percurso));
    // spyOn(linhaService, 'getLinhas').and.returnValue(of([linha]));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
      expect(component).toBeTruthy();
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

  it('getPercurso should call getPercurso', () => {
    component.getPercurso(percurso.idPercurso);
    expect(percursoService.getPercurso).toHaveBeenCalled();
  });
});
