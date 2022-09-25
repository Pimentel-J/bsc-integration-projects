import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';

import { Tripulante } from './tripulante';
import Config from '../../config'

@Injectable({
  providedIn: 'root'
})

export class TripulanteService {
  // URL to web api
  private tripulanteUrl = Config.apis.mdvPrefix + 'tripulantes';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  getTripulantes(): Observable<Tripulante[]> {
    // TODO: send the message _after_ fetching the tripulantes
    //this.messageService.add('TripulanteService: fetched tripulantes');
    return this.http.get<Tripulante[]>(this.tripulanteUrl).pipe(
      tap(_ => this.log('fetched Tripulantes')),
      catchError(this.handleError<Tripulante[]>('getTripulantes', []))
    );
  }

  /** GET Tripulante by numeroMecanografico. Will 404 if numeroMecanografico not found */
  getTripulante(numeroMecanografico: string): Observable<Tripulante> {
    const url = `${this.tripulanteUrl}/${numeroMecanografico}`;
    return this.http.get<Tripulante>(url).pipe(
      tap(_ => this.log(`fetched tripulante numeroMecanografico=${numeroMecanografico}`)),
      catchError(this.handleError<Tripulante>(`getTripulante numeroMecanografico=${numeroMecanografico}`))
    );
  }

  ////////// Save methods //////////

  /** POST: add a new Tripulante to the server */
  addTripulante(tripulante: Tripulante): Observable<any> {
    if (tripulante.numeroMecanografico == null) {
      alert('Dados de preenchimento obrigatório incompletos');
    } else {
      return this.http.post<any>(this.tripulanteUrl, tripulante, this.httpOptions).pipe(
        tap((ret) => this.log(ret.message)),
        catchError(this.handleError<Tripulante>('addTripulante'))
      );
    }
  }

  /**
   * PUT: update the Tripulante on the server
   * @param tripulante 
   * @param numeroMecanografico 
   */
  updateTripulante(tripulante: Tripulante, numeroMecanografico: string): Observable<any> {
    const url = `${this.tripulanteUrl}/${numeroMecanografico}`;
    return this.http.put<any>(url, tripulante, this.httpOptions).pipe(
      tap(ret => this.log(ret.message)),
      catchError(this.handleError<any>('updateTripulante'))
    );
  }

  /**
   * DELETE: delete the Tripulante from the server
   * @param tripulante 
   */
  deleteTripulante(tripulante: Tripulante | string): Observable<Tripulante> {
    const numeroMecanografico = typeof tripulante === 'string' ? tripulante : tripulante.numeroMecanografico;
    const url = `${this.tripulanteUrl}/${numeroMecanografico}`;

    return this.http.delete<any>(url, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<Tripulante>('deleteTripulante'))
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

  /** Log a TripulanteService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`TripulanteService: ${message}`);
  }

  /* istanbul ignore next */
  private getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: {
        return "Dados de preenchimento obrigatório incompletos";
      }
      case 0: {
        return `Já existe um tripulante com o nº mecanográfico`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }
    }
  }

}
