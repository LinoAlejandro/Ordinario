package modelo;

import java.util.ArrayList;

import modelo.exceptions.NotEnoughDataException;

public class Row {
	private ArrayList<Data> datos;
	private Table tabla;
	
	public Row(Table tabla, String data) throws NotEnoughDataException {
		this.tabla = tabla;
		String[] string = data.split(",");
		if(string.length == tabla.getColumna().size()){
			for (Column tipo : tabla.getColumna()) {
				
			}
		} else {
			throw new NotEnoughDataException();
		}
		
	}
	
	public ArrayList<Data> getDatos() {
		return datos;
	}

	public void setDatos(ArrayList<Data> datos) {
		this.datos = datos;
	}
}
