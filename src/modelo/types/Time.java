package modelo.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.exceptions.NotCorrectFormatTimeException;

public class Time {
	
	private int hour, minutes, seconds;

	public Time(String hora) throws NotCorrectFormatTimeException{
		if(hora.matches("^(\\d{2}:\\d{2}:\\d{2})$")){
			String[] horaDividida = hora.split(":");
			int hour = Integer.parseInt(horaDividida[0]);
			int minutes = Integer.parseInt(horaDividida[1]);
			int seconds = Integer.parseInt(horaDividida[2]);

			if(hour < 24 && minutes < 60 && seconds < 60 && hour > 0 && minutes > 0 && seconds > 0){
				this.hour = hour;
				this.minutes = minutes;
				this.seconds = seconds;
			} else {
				throw new NotCorrectFormatTimeException();
			}
		} else {
			throw new NotCorrectFormatTimeException();
		}
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public String getString(){
		return hour + ":" + minutes + ":" + seconds;
	}
}
