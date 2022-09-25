import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';

import { ServicoTripulante } from './servicoTripulante';
import Config from '../../config'

@Injectable({
  providedIn: 'root'
})

export class ServicoTripulanteService {
  // URL to web api
  private servicoTripulanteUrl = Config.apis.mdvPrefix + 'servicosTripulante';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getServicosTripulante(): Observable<ServicoTripulante[]> {
    // TODO: send the message _after_ fetching the servicosTripulante
    // this.messageService.add('ServicoTripulanteService: fetched servicosTripulante');
    return this.http.get<ServicoTripulante[]>(this.servicoTripulanteUrl)
      .pipe(
        tap(_ => this.log('fetched servicosTripulante')),
        catchError(this.handleError<ServicoTripulante[]>('getServicosTripulante', []))
      );
  }

  /** GET ServicoTripulante by codigoServicoTripulante. Will 404 if codigoServicoTripulante not found */
  getServicoTripulante(codigoServicoTripulante: string): Observable<ServicoTripulante> {
    const url = `${this.servicoTripulanteUrl}/${codigoServicoTripulante}`;
    return this.http.get<ServicoTripulante>(url).pipe(
      tap(_ => this.log(`fetched servicoTripulante codigoServicoTripulante=${codigoServicoTripulante}`)),
      catchError(this.handleError<ServicoTripulante>(`getServicoTripulante codigoServicoTripulante=${codigoServicoTripulante}`))
    );
  }

  ////////// Save methods //////////
  /** POST: add a new ServicoTripulante to the server */
  addServicoTripulante(servicoTripulante: ServicoTripulante): Observable<any> {
    if (servicoTripulante.codigoServicoTripulante == null) {
      alert('Dados de preenchimento obrigatório incompletos');
    } else {
      return this.http.post<any>(this.servicoTripulanteUrl, servicoTripulante, this.httpOptions).pipe(
        tap((ret) => this.log(ret.message)),
        catchError(this.handleError<ServicoTripulante>('addServicoTripulante'))
      );
    }
  }

  /**
   * DELETE: delete the ServicoTripulante from the server
   * @param servicoTripulante 
   */
  deleteServicoTripulante(servicoTripulante: ServicoTripulante | string): Observable<ServicoTripulante> {
    const codigoServicoTripulante = typeof servicoTripulante === 'string' ? servicoTripulante : servicoTripulante.codigoServicoTripulante;
    const url = `${this.servicoTripulanteUrl}/${codigoServicoTripulante}`;

    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<ServicoTripulante>('deleteServicoTripulante'))
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

      if (message.includes("undefined")) {
        alert(this.getServerErrorMessage(error));
      } else {
        this.log(message);
        // Alert popup
        alert(message);
      }

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ServicoTripulanteService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ServicoTripulanteService: ${message}`);
  }

  /* istanbul ignore next */
  private getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: {
        return "Dados de preenchimento obrigatório incompletos";
      }
      case 0: {
        return `Já existe um tripulante com o código de serviço de tripulante`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }
    }
  }

}
