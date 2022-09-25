import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';

import { TipoTripulante } from './tipoTripulante';
import Config from '../../config'

@Injectable({
  providedIn: 'root'
})

export class TipoTripulanteService {
  // URL to web api
  private tipoTripulanteUrl = Config.apis.mdrPrefix + 'tiposTripulante';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getTiposTripulante(): Observable<TipoTripulante[]> {
    // TODO: send the message _after_ fetching the tiposTripulante
    //this.messageService.add('TipoTripulanteService: fetched tiposTripulante');
    return this.http.get<TipoTripulante[]>(this.tipoTripulanteUrl)
      .pipe(
        tap(_ => this.log('fetched tiposTripulante')),
        catchError(this.handleError<TipoTripulante[]>('getTiposTripulante', []))
      );
  }

  /** GET TipoTripulante by codigo. Will 404 if codigo not found */
  getTipoTripulante(codigo: string): Observable<TipoTripulante> {
    const url = `${this.tipoTripulanteUrl}/${codigo}`;
    return this.http.get<TipoTripulante>(url).pipe(
      tap(_ => this.log(`fetched tipoTripulante codigo=${codigo}`)),
      catchError(this.handleError<TipoTripulante>(`getTipoTripulante codigo=${codigo}`))
    );
  }

  /* GET TiposTripulante whose codigo contains search term */
  searchTiposTripulante(term: string): Observable<TipoTripulante[]> {
    if (!term.trim()) {
      // if not search term, return empty linha array.
      return of([]);
    }
    return this.http.get<TipoTripulante[]>(`${this.tipoTripulanteUrl}/?codigo=${term}`).pipe(
      tap(x => x.length ?
        this.log(`found Tipo de Tripulante matching "${term}"`) :
        /* istanbul ignore next  */
        this.log(`no Tipo de Tripulante matching "${term}"`)),
      catchError(this.handleError<TipoTripulante[]>('searchTipoTripulante', []))
    );
  }

  ////////// Save methods //////////

  /** POST: add a new TipoTripulante to the server */
  addTipoTripulante(tipoTripulante: TipoTripulante): Observable<any> {
    return this.http.post<any>(this.tipoTripulanteUrl, tipoTripulante, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<TipoTripulante>('addTipoTripulante'))
    );
  }

  /**
   * PUT: update the TipoTripulante on the server
   * @param tipoTripulante 
   * @param codigo 
   */
  updateTipoTripulante(tipoTripulante: TipoTripulante, codigo: string): Observable<any> {
    const url = `${this.tipoTripulanteUrl}/${codigo}`;
    return this.http.put<any>(url, tipoTripulante, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateTipoTripulante'))
    );
  }

  /**
   * DELETE: delete the TipoTripulante from the server
   * @param tipoTripulante 
   */
  deleteTipoTripulante(tipoTripulante: TipoTripulante | string): Observable<TipoTripulante> {
    const codigo = typeof tipoTripulante === 'string' ? tipoTripulante : tipoTripulante.codigo;
    const url = `${this.tipoTripulanteUrl}/${codigo}`;
    
    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<TipoTripulante>('deleteTipoTripulante'))
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
      console.error(error.error.error); // log to console instead

      // Webpage messages log
      let message = `${operation} failed: ${error.error.error}`;
      this.log(message);

      // Alert popup
      alert(message);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a TipoTripulanteService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`TipoTripulanteService: ${message}`);
  }

}
