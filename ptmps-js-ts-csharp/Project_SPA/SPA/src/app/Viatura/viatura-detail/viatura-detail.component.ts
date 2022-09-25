import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { filter } from 'rxjs/operators';
import { Viatura } from '../viatura';
import { ViaturaService } from '../viatura.service';
import { Tipoviatura } from 'src/app/tipoviatura/tipoviatura';
import { TipoviaturaService } from 'src/app/tipoviatura/tipoviatura.service';



@Component({
  selector: 'app-viatura-detail',
  templateUrl: './viatura-detail.component.html',
  styleUrls: ['./viatura-detail.component.css']
})
  
export class ViaturaDetailComponent implements OnInit {

  viatura: Viatura = { id: null, niv: null, tipoviatura: null, data_entrada_servico: null };
  criarViatura: boolean;
  selectTpv: Tipoviatura[];

  constructor(
    private route: ActivatedRoute,
    private viaturaService: ViaturaService,
    private tipoViaturaService: TipoviaturaService,
    private location: Location
  ) { }

  ngOnInit(): void {
    const codigo = this.route.snapshot.paramMap.get('id');
    if (codigo) {;
      this.getViatura(codigo);
      this.criarViatura = false;
    } else {
      this.criarViatura = true;
    }

    this.tipoViaturaService.getTiposviatura()
      .subscribe(selectTpv => { this.selectTpv = selectTpv; console.log(this.selectTpv); });
    
  }

  getViatura(codigo: string): void {
    this.viaturaService.getViaturaData(codigo);
    this.viaturaService.getViatura(codigo)
      .subscribe(viatura => this.viatura = viatura);
    
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (confirm('Guardar alterações da viatura '+ this.viatura.id + '?')) {
      const codigo = this.route.snapshot.paramMap.get('id');
      console.log(this.viatura);
      this.viaturaService.updateViatura(this.viatura, codigo)
        .pipe(
          filter(
            (viatura: Viatura) => viatura != null
          )
        )
        .subscribe(
          () => this.goBack()
        );
    }
    
  }

  create() {

        if (confirm('Criar viatura ' + this.viatura.id + '?')) {
          
        this.viaturaService.addViatura(this.viatura)
          .pipe(
            filter(
              (viatura: Viatura) => viatura != null
            )
          )
          .subscribe(
            () => this.goBack()
          );
      }
    
  }


}
