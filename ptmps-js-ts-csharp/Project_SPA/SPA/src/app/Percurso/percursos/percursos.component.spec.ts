import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

import { PercursosComponent } from './percursos.component';
import { PercursoService } from '../percurso.service';
import { Percurso } from '../percurso';
import { of } from 'rxjs';

describe('PercursosComponent', () => {
  let component: PercursosComponent;
  let fixture: ComponentFixture<PercursosComponent>;
  let percursoService: PercursoService;
  let percursos: Percurso[] = [];

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        PercursosComponent
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    percursoService = TestBed.inject(PercursoService);

    fixture = TestBed.createComponent(PercursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    spyOn(percursoService, 'getPercursosByLine').and.returnValue(of(percursos));
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create PercursosComponent', () => {
    expect(component).toBeTruthy();
  });
  
  it('getPercursosByLine should call getPercursosByLine', () => {
    component.getPercursosByLine();
    expect(percursoService.getPercursosByLine).toHaveBeenCalled();
  });

  it('onChange should call getPercursosByLine', () => {
    component.onChange("teste");
    expect(percursoService.getPercursosByLine).toHaveBeenCalled();
  });
});
