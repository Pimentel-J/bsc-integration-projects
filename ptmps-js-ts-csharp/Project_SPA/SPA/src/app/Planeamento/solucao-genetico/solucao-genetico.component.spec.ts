import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolucaoGeneticoComponent } from './solucao-genetico.component';

xdescribe('SolucaoGeneticoComponent', () => {
  let component: SolucaoGeneticoComponent;
  let fixture: ComponentFixture<SolucaoGeneticoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolucaoGeneticoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SolucaoGeneticoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
