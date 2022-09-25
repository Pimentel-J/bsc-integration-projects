import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { TipoviaturaService } from '../tipoviatura.service';
import { TpvSearchComponent } from './tpv-search.component';
import { Tipoviatura } from '../tipoviatura';
import { of } from 'rxjs';

describe('TpvSearchComponent', () => {
  let component: TpvSearchComponent;
  let fixture: ComponentFixture<TpvSearchComponent>;
  let tipoViaturaService: TipoviaturaService;
  let tipoViatura: Tipoviatura;

  let routeMock = { snapshot: { paramMap: { get: () => { return "BUS_GAS01" } } } };

  tipoViatura = {
    codigo: "BUS_GAS01",
    nome: "BUS_GAS01",
    tipoCombustivel: "GPL",
    autonomia: 30000,
    consumoMedio: 8.45,
    custoKm: 0.8,
    velocidadeMedia: 35,
    emissoesCO2: 700
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        TpvSearchComponent
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
    tipoViaturaService = TestBed.inject(TipoviaturaService);

    fixture = TestBed.createComponent(TpvSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(tipoViaturaService, 'searchTpv').and.returnValue(of([tipoViatura]));
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('search function', () => {
    component.search("BUS_GAS01");
  });
});
