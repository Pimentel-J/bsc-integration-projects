import { Component, OnInit } from '@angular/core';
import { ServicoViatura } from '../servicoviatura';
import { ServicoViaturaService } from '../servicoviatura.service';
//mport { ViagemDTO } from '../../Viagem/viagemDTO';

@Component({
  selector: 'app-sv-list',
  templateUrl: './sv-list.component.html',
  styleUrls: ['./sv-list.component.css']
})

export class SvListComponent implements OnInit {

  servicosViatura: ServicoViatura[] = [];
  //selectviagens: ViagemDTO[] = [];

  constructor(private servicoViaturaService: ServicoViaturaService) { }

  ngOnInit(): void {
    this.getServicosViatura();

  }


  getServicosViatura(): void {
    this.servicoViaturaService.getServicoViaturas()
      .subscribe(servicosViatura => {
        this.servicosViatura = servicosViatura
      });


  }

}
