package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Csv implements Serializable{
	private ArrayList<Column> columnas;
	private String nombre;
	
	public Csv(String nombre){
		this.nombre = nombre;
		columnas = new ArrayList<Column>();
	}
	
	public void addColumn(Column column){
		columnas.add(column);
	}

	public ArrayList<Column> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<Column> columnas) {
		this.columnas = columnas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
