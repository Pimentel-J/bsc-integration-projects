import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { BlocosComponent } from './blocos.component';
import { BlocoService } from '../bloco.service';
import { Bloco } from '../bloco';
import { of } from 'rxjs';


describe('BlocosComponent', () => {
  let component: BlocosComponent;
  let fixture: ComponentFixture<BlocosComponent>;
  let blocoService: BlocoService;
  let bloco: Bloco;

  let routeMock = { snapshot: { paramMap: { get: () => { return "Linha1" } } } };
  let spyOnConfirm: any;

  bloco = {
    codigo: "PT",
    startTime: 3600,
    endTime: 33200,
    servicoMotoristaId: "tripulante",
    servicoViaturaId: "viatura",
    viagens: []
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        BlocosComponent
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

    fixture = TestBed.createComponent(BlocosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(blocoService, 'getBlocos').and.returnValue(of([bloco]));
    spyOn(blocoService, 'deleteBloco').and.returnValue(of(bloco));
    spyOn(blocoService, 'import');

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });
  
  it('should create BlocosComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getBlocos should call getBlocos', () => {
    component.getBlocos();
    expect(blocoService.getBlocos).toHaveBeenCalled();
  });

  it('delete should call deleteBloco', () => {
    component.blocos.push(bloco);
    component.delete(bloco);
    expect(blocoService.deleteBloco).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(bloco);
    expect(blocoService.deleteBloco).toHaveBeenCalledTimes(0);
  });

  it('import should call import', () => {
    component.import();
    expect(blocoService.import).toHaveBeenCalled();
  });
});
