import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Passagem } from '../passagem';
import { TimeUtils } from '../../utils/time';

@Component({
  selector: 'app-passagem-detail',
  templateUrl: './passagem-detail.component.html',
  styleUrls: ['./passagem-detail.component.css']
})
export class PassagemDetailComponent implements OnInit {

  @Input() passagem: Passagem;

  editable: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private timeUtils: TimeUtils
    ) { }

  ngOnInit(): void {

    // this.passagem = {
    //   id: null,
    //   viagemId: null,
    //   horaPassagem: null,
    //   abreviaturaNo: null
    // }

  }

  timeMask(prop: number) {
    return this.timeUtils.seconds2timeValue(prop);
  }
}
