import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import Config from '../../config'


@Injectable({
    providedIn: 'root'
})

export class ImportService {

    private importMDRUrl = Config.apis.mdrPrefix + 'importarDados';  // URL to web api
    private importMDVUrl = Config.apis.mdvPrefix + 'importarDados';  // URL to web api

    //httpOptions = {
    //    headers: new HttpHeaders({
    //        'Content-Type': 'application/json'
    //    })
    //};

    constructor(private http: HttpClient,
        private messageService: MessageService) { }

    ////////// Save methods //////////

    /** POST: add a new Percurso to the server */
  importarFicheiroMDR(ficheiro: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('ficheiro', ficheiro, ficheiro.name);
    return this.http.post<any>(this.importMDRUrl, formData).pipe(
            tap((ret) => this.log(ret.message)),
            catchError(this.handleError<any>('importarFicheiro'))
        );
  }

  importarFicheiroMDV(ficheiro: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('ficheiro', ficheiro, ficheiro.name);
    return this.http.post<any>(this.importMDVUrl, formData).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<any>('importarFicheiro'))
    );
  }

    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead

            // TODO: better job of transforming error for user consumption
            this.log(`${operation} failed: ${error.error.error}`);

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }

    /** Log a Percurso message with the MessageService */
    private log(message: string) {
        this.messageService.add(`ImportService: ${message}`);
    }
}
