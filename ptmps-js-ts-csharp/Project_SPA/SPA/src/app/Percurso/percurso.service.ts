import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import { Percurso } from './percurso';
import Config from '../../config'
import { PercursoDTO } from './percursoDTO';
import { QueryIdaDTO } from './queryIdaDTO';


@Injectable({
  providedIn: 'root'
})

export class PercursoService {

  private percursoUrl = Config.apis.mdrPrefix + 'percursos';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getPercursos(): Observable<Percurso[]> {
    // TODO: send the message _after_ fetching the tiposviatura
    //this.messageService.add('TipoviaturaService: fetched tiposviatura');
    return this.http.get<Percurso[]>(this.percursoUrl)
      .pipe(
        tap(_ => this.log('fetched percursos')),
        catchError(this.handleError<Percurso[]>('getPercursos', []))
      );
  }

  getPercursosByLine(line:String): Observable<Percurso[]> {
    const url = this.percursoUrl+'/linha';
    const urllinha = `${url}/${line}`;
    console.log(line);
    return this.http.get<Percurso[]>(urllinha)
      .pipe(
        tap(_ => this.log('fetched percursos')),
        catchError(this.handleError<Percurso[]>('getPercursos', []))
      );
  }


  /** GET percurso by id. Will 404 if id not found */
  getPercurso(idPercurso: String): Observable<Percurso> {
    const url = `${this.percursoUrl}/${idPercurso}`; 
    return this.http.get<Percurso>(url).pipe(
      tap(_ => this.log(`fetched percurso idPercurso=${idPercurso}`)),
      catchError(this.handleError<Percurso>(`getPercurso idPercurso=${idPercurso}`))
    );
  }

  /** GET percurso by id. Will 404 if id not found */
  getPercursosIdaVolta(ida: string, linha: string): Observable<Percurso[]> {

    var http_options = {
      params: {
        ida: ida,
        linha: linha
      }
    };

    const url = `${this.percursoUrl}`;
    return this.http.get<Percurso[]>(url, http_options).pipe(
      tap(_ => this.log(`fetched percurso query=${http_options}`)),
      catchError(this.handleError<Percurso[]>(`getPercurso query=${http_options}`))
    );
  }

  ////////// Save methods //////////

  /** POST: add a new Percurso to the server */
  addPercurso(percurso: PercursoDTO): Observable<any> {
    return this.http.post<any>(this.percursoUrl, percurso, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<PercursoDTO>('addPercurso'))
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
     this.messageService.add(`PercursoService: ${message}`);
  }
}
