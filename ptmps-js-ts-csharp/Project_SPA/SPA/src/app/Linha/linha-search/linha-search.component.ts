import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';


import { Linha } from '../linha';
import { LinhaService } from '../linha.service'

@Component({
  selector: 'app-linha-search',
  templateUrl: './linha-search.component.html',
  styleUrls: ['./linha-search.component.css']
})
export class LinhaSearchComponent implements OnInit {

  linha$: Observable<Linha[]>;
  private searchTerms = new Subject<string>();
  
  constructor(private linhaService: LinhaService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.linha$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),
    
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.linhaService.searchLinha(term)),
    );
  }

}
