import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from
  '@angular/common/http';
import { Subject } from 'rxjs';
import { Observable } from 'rxjs';
import * as jwt_decode from 'jwt-decode';
import { User } from './models/user';
import { RegisterUser } from './models/RegisterUser';
import Config from '../config';
import { map } from 'rxjs/operators';

class Token { token: string };

@Injectable()
export class AuthenticationService {
  private authUrl = Config.apis.authPrefix + '/users/authenticate';
  private registerUrl = Config.apis.authPrefix + '/users/register';
  private usersUrl = Config.apis.authPrefix;
  
  //verificar o servidor:porta
  public userInfo: User;

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  authentication: Subject<User> = new Subject<User>();
  constructor(
    private http: HttpClient
  ) {
    this.userInfo = localStorage.userInfo;
  }
  
  
  login(user: string, password: string): Observable<boolean> {
    
    var loginDetails = {
      username: user,
      password: password
    };

    return new Observable<boolean>(observer => {
      this.http.post<User>(this.authUrl, JSON.stringify(loginDetails), this.httpOptions)
        .subscribe(data => {
          if (data.token) {
            const tokenDecoded = jwt_decode(data.token);
            this.userInfo = {
              token: data.token,
              tokenExp: tokenDecoded.exp,
              cliente: data.cliente,
              dataAdmin: data.dataAdmin,
              id: data.id
            }

            localStorage.userInfo = this.userInfo;

            this.authentication.next(this.userInfo);
            observer.next(true);
          } else {
            this.authentication.next(this.userInfo);
            observer.next(false);
          }
        },
          (err: HttpErrorResponse) => {
            if (err.error instanceof Error) {
              console.log("Client-side error occured.");
            } else {
              console.log("Server-side error occured.");
            }
            console.log(err);
            this.authentication.next(this.userInfo);
            observer.next(false);
          });

    });
  }

  logout() {
    this.userInfo = null;
    localStorage.removeItem('userInfo');
    this.authentication.next(this.userInfo);
  }

  register(user: RegisterUser) {
    return this.http.post(this.registerUrl, user);
  }

  delete(id: string) {
    var headers_object = { headers: new HttpHeaders().set("Authorization", "Bearer " + this.userInfo.token) };
    return this.http.delete(`${this.usersUrl}/users/${id}`, headers_object).pipe(map(x => {
      // auto logout if the logged in user deleted their own record
      if (id == this.userInfo.id) {
        this.logout();
      }
      console.log(x);
      return x;
    }));
  }

}