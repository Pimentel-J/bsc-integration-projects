import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';


import { Tipoviatura } from '../tipoviatura';
import { TipoviaturaService } from '../tipoviatura.service'

@Component({
  selector: 'app-tpv-search',
  templateUrl: './tpv-search.component.html',
  styleUrls: ['./tpv-search.component.css']
})
export class TpvSearchComponent implements OnInit {

  tiposviatura$: Observable<Tipoviatura[]>;
  private searchTerms = new Subject<string>();
  
  constructor(private tpvService: TipoviaturaService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.tiposviatura$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),
    
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.tpvService.searchTpv(term)),
    );
  }

}
