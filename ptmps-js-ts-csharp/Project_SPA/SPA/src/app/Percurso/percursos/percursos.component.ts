import { Component, OnInit } from '@angular/core';
import { LinhaService } from 'src/app/Linha/linha.service';
import { Linha} from '../../Linha/linha'
import { Percurso } from '../percurso'
import { PercursoService } from '../percurso.service'

@Component({
  selector: 'app-percursos',
  templateUrl: './percursos.component.html',
  styleUrls: ['./percursos.component.css']
})

export class PercursosComponent implements OnInit {

  percursos: Percurso[] = [];
  linhas: Linha[]=[];
  linha :String;

  constructor(private percursoService: PercursoService,  private linhaService : LinhaService) { }

  ngOnInit() {
    this.linhaService.getLinhas()
      .subscribe(linhas => this.linhas = linhas);
      
  }

  onChange(linhaSelect: string) {
    this.linha = linhaSelect;
    this.getPercursosByLine();
  }

  getPercursosByLine(): void {
    this.percursoService.getPercursosByLine(this.linha)
      .subscribe(percursos => this.percursos = percursos);
      this.percursos.forEach(element => {
        console.log(element.idPercurso);
      });

  }


}
