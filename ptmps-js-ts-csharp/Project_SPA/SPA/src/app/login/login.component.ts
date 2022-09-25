import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  error = '';
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private spinner: NgxSpinnerService) { }
  ngOnInit() {
    this.authenticationService.logout();
    this.activatedRoute.params.subscribe(params => {
      if (params['u'] !== undefined) {
        ;
        this.error = 'O seu perfil nao permite aceder a este menu';
      }
    });
  }
  login() {
    this.loading = true;
    this.spinner.show();
    this.authenticationService.login(this.model.username,
      this.model.password)
      .subscribe(result => {
        this.loading = false;
        if (result === true) {
          this.router.navigate(['/']); this.spinner.hide()
        } else {
          this.error = 'Username ou password incorretos'; this.spinner.hide()
        }
      });
  }
}