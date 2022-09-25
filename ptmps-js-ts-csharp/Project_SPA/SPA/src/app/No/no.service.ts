import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import { No } from './no';
import Config from '../../config'


@Injectable({
  providedIn: 'root'
})

export class NoService {

  private noUrl = Config.apis.mdrPrefix + 'nos';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getNos(): Observable<No[]> {
    // TODO: send the message _after_ fetching the tiposviatura
    //this.messageService.add('TipoviaturaService: fetched tiposviatura');
    return this.http.get<No[]>(this.noUrl)
      .pipe(
        tap(_ => this.log('fetched nos')),
        catchError(this.handleError<No[]>('getNos', []))
      );
  }

  ///** GET hero by id. Return `undefined` when id not found */
  //getTipoviaturaNo404<Data>(codigo: string): Observable<Tipoviatura> {
  //  const url = `${this.tipoviaturaUrl}/?id=${codigo}`;
  //  return this.http.get<Tipoviatura[]>(url)
  //    .pipe(
  //      map(tiposviatura => tiposviatura[0]), // returns a {0|1} element array
  //      tap(h => {
  //        const outcome = h ? `fetched` : `did not find`;
  //        this.log(`${outcome} tipoviatura id=${codigo}`);
  //      }),
  //      catchError(this.handleError<Tipoviatura>(`getTipoviatura id=${codigo}`))
  //    );
  //}



  /** GET no by id. Will 404 if id not found */
  getNo(abreviatura: string): Observable<No> {
    const url = `${this.noUrl}/${abreviatura}`;
    return this.http.get<No>(url).pipe(
      tap(_ => this.log(`fetched no abreviatura=${abreviatura}`)),
      catchError(this.handleError<No>(`getNo abreviatura=${abreviatura}`))
    );
  }


  ////////// Save methods //////////

  /** POST: add a new No to the server */
  addNo(no: No): Observable<any> {
    return this.http.post<any>(this.noUrl, no, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<No>('addNo'))
    );
  }

  /** DELETE: delete the tipoviatura from the server */
  deleteNo(no: No | string): Observable<No> {
    const abreviatura = typeof no === 'string' ? no : no.abreviatura;
    const url = `${this.noUrl}/${abreviatura}`;

    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<No>('deleteNo'))
    );
  }

  /** PUT: update the hero on the server */
  updateNo(no: No, abreviatura: string): Observable<any> {
    const url = `${this.noUrl}/${abreviatura}`;
    return this.http.put<any>(url, no, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateNo'))
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

  /** Log a TipoviaturaService message with the MessageService */
  private log(message: string) {
     this.messageService.add(`NoService: ${message}`);
  }
}
