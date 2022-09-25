import { Component, OnInit } from '@angular/core';
import { ImportService } from './import.service';
import { filter } from 'rxjs/operators';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-import-file',
  templateUrl: './import-file.component.html',
  styleUrls: ['./import-file.component.css']
})
export class ImportFileComponent implements OnInit {

  ficheiro: File;

  constructor(private importService: ImportService,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {

  }

  receberFicheiro(ficheiros: FileList) {
    this.ficheiro = ficheiros.item(0);
  }

  create() {
    if (confirm('Importar ficheiro ' + this.ficheiro.name + '?')) {
      this.spinner.show();
      this.importService.importarFicheiroMDR(this.ficheiro)
        .pipe(
          filter(
            (ret) => ret != null
          )
        )
        .subscribe(
          () => {
            console.log('DB MDR carregada');
            this.importService.importarFicheiroMDV(this.ficheiro)
              .pipe(
                filter(
                  (ret) => ret != null
                )
              )
              .subscribe(
                () => {
                  alert('Ficheiro importado com sucesso!');
                  this.spinner.hide();
                }
              )
          }
      )
    }


  }
}
