package modelo;

import java.time.LocalDate;

import modelo.exceptions.NotCorrectTypeException;
import modelo.types.Time;

public class Data<T> {
	private T internalData;
	
	public Data (T internalData) {
		this.internalData = internalData;
	}
	
	public double getValue() {
		
		if(internalData instanceof Byte) {
			return (double) internalData;
		} else if (internalData instanceof Integer){
			Integer entero = (Integer)internalData;
			Double doble = (double)entero ;
			return doble;
		} else if (internalData instanceof Double) {
			return (double) internalData;
		} else if (internalData instanceof LocalDate) {
			 LocalDate date = (LocalDate)internalData;
			return (date.getYear() * 10000) + (date.getMonthValue() * 100) + (date.getDayOfMonth());
		} else if (internalData instanceof Time) {
			 Time time = (Time)internalData;
			 return (time.getHour() * 10000) + (time.getMinutes() * 100) + (time.getSeconds());
		} 
		
		return 0;
	}

	public T getInternalData() {
		return internalData;
	}
	
	public Byte getByteValue() throws NotCorrectTypeException{
		if(internalData instanceof Byte){
			return (Byte)internalData;
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public Integer getIntegerValue() throws NotCorrectTypeException{
		if(internalData instanceof Integer){
			return (Integer)internalData;
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public Double getDoubleValue() throws NotCorrectTypeException{
		if(internalData instanceof Double){
			return (Double)internalData;
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public String getDateValue() throws NotCorrectTypeException{
		if(internalData instanceof LocalDate){
			LocalDate date = (LocalDate)internalData;
			return date.getDayOfMonth() + "-" + date.getMonth() + "-" + date.getYear();
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public String getTimeValue() throws NotCorrectTypeException{
		if(internalData instanceof Time){
			Time time = (Time)internalData;
			return time.getHour() + ":" + time.getMinutes() + ":" + time.getSeconds();
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public StringBuffer getStringBufferValue() throws NotCorrectTypeException{
		if(internalData instanceof StringBuffer){
			return (StringBuffer)internalData;
		} else {
			throw new NotCorrectTypeException();
		}
	}
	
	public void setInternalData(T internalData) {
		this.internalData = internalData;
	}
}
