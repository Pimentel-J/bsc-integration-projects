import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

import { ViagensComponent } from './viagens.component';
import { ViagemService } from '../viagem.service';
import { Viagem } from '../viagem';
import { ViagemDTO } from '../viagemDTO';
import { of } from 'rxjs';

describe('PercursosComponent', () => {
  let component: ViagensComponent;
  let fixture: ComponentFixture<ViagensComponent>;
  let viagemService: ViagemService;
  let viagem: Viagem;
  let viagensDTO: ViagemDTO[] = [];
  // let viagens: Viagem[] = [];

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        ViagensComponent
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    viagemService = TestBed.inject(ViagemService);

    fixture = TestBed.createComponent(ViagensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(viagemService, 'getViagens').and.returnValue(of(viagensDTO));
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create ViagensComponent', () => {
    expect(component).toBeTruthy();
  });
  
  it('getViagens should call getViagens', () => {
    component.getViagens();
    expect(viagemService.getViagens).toHaveBeenCalled();
  });
});
