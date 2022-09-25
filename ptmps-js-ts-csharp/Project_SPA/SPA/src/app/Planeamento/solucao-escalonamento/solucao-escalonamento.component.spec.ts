import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolucaoEscalonamentoComponent } from './solucao-escalonamento.component';

xdescribe('SolucaoEscalonamentoComponent', () => {
  let component: SolucaoEscalonamentoComponent;
  let fixture: ComponentFixture<SolucaoEscalonamentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolucaoEscalonamentoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SolucaoEscalonamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
