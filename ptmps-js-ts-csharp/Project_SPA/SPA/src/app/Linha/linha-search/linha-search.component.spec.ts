import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { LinhaSearchComponent } from './linha-search.component';
import { LinhaService } from '../linha.service';
import { Linha } from '../linha';
import { of } from 'rxjs';

describe('TpvSearchComponent', () => {
  let component: LinhaSearchComponent;
  let fixture: ComponentFixture<LinhaSearchComponent>;
  let linhaService: LinhaService;
  let linha: Linha;

  let routeMock = { snapshot: { paramMap: { get: () => { return "Linha1" } } } };

  linha = {
    codigo: "Linha1",
    nome: "teste",
    permissaoViatura: "BUS_ELECT",
    permissaoMotorista: "PTENG",
    noFinal: "GAND",
    cor: "green"
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        LinhaSearchComponent
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
    linhaService = TestBed.inject(LinhaService);

    fixture = TestBed.createComponent(LinhaSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(linhaService, 'searchLinha').and.returnValue(of([linha]));
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('search function', () => {
    component.search("Linha1");
  });
});
