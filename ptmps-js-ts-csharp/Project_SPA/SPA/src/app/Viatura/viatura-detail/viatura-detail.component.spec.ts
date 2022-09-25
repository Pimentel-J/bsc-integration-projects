import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { ViaturaDetailComponent } from './viatura-detail.component';
import { ViaturaService } from '../viatura.service';
import { Viatura } from '../viatura';
import { of } from 'rxjs';

describe('ViaturaDetailComponent', () => {
  let component: ViaturaDetailComponent;
  let fixture: ComponentFixture<ViaturaDetailComponent>;
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
        ViaturaDetailComponent
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
    viaturaService = TestBed.inject(ViaturaService);

    fixture = TestBed.createComponent(ViaturaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(viaturaService, 'getViatura').and.returnValue(of(viatura));
    spyOn(viaturaService, 'getViaturaData');
    spyOn(viaturaService, 'updateViatura').and.returnValue(of(viatura));
    spyOn(viaturaService, 'addViatura').and.returnValue(of(viatura));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create ViaturaDetailComponent', () => {
    expect(component).toBeTruthy();
  });

  it('getViatura should call getViatura', () => {
    component.getViatura(viatura.id);
    expect(viaturaService.getViatura).toHaveBeenCalled();
  });

  it('create should call addViatura', () => {
    component.create();
    expect(viaturaService.addViatura).toHaveBeenCalled();
  });

  it('save should call updateViatura', () => {
    component.save();
    expect(viaturaService.updateViatura).toHaveBeenCalled();
  });

  it('test create else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.create();
    expect(viaturaService.addViatura).toHaveBeenCalledTimes(0);
  });

  it('test save else statement', () => {
    spyOnConfirm.and.callThrough();
    spyOnConfirm.and.returnValue(false);
    component.save();
    expect(viaturaService.updateViatura).toHaveBeenCalledTimes(0);
  });

  it('test ngOnInit else statement', () => {
    routeMock = { snapshot: { paramMap: { get: () => { return null } } } };
    expect(component).toBeTruthy();
  });

});
