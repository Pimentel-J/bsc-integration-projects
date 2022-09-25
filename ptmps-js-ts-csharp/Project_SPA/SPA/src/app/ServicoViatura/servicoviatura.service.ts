import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';
import { ServicoViatura } from './servicoviatura';
import Config from '../../config'

@Injectable({
  providedIn: 'root'
})
export class ServicoViaturaService {

  private servicoviaturaUrl = Config.apis.mdvPrefix + 'servicosviatura';  // URL to web api
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }


  getServicoViaturas(): Observable<ServicoViatura[]> {
    // TODO: send the message _after_ fetching the tiposviatura
    //this.messageService.add('TipoviaturaService: fetched tiposviatura');
    return this.http.get<ServicoViatura[]>(this.servicoviaturaUrl)
      .pipe(
        tap(_ => this.log('fetched servicos viatura')),
        catchError(this.handleError<ServicoViatura[]>('getServicoViaturas', []))
      );
  }

    /** GET percurso by id. Will 404 if id not found */
    getServicoViatura(idServViat: string): Observable<ServicoViatura> {
      const url = `${this.servicoviaturaUrl}/${idServViat}`;
      return this.http.get<ServicoViatura>(url).pipe(
        tap(_ => this.log(`fetched servicos viatura idServico=${idServViat}`)),
        catchError(this.handleError<ServicoViatura>(`getServico idServico=${idServViat}`))
      );
    }


   ////////// Save methods //////////
  /** POST: add a new viatura to the server */
  addServicoViatura(sv: ServicoViatura): Observable<any> {
    console.log('servico viatura',sv);
    console.log(this.servicoviaturaUrl);
  //  console.log(this.httpOptions);
     return this.http.post<any>(this.servicoviaturaUrl, sv, this.httpOptions)
      .pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<ServicoViatura>('addServicoViatura'))
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

  /** Log a Percurso message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ServiceViaturaService: ${message}`);
  }

}
