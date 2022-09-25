import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import {ViagemDTO} from '../Viagem/viagemDTO';
import { MessageService } from '../message.service';

import { Bloco } from './bloco';
import Config from '../../config'


@Injectable({
  providedIn: 'root'
})

export class BlocoService {

  private blocoUrl = Config.apis.mdvPrefix + 'blocos';  // URL to web api
  private vig= this.blocoUrl +'/Viagens';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  import() {
    const url = this.blocoUrl+'/import';
    this.http.post<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)))
  }

  getBlocos(): Observable<Bloco[]> {
    return this.http.get<Bloco[]>(this.blocoUrl)
      .pipe(
        tap(_ => this.log('fetched Blocos')),
        catchError(this.handleError<Bloco[]>('getBlocos', []))
      );
  }

  /** GET no by id. Will 404 if id not found */
  getBloco(codigo: string): Observable<Bloco> {
    const url = `${this.blocoUrl}/${codigo}`;
    return this.http.get<Bloco>(url).pipe(
      tap(_ => this.log(`fetched Bloco codigo=${codigo}`)),
      catchError(this.handleError<Bloco>(`getBloco codigo=${codigo}`))
    );
  }

  getBlocoData(codigo: string) {
    const url = `${this.blocoUrl}/${codigo}`;
    console.log(this.http.get<Bloco>(url));
  }

  getViagensServico(idServViat: string): Observable<ViagemDTO[]> {
    const url = `${this.vig}/${idServViat}`;
    return this.http.get<ViagemDTO[]>(url).pipe(
      tap(_ => this.log(`fetched servicos viatura idServico=${idServViat}`)),
      catchError(this.handleError<ViagemDTO[]>(`getPercurso idPercurso=${idServViat}`))
    );
  }

 /* getPermissoesMotorista(codigo: string): Observable<TipoTripulante> {
    var Bloco = this.getBlocoData(codigo);
    
    return this.http.get<TipoTripulante>(Bloco.).pipe(
      tap(_ => this.log(`fetched Bloco codigo=${codigo}`)),
      catchError(this.handleError<TipoTripulante>(`getBloco codigo=${codigo}`))
    );
  }*/

  ////////// Save methods //////////

  /** POST: add a new Bloco to the server */
  addBloco(Bloco: Bloco): Observable<any> {
    return this.http.post<any>(this.blocoUrl, Bloco, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<Bloco>('addBloco'))
    );
  }

  ///** DELETE: delete the tipoviatura from the server */
  deleteBloco(Bloco: Bloco | string): Observable<Bloco> {
    const codigo = typeof Bloco === 'string' ? Bloco : Bloco.codigo;
    const url = `${this.blocoUrl}/${codigo}`;

    return this.http.delete<Bloco>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted Bloco codigo=${codigo}`)),
      catchError(this.handleError<Bloco>('deleteBloco'))
    );
  }

  /** PUT: update the hero on the server */
  updateBloco(Bloco: Bloco, codigo: string): Observable<any> {
    const url = `${this.blocoUrl}/${codigo}`;
    return this.http.put<any>(url, Bloco, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateBloco'))
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

  /* GET Bloco whose name contains search term */
  /* istanbul ignore next */
  searchBloco(term: string): Observable<Bloco[]> {
    if (!term.trim()) {
      // if not search term, return empty Bloco array.
      return of([]);
    }
    return this.http.get<Bloco[]>(`${this.blocoUrl}/?codigo=${term}`).pipe(
      tap(x => x.length ?
        this.log(`found Bloco matching "${term}"`) :
        this.log(`no Bloco matching "${term}"`)),
      catchError(this.handleError<Bloco[]>('searchBloco', []))
    );
  }
  /** Log a TipoviaturaService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`BlocoService: ${message}`);
  }

  
}
