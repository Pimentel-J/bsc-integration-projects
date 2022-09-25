import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import { Viatura } from './viatura';
import Config from '../../config';

@Injectable({
  providedIn: 'root'
})
export class ViaturaService {

  private viaturaUrl = Config.apis.mdvPrefix + 'viaturas'; // URL
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private http: HttpClient,
    private messageService: MessageService) { }
  
  import() {
    const url = this.viaturaUrl + '/import';
    this.http.post<any>(url, this.httpOptions)
      .pipe(tap((ret) => this.log(ret.message)))
  }
  
  getViaturas(): Observable<Viatura[]> {
    return this.http.get<Viatura[]>(this.viaturaUrl)
      .pipe(tap(_ => this.log('fetched Viaturas')),
        catchError(this.handleError<Viatura[]>('getViaturas', []))
        );
  }
  
  /** GET no by id. Will 404 if id not found */
  getViatura(matricula: string): Observable<Viatura> {
    const url = `${this.viaturaUrl}/${matricula}`;
    return this.http.get<Viatura>(url).pipe(
      tap(_ => this.log(`fetched Viatura matricula=${matricula}`)),
      catchError(this.handleError<Viatura>(`getViatura matricula=${matricula}`))
    );
  }

  getViaturaData(codigo: string) {
    const url = `${this.viaturaUrl}/${codigo}`;
    console.log(this.http.get<Viatura>(url));
    console.log(codigo);
  }

 ////////// Save methods //////////

  /** POST: add a new viatura to the server */
  addViatura(v: Viatura): Observable<any> {
    console.log(v);
    console.log(this.viaturaUrl);
    console.log(this.httpOptions);
     return this.http.post<any>(this.viaturaUrl, v, this.httpOptions)
      .pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<Viatura>('addViatura'))
    );
  }

  /** DELETE: delete the tipoviatura from the server */
  deleteViatura(viatura: Viatura | string): Observable<Viatura> {
    const codigo = typeof viatura === 'string' ? viatura : viatura.id;
    const url = `${this.viaturaUrl}/${codigo}`;

    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<Viatura>('deleteViatura'))
    );
  }

  /** PUT: update the tipoviatura on the server */
  updateViatura(viatura: Viatura, matricula: string): Observable<any> {
    const url = `${this.viaturaUrl}/${matricula}`;
    console.log(viatura);
    console.log(viatura.niv);
    console.log(viatura.tipoviatura);
    
    return this.http.put<any>(url, viatura, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateViatura'))
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
    this.messageService.add(`ViaturaService: ${message}`);
  }
}
