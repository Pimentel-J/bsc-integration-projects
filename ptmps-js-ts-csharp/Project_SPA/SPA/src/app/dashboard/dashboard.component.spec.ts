import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';


import { DashboardComponent } from './dashboard.component';

xdescribe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let linhaService;
  let getLinhasSpy;


  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should display "Top Linhas" as headline', () => {
    expect(fixture.nativeElement.querySelector('h3').textContent).toEqual('Top Linhas');
  });

  it('should call heroService', waitForAsync(() => {
       expect(getLinhasSpy.calls.any()).toBe(true);
     }));

  it('should display 4 links', waitForAsync(() => {
       expect(fixture.nativeElement.querySelectorAll('a').length).toEqual(4);
     }));
});
