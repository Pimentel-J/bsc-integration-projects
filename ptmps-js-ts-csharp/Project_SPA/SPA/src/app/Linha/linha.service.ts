import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import { Linha } from './linha';
import { TipoTripulante } from '../TipoTripulante/tipoTripulante';
import { Tipoviatura} from '../tipoviatura/tipoviatura';
import Config from '../../config'

@Injectable({
  providedIn: 'root'
})

export class LinhaService {

  private linhaUrl = Config.apis.mdrPrefix + 'linhas';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  import() {
    const url = this.linhaUrl+'/import';
    this.http.post<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)))
  }

  getLinhas(): Observable<Linha[]> {
    // TODO: send the message _after_ fetching the tiposviatura
    //this.messageService.add('TipoviaturaService: fetched tiposviatura');
    return this.http.get<Linha[]>(this.linhaUrl)
      .pipe(
        tap(_ => this.log('fetched linhas')),
        catchError(this.handleError<Linha[]>('getLinhas', []))
      );
  }

  getPermissoesViaturas(codigo: string): Observable<Tipoviatura[]>{
    const url = `${this.linhaUrl}/pv/${codigo}`;
    return this.http.get<Tipoviatura[]>(url).pipe(
      tap(_ => this.log(`fetched tipoviatura codigo=${codigo}`)),
      catchError(this.handleError<Tipoviatura[]>(`getTipoViatura codigo=${codigo}`))
    );
  }

  getPermissoesMotoristas(codigo: string): Observable<TipoTripulante[]>{
    const url = `${this.linhaUrl}/pm/${codigo}`;
    return this.http.get<TipoTripulante[]>(url).pipe(
      tap(_ => this.log(`fetched TipoTripulante codigo=${codigo}`)),
      catchError(this.handleError<TipoTripulante[]>(`getTipoTripulante codigo=${codigo}`))
    );
  }
  /** GET no by id. Will 404 if id not found */
  getLinha(codigo: string): Observable<Linha> {
    const url = `${this.linhaUrl}/${codigo}`;
    return this.http.get<Linha>(url).pipe(
      tap(_ => this.log(`fetched linha codigo=${codigo}`)),
      catchError(this.handleError<Linha>(`getLinha codigo=${codigo}`))
    );
  }

  getLinhaData(codigo: string) {
    const url = `${this.linhaUrl}/${codigo}`;
    console.log(this.http.get<Linha>(url));
  }


 /* getPermissoesMotorista(codigo: string): Observable<TipoTripulante> {
    var linha = this.getLinhaData(codigo);
    
    return this.http.get<TipoTripulante>(linha.).pipe(
      tap(_ => this.log(`fetched linha codigo=${codigo}`)),
      catchError(this.handleError<TipoTripulante>(`getLinha codigo=${codigo}`))
    );
  }*/

  ////////// Save methods //////////

  /** POST: add a new Linha to the server */
  addLinha(linha: Linha): Observable<any> {
    return this.http.post<any>(this.linhaUrl, linha, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<Linha>('addLinha'))
    );
  }

  ///** DELETE: delete the tipoviatura from the server */
  deleteLinha(linha: Linha | string): Observable<Linha> {
    const codigo = typeof linha === 'string' ? linha : linha.codigo;
    const url = `${this.linhaUrl}/${codigo}`;

    return this.http.delete<Linha>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted linha codigo=${codigo}`)),
      catchError(this.handleError<Linha>('deleteLinha'))
    );
  }

  /** PUT: update the hero on the server */
  updateLinha(linha: Linha, codigo: string): Observable<any> {
    const url = `${this.linhaUrl}/${codigo}`;
    return this.http.put<any>(url, linha, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateLinha'))
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

  /* GET linha whose name contains search term */
  searchLinha(term: string): Observable<Linha[]> {
    if (!term.trim()) {
      // if not search term, return empty linha array.
      return of([]);
    }
    return this.http.get<Linha[]>(`${this.linhaUrl}/?codigo=${term}`).pipe(
      tap(x => x.length ?
        this.log(`found linha matching "${term}"`) :
        /* istanbul ignore next  */
        this.log(`no linha matching "${term}"`)),
      catchError(this.handleError<Linha[]>('searchLinha', []))
    );
  }
  /** Log a TipoviaturaService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`LinhaService: ${message}`);
  }
}
