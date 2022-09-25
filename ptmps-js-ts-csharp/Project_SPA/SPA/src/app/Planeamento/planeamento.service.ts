import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { MessageService } from '../message.service';

import Config from '../../config'
import { SolucoesMudancaMotorista } from './solucao-mudanca-motorista/solucoes-mudanca-motorista';
import { InputGenetico } from './solucao-genetico/input-genetico';
import { SolucaoGenetico } from './solucao-genetico/solucao-genetico';
import { InputEscalonamento } from './solucao-escalonamento/input-escalonamento';
import { SolucaoEscalonamento } from './solucao-escalonamento/solucao-escalonamento';


@Injectable({
  providedIn: 'root'
})

export class PlaneamentoService {

  private smmUrl = Config.apis.planPrefix + '/solMudancaMotorista';  // URL to web api
  private sgUrl = Config.apis.planPrefix + '/solGenetico';  // URL to web api
  private escUrl = Config.apis.planPrefix + '/solEscalonamento';  // URL to web api
   

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

 

  /** POST: obter solucao do algoritmo de mudanca de motorista */
  getSolucaoMudMot(sol: SolucoesMudancaMotorista): Observable<any> {
    var bodyForm = 'horaInicial=' + sol.horaInicial + '&noOrigem=' + sol.noOrigem + '&noDestino=' + sol.noDestino;
    return this.http.post<any>(this.smmUrl, bodyForm, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<SolucoesMudancaMotorista>('getSolucao'))
    );
  }

  /** POST: obter solucao do algoritmo genetico */
  getSolucaoGenetico(input: InputGenetico): Observable<any> {
    var bodyForm =
      'servicoViaturaID=' + input.servicoViaturaID +
      '&escalonamentoMotoristas=' + input.escalonamentoMotoristas +
      '&numeroGeracoes=' + input.numeroGeracoes +
      '&tamanhoPopulacao=' + input.tamanhoPopulacao +
      '&probCruzamento=' + input.probCruzamento +
      '&probMutacao=' + input.probMutacao +
      '&taxaPreservacao=' + input.taxaPreservacao +
      '&numeroGeracoesEstagnacao=' + input.numeroGeracoesEstagnacao +
      '&valorAlvo=' + input.valorAlvo +
      '&tempoLimite=' + input.tempoLimite +
      '&escrita=' + input.escrita
      ;
    return this.http.post<any>(this.sgUrl, bodyForm, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<SolucaoGenetico>('getSolucao'))
    );
  }

  /** POST: obter solucao do escalonamento para todos os SV */
  getSolucaoEscalonamento(input: InputEscalonamento): Observable<any> {
    var bodyForm =
      'numeroGeracoes=' + input.numeroGeracoes +
      '&tamanhoPopulacao=' + input.tamanhoPopulacao +
      '&probCruzamento=' + input.probCruzamento +
      '&probMutacao=' + input.probMutacao +
      '&taxaPreservacao=' + input.taxaPreservacao +
      '&numeroGeracoesEstagnacao=' + input.numeroGeracoesEstagnacao +
      '&valorAlvo=' + input.valorAlvo +
      '&tempoLimite=' + input.tempoLimite +
      '&escrita=' + input.escrita
      ;
    console.log(bodyForm);
    return this.http.post<any>(this.escUrl, bodyForm, this.httpOptions).pipe(
      tap((ret) => this.log(ret.message)),
      catchError(this.handleError<SolucaoEscalonamento>('getSolucao'))
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
      this.log(`${operation} failed: ${error.error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a TipoviaturaService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`NoService: ${message}`);
  }
}
