import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';
import { ViagemDTO } from './viagemDTO';

import Config from '../../config';
import { CreatingViagemDTO } from './creatingViagemDTO';

@Injectable({
  providedIn: 'root'
})
  
export class ViagemService {

  private viagemUrl = Config.apis.mdvPrefix + 'viagens'; // URL
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private http: HttpClient,
    private messageService: MessageService) { }
  
 
  getViagens(): Observable<ViagemDTO[]> {
    return this.http.get<ViagemDTO[]>(this.viagemUrl)
      .pipe(tap(_ => this.log('fetched Viagens')),
        catchError(this.handleError<ViagemDTO[]>('getViagens', []))
        );
  }

  getViagensByServicoViatura(idServicoViatura:string): Observable<ViagemDTO[]> {
    var http_options = {
      params: {
        servicoViatura: idServicoViatura
      }
    };
    return this.http.get<ViagemDTO[]>(this.viagemUrl, http_options)
      .pipe(tap(_ => this.log('fetched Viagens')),
        catchError(this.handleError<ViagemDTO[]>('getViagens', []))
      );
  }

  getViagensSemServico(): Observable<ViagemDTO[]> {
    return this.http.get<ViagemDTO[]>(this.viagemUrl + '/semservico')
      .pipe(tap(_ => this.log('fetched Viagens')),
        catchError(this.handleError<ViagemDTO[]>('getViagens', []))
      );
  }
  
  /** GET Viagem by id. Will 404 if id not found */
  getViagem(idViagem: string): Observable<ViagemDTO> {
    const url = `${this.viagemUrl}/${idViagem}`;
    return this.http.get<ViagemDTO>(url).pipe(
      tap(_ => this.log(`fetched viagem idViagem=${idViagem}`)),
      catchError(this.handleError<ViagemDTO>(`getViagem idViagem=${idViagem}`))
    );
  }
 

  getViagemData(codigo: string) {
    const url = `${this.viagemUrl}/${codigo}`;
    console.log(this.http.get<ViagemDTO>(url));
    console.log(codigo);
  }

 ////////// Save methods //////////

  /** POST: add new Viagem to the server */
  addViagem(viagem: CreatingViagemDTO): Observable<any> {
    return this.http.post<any>(this.viagemUrl, viagem, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<any>('addViagem'))
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
    this.messageService.add(`ViagemService: ${message}`);
  }
}
