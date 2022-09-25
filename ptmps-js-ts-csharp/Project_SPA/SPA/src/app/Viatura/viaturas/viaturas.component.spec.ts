import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { ViaturasComponent } from './viaturas.component';
import { ViaturaService } from '../viatura.service';
import { Viatura } from '../viatura';
import { of } from 'rxjs';

describe('ViaturasComponent', () => {
  let component: ViaturasComponent;
  let fixture: ComponentFixture<ViaturasComponent>;
  let viaturaService: ViaturaService;
  let viatura: Viatura;

  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;

  viatura = {
    id: "PT",
    niv: "N1",
    tipoviatura: "TV",
    data_entrada_servico: "data"
  };

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        ViaturasComponent
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
    viaturaService = TestBed.inject(ViaturaService);

    fixture = TestBed.createComponent(ViaturasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(viaturaService, 'getViaturas').and.returnValue(of([viatura]));
    spyOn(viaturaService, 'deleteViatura').and.returnValue(of(viatura));
    spyOn(viaturaService, 'import');

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('ViaturaService should be created', () => {
    expect(viaturaService).toBeTruthy();
  });

  it('getViaturas should call getViaturas', () => {
    component.getViaturas();
    expect(viaturaService.getViaturas).toHaveBeenCalled();
  });

  it('delete should call deleteViatura', () => {
    component.viaturas.push(viatura);
    component.delete(viatura);
    expect(viaturaService.deleteViatura).toHaveBeenCalled();
  });

  it('test else if statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.delete(viatura);
    expect(viaturaService.deleteViatura).toHaveBeenCalledTimes(0);
  });

  it('import should call import', () => {
    component.import();
    expect(viaturaService.import).toHaveBeenCalled();
  });
});
