import { Component, ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { User } from './models/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'rideAIT';
  subscriptionAuth: Subscription;
  userInfo: User;
  constructor(
    private authenticationService: AuthenticationService,
    private cdr: ChangeDetectorRef) { }
  ngOnInit() {
    this.userInfo = this.authenticationService.userInfo;
    this.subscriptionAuth =
      this.authenticationService.authentication.subscribe((userInfo) => {
        this.userInfo = userInfo;
        console.log(userInfo);
        this.cdr.detectChanges();
      });
  }

  direitoAoEsquecimento() {
    if (confirm("Tem a certeza que deseja prosseguir? Todos os seus dados irão ser apagados")) {
      console.log(this.userInfo.id);
      this.authenticationService.delete(this.userInfo.id).subscribe(()=>alert("Dados Apagados com sucesso"));  
    }
    
  }

  
  ngOnDestroy() {
    this.subscriptionAuth.unsubscribe();
  }
}
