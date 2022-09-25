import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { PassagemDetailComponent } from './passagem-detail.component';
import { PassagemService } from '../passagem.service';
import { Passagem } from '../passagem';

xdescribe('PassagemDetailComponent', () => {
  let component: PassagemDetailComponent;
  let fixture: ComponentFixture<PassagemDetailComponent>;
  let passagemService: PassagemService;
  let passagem: Passagem;

  let routeMock = { snapshot: { paramMap: { get: () => { return "PT" } } } };
  let spyOnConfirm: any;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      declarations: [
        PassagemDetailComponent
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
    passagemService = TestBed.inject(PassagemService);

    fixture = TestBed.createComponent(PassagemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    // spyOn(passagemService, 'getPassagem').and.returnValue(of(passagem));

    spyOnConfirm = spyOn(window, 'confirm').and.returnValue(true);
  });

  afterEach(() => {
    TestBed.resetTestingModule();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
