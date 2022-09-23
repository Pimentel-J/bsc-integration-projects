package eapli.shopfloor.util;

import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Tempo {

	public static long[] calculaDiferencaHMS(long diferenca) {
    	long[] tempos = new long[3];	// horas, minutos, segundos
    	long horas = TimeUnit.HOURS.convert(diferenca, TimeUnit.MILLISECONDS);
    	long minutos = TimeUnit.MINUTES.convert(diferenca, TimeUnit.MILLISECONDS) - horas*60;
    	long segundos = TimeUnit.SECONDS.convert(diferenca, TimeUnit.MILLISECONDS) - horas*60 - minutos*60;
    	tempos[0] = horas;
    	tempos[1] = minutos;
    	tempos[2] = segundos;
    	return tempos;
    }
    
    public static long calculaDiferencaMilis(Calendar inicio, Calendar fim) {
    	return Math.abs(fim.getTimeInMillis()-inicio.getTimeInMillis());
    }
    
    public static Duration calendarToDuration(Calendar duracao) {
    	String textoPeriod = "PT"+duracao.get(Calendar.HOUR_OF_DAY)+"H"+duracao.get(Calendar.MINUTE)+"M"+duracao.get(Calendar.SECOND)+"S";
    	return Duration.parse(textoPeriod);
    }
	
}
