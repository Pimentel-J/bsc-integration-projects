import { Component, OnInit } from '@angular/core';
import { Viatura } from '../viatura';
import { ViaturaService } from '../viatura.service';

@Component({
  selector: 'app-viaturas',
  templateUrl: './viaturas.component.html',
  styleUrls: ['./viaturas.component.css']
})
export class ViaturasComponent implements OnInit {

  viaturas: Viatura[] = [];

  constructor(private viaturaService: ViaturaService) { }

  ngOnInit(): void {
    this.getViaturas();
     
  }

  getViaturas(): void {
    this.viaturaService.getViaturas()
      .subscribe(viaturas => this.viaturas = viaturas);
  }


  delete(viatura: Viatura): void {
    if (confirm('Remover definitivamente a viatura ' + viatura.id + '?')) {
      this.viaturas = this.viaturas.filter(n => n !== viatura);
      this.viaturaService.deleteViatura(viatura).subscribe();
    }
  }

  import():void{
    this.viaturaService.import();
  }

}
