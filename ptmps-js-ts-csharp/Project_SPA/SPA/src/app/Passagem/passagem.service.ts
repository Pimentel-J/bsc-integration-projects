import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import Config from '../../config';
import { Passagem } from './passagem';

@Injectable({
  providedIn: 'root'
})

export class PassagemService {

  private passagemUrl = Config.apis.mdvPrefix + 'passagens'; // URL
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private http: HttpClient,
    private messageService: MessageService) { }


  getPassagens(): Observable<Passagem[]> {
    return this.http.get<Passagem[]>(this.passagemUrl)
      .pipe(tap(_ => this.log('fetched Passagens')),
        catchError(this.handleError<Passagem[]>('getPassagens', []))
      );
  }

  getPassagensPontosTrocaDeViagem(viagemStr: string): Observable<Passagem[]> {
    var http_options = {
      params: {
        viagem: viagemStr,
        pontoTroca: "true"
      }
    };
    return this.http.get<Passagem[]>(this.passagemUrl, http_options)
      .pipe(tap(_ => this.log('fetched Passagens')),
        catchError(this.handleError<Passagem[]>('getPassagens', []))
      );
  }

  /** GET Viagem by id. Will 404 if id not found */
  getPassagem(idPassagem: string): Observable<Passagem> {
    const url = `${this.passagemUrl}/${idPassagem}`;
    return this.http.get<Passagem>(url).pipe(
      tap(_ => this.log(`fetched viagem idPassagem=${idPassagem}`)),
      catchError(this.handleError<Passagem>(`getPassagem idPassagem=${idPassagem}`))
    );
  }



  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  /* istanbul ignore next */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a Passagem message with the MessageService */
  private log(message: string) {
    this.messageService.add(`Passagem: ${message}`);
  }
}
