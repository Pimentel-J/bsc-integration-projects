import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../authentication.service';
import { HttpClient, HttpHeaders, HttpErrorResponse } from
  '@angular/common/http';
import * as $ from 'jquery';// import Jquery here  

@Component({ templateUrl: 'register.component.html' })
export class RegisterComponent implements OnInit {

  // Get the iframe element reference
  @ViewChild('iframe', { static: false }) iframe: ElementRef;

  userLicense = '';
  enableAccept: boolean;
  isLoading: boolean;

  form: FormGroup;
  loading = false;
  submitted = false;


  enabledAgreement: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthenticationService,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', Validators.required],
      cliente: [true],
      dataAdmin: [false],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.isLoading = true;
    /* Get the end user license */
    this.http.get('assets/politica-privacidade.html', {responseType: "text"})
      .subscribe((res: any) => {
        this.isLoading = false;
        console.log(res);
        // Get the iframe's document reference
        const doc = this.iframe.nativeElement.contentDocument || this.iframe.nativeElement.contentWindow;

        // Open the doc and add html in it
        doc.open();
        doc.write(res);
        doc.close();
        const self = this;

        // Add scroll event
        doc.addEventListener('scroll', function (event) {
          console.log('event: ', event);
          const iframe = self.iframe.nativeElement;
          if (Math.ceil($(iframe.contentWindow).scrollTop()) === $(iframe.contentWindow).height() - $(iframe.contentWindow)[0].innerHeight) {
            // Set true
            self.enableAccept = true;
          }
        }, false);
      });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;


    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.authService.register(this.form.value)
      .pipe(first())
      .subscribe({
        next: () => {
          alert('Registo bem sucedido');
          this.router.navigate(['../login'], { relativeTo: this.route });
        },
        error: error => {
          alert(error);
          this.loading = false;
        }
      });
  }
}
