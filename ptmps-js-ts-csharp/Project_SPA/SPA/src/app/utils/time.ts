import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class TimeUtils {

  seconds2timeValue(seg: number) {

    if (!seg) {
      seg = 0;
    }

    var horas = Math.floor(seg/(60*60));
    seg = seg - horas*(60*60);
    var minutos = Math.floor(seg/60);
    seg = seg - minutos*60;
    return ('0' + horas).slice(-2) + ':' + ('0' + minutos).slice(-2) + ':' + ('0' + seg).slice(-2);

  }
  
}
