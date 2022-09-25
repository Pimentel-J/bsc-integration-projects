import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Tipoviatura } from './tipoviatura';
import { MessageService } from '../message.service';
import Config from '../../config';

@Injectable({
  providedIn: 'root'
})
  
export class TipoviaturaService {

  private tipoviaturaUrl = Config.apis.mdrPrefix+'tiposviatura';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getTiposviatura(): Observable<Tipoviatura[]> {
    // TODO: send the message _after_ fetching the tiposviatura
    //this.messageService.add('TipoviaturaService: fetched tiposviatura');
    return this.http.get<Tipoviatura[]>(this.tipoviaturaUrl)
      .pipe(
        tap(_ => this.log('fetched tiposviatura')),
        catchError(this.handleError<Tipoviatura[]>('getTiposviatura', []))
      );
  }

 /** GET hero by codigo. Return `undefined` when codigo not found */
 /* istanbul ignore next */
 getTipoviaturaNo404<Data>(codigo: string): Observable<Tipoviatura> {
  const url = `${this.tipoviaturaUrl}/?codigo=${codigo}`;
  return this.http.get<Tipoviatura[]>(url)
    .pipe(
      map(tiposviatura => tiposviatura[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} tipoviatura codigo=${codigo}`);
      }),
      catchError(this.handleError<Tipoviatura>(`getTipoviatura codigo=${codigo}`))
    );
}

   /** GET Tipoviatura by codigo. Will 404 if codigo not found */
    getTipoviatura(codigo: string): Observable<Tipoviatura> {
      const url = `${this.tipoviaturaUrl}/${codigo}`;
      console.log(url);
      return this.http.get<Tipoviatura>(url).pipe(
        tap(_ => this.log(`fetched tipoviatura codigo=${codigo}`)),
        catchError(this.handleError<Tipoviatura>(`getTipoviatura codigo=${codigo}`))
      );
    }
   
  /* GET tipos de viatura whose name contains search term */
  searchTpv(term: string): Observable<Tipoviatura[]> {
    if (!term.trim()) {
      // if not search term, return empty linha array.
      return of([]);
    }
    return this.http.get<Tipoviatura[]>(`${this.tipoviaturaUrl}/?codigo=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found tipo de viatura matching "${term}"`) :
         this.log(`no tipo viaturas matching "${term}"`)),
      catchError(this.handleError<Tipoviatura[]>('searchTpv', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new tipoviatura to the server */
  addTipoviatura(tipoviatura: Tipoviatura): Observable<any> {
    return this.http.post<any>(this.tipoviaturaUrl, tipoviatura, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<Tipoviatura>('addTipoviatura'))
    );
  }

  /** DELETE: delete the tipoviatura from the server */
  deleteTipoviatura(tipoviatura: Tipoviatura | string): Observable<Tipoviatura> {
    const codigo = typeof tipoviatura === 'string' ? tipoviatura : tipoviatura.codigo;
    const url = `${this.tipoviaturaUrl}/${codigo}`;

    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<Tipoviatura>('deleteTipoviatura'))
    );
  }

  /** PUT: update the tipoviatura on the server */
  updateTipoviatura(tipoviatura: Tipoviatura, codigo: string): Observable<any> {
    const url = `${this.tipoviaturaUrl}/${codigo}`;
    console.log(tipoviatura);
    console.log(tipoviatura.codigo);
    console.log(tipoviatura.consumoMedio);
    
    return this.http.put<any>(url, tipoviatura, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateTipoviatura'))
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
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a TipoviaturaService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`TipoviaturaService: ${message}`);
  }
}
