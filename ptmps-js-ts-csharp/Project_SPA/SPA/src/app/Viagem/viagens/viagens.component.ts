import { Component, OnInit } from '@angular/core';
import { TimeUtils } from '../../utils/time';

import { ViagemDTO } from '../viagemDTO'
import { ViagemService } from '../viagem.service'

@Component({
  selector: 'app-viagens',
  templateUrl: './viagens.component.html',
  styleUrls: ['./viagens.component.css']
})

export class ViagensComponent implements OnInit {

  viagens: ViagemDTO[] = [];

  constructor(private viagemService: ViagemService,
    private timeUtils: TimeUtils) { }

  ngOnInit() {
    this.getViagens();
  }

  getViagens(): void {
    this.viagemService.getViagens()
      .subscribe(viagens => { this.viagens = viagens} );

  }

  timeMask(prop: number) {
    return this.timeUtils.seconds2timeValue(prop);
  }

}
